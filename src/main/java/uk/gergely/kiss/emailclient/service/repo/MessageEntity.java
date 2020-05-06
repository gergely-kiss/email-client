package uk.gergely.kiss.emailclient.service.repo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "message")
@Data
@NoArgsConstructor
public class MessageEntity {

    @Id
    private Integer id;
    @Column
    private String content;
    @Column
    private Boolean done;
    @Column
    private String difficulty;
}
