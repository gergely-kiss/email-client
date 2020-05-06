package uk.gergely.kiss.emailclient.security;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "app")
public class AppEntity {

    @Id
    @Column(name = "app_id")
    private String appId;
    @Column(name = "app_info")
    private String appInfo;
    @Column
    private String role;

}
