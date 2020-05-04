package uk.gergely.kiss.emailclient.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MessageDTO {

    private String subject;
    private String content;

}
