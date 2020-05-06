package uk.gergely.kiss.emailclient.service.repo;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "process")
@NoArgsConstructor
@Data
public class ProcessEntity {

    @Id
    private String id;

    @Column(name = "is_running")
    private Boolean isRunning;
}
