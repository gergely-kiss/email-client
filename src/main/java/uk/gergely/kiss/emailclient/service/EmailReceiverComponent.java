package uk.gergely.kiss.emailclient.service;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;

@Component
public class EmailReceiverComponent {

    private final Logger logger = LoggerFactory.getLogger(EmailReceiverComponent.class);

    private final MessageService messageService;

    public EmailReceiverComponent(MessageService messageService) {
        this.messageService = messageService;
    }

    private Properties getServerProperties(String protocol, String host, String port) {
        Properties properties = new Properties();

        // server setting
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);

        // SSL setting
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));
        return properties;
    }

    public JSONObject downloadEmails(String protocol, String host, String port, String email, String password, String filter, String folder) {
        Properties properties = getServerProperties(protocol,host,port);
        Session session = Session.getDefaultInstance(properties);
        LocalDateTime startTime = LocalDateTime.now();
        logger.info("Start time of the process: {}", startTime);
        try {
            Store store = session.getStore(protocol);
            store.connect(email, password);
            Folder folderInbox = store.getFolder(folder);
            folderInbox.open(Folder.READ_ONLY);
            Message[] messages = folderInbox.getMessages();
            JSONObject response = new JSONObject();
            List<MessageDTO> messageList = new ArrayList<>();
            int j = 0;
            for (int i = 0; i < messages.length; i++) {
                Message msg = messages[i];
                String subject = msg.getSubject();
                if (subject != null && subject.contains(filter)) {
                    j++;
                    logger.info("Message total message {} daily coding problem number {}", i, j);
                    MessageDTO messageDTO = new MessageDTO();
                    messageDTO.setSubject(msg.getSubject());
                    messageDTO.setContent(getTextFromMessage(msg));
                    messageList.add(messageDTO);
                }
            }
            // disconnect
            folderInbox.close(false);
            store.close();
            messageService.saveBatchOfMessages(messageList);
            response.put("dcp_list", messageList);
            logger.info("Process finished  after {}", (LocalDateTime.now().getMinute() - startTime.getMinute()));
            return response;
        } catch (NoSuchProviderException ex) {
            logger.error("No provider for protocol: {} ex.getMessage() {} ", protocol, ex.getMessage());
            return new JSONObject(ex.getMessage());
        } catch (MessagingException ex) {
            logger.error("Could not connect to the message store. ex.getMessage() {}", ex.getMessage());
            return new JSONObject(ex.getMessage());
        } catch (IOException ex) {
            logger.error("no content in the message {}", ex.getMessage());
            return new JSONObject(ex.getMessage());
        }
    }
    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException{
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }
}

