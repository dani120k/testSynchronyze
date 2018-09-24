package syncronization.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgUnitReository<P> extends CrudRepository<OrgUnit, Long> {
    @Query("select b from OrgUnit b where b.domainId = :domain_id")
    Iterable<P> findByDomainId(@Param("domain_id") Long name);
}
