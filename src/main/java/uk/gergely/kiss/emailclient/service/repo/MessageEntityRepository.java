package uk.gergely.kiss.emailclient.service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageEntityRepository extends JpaRepository<MessageEntity, Integer> {
}
