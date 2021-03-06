package syncronization.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository<P> extends CrudRepository<UserInfo, Long> {
    @Query("select b from UserInfo b where b.email = :email")
    P findByEmail(@Param("email") String email);
}
