package uk.gergely.kiss.emailclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import uk.gergely.kiss.emailclient.security.DefaultAppConfiguration;

@SpringBootApplication
public class EmailClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EmailClientApplication.class, args);
		context.getBean(DefaultAppConfiguration.class).registerDefaultApplication();
	}
}
