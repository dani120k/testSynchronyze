package syncronization.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository<P> extends CrudRepository<Domain, Long> {
    @Query("select b from Domain b where b.domainName = :domainName")
    P findByDomainName(@Param("domainName") String domainName);
}
