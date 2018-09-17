package syncronization.test;

import syncronization.model.Domain;

import java.util.List;

public class UpdateStructure {
    ImportHashTable hashTable;

    public UpdateStructure(ImportHashTable hashTable){
        this.hashTable = hashTable;
    }

    public void updateStructure(List<Domain> domains){
        for(Domain domain :domains) {
            if (hashTable.getDomainHashtable().containsKey(domain.getDomainName())) {
                Tasks.updateDomain(domain, hashTable.getDomainHashtable().get(domain.getDomainName()));
                hashTable.getDomainHashtable().remove(domain.getDomainName());
            }
        }
    }
}
