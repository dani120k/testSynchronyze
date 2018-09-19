package syncronization.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository<P> extends CrudRepository<UserCredentials, Long> {

}
