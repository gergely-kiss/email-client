package uk.gergely.kiss.emailclient.security;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppEntityRepository extends JpaRepository<AppEntity, String> {
}
