package syncronization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HierarchyGroupService {

    @Autowired
    HierarchyGroupRepository<HierarchyGroup> repository;

    @Transactional("jpaTransactionManager")
    public List<HierarchyGroup> getAllHierarchyGroups() {
        return (List<HierarchyGroup>) repository.findAll();

    }

    @Transactional("jpaTransactionManager")
    public void addHierarchyGroup(HierarchyGroup hierarchyGroup) {
        repository.save(hierarchyGroup);
    }

    @Transactional("jpaTransactionManager")
    public void deleteHierarchyGroup(HierarchyGroup hierarchyGroup) {
        repository.delete(hierarchyGroup);
    }

    @Transactional("jpaTransactionManager")
    public void updateHierarchyGroup(HierarchyGroup hierarchyGroup) {
        repository.save(hierarchyGroup);
    }

}
