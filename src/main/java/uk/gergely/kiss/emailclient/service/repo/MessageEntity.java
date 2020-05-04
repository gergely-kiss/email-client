package uk.gergely.kiss.emailclient.service.repo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "message")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String content;
    @Column
    private Boolean done;
    @Column
    private String subject;
}
