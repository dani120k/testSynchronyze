package syncronization.importFrom.authorization;

import syncronization.model.Domain;

import java.util.List;

public interface ILdapConnection {

    List<Domain> getFullStructure();

}
