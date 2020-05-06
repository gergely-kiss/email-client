package uk.gergely.kiss.emailclient.service.repo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageEntityRepository extends PagingAndSortingRepository<MessageEntity, Integer> {

}
