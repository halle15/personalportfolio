package mmd.personalportfolio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;


@RepositoryRestResource(collectionResourceRel = "messages", path = "messages")
public interface MessageRepository extends CrudRepository<Message, Long>{
	@Override
    @RestResource(exported = false)
    void deleteById(Long id);

    @Override
    @RestResource(exported = false)
    void delete(Message entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Message> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
	
	List<Message> findByName(String name);
	
	Message findById(long id);
}
