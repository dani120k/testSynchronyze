package syncronization.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository<P> extends CrudRepository<UserInfo, Long> {

}
