package syncronization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DomainService {

    @Autowired
    DomainRepository<Domain> repository;

    @Transactional("jpaTransactionManager")
    public List<Domain> getAllDomains() {
        return (List<Domain>) repository.findAll();

    }

    @Transactional("jpaTransactionManager")
    public void addDomain(Domain domain) {
        repository.save(domain);
    }

    @Transactional("jpaTransactionManager")
    public void deleteDomain(Domain domain) {
        repository.delete(domain);
    }

    @Transactional("jpaTransactionManager")
    public void updateDomain(Domain domain) {
        repository.save(domain);
    }

    @Transactional("jpaTransactionManager")
    public Domain getByDomainName(String domainName) {
        return (Domain) repository.findByDomainName(domainName);
    }

}
