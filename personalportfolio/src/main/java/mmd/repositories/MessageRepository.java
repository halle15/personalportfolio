package mmd.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mmd.models.Message;


@RepositoryRestResource(collectionResourceRel = "messages", path = "messages")
public interface MessageRepository extends CrudRepository<Message, Long>{
	@Override
    void deleteById(Long id);

    @Override
    void delete(Message entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Message> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
	
	List<Message> findByName(String name);
	
	Message findById(long id);
	
	List<Message> findByDate(Date date);
	
	Page<Message> findAll(Pageable pageable);
	
	List<Message> findByIsReadTrue();
	
	List<Message> findByIsReadFalse();
	
	long countByIsReadTrue();
	
	long countByIsReadFalse();
}
