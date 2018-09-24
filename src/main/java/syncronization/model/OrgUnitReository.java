package syncronization.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgUnitReository<P> extends CrudRepository<OrgUnit, Long> {
    @Query("select b from OrgUnit b where b.orgUnitName = :orgUnitName")
    P findByOrgUnitName(@Param("orgUnitName") String orgUnitName);
}
