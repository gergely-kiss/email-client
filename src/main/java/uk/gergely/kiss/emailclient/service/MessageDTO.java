package uk.gergely.kiss.emailclient.service;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {

    private String subject;
    private String content;

}
