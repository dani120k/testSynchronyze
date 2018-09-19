package syncronization.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID extends Serializable> extends Repository<T, ID> {
    Optional<T> findOne(Long id);
    Iterable<T> findAll();
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
}

