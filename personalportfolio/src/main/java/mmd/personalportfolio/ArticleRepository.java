package mmd.personalportfolio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface ArticleRepository extends JpaRepository<Article, String>{
	

    @Override
    @RestResource(exported = false)
    <S extends Article> List<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(String id);

    @Override
    @RestResource(exported = false)
    void delete(Article entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Article> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
    
	
	
}
