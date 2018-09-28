package syncronization.importFrom.interfaces;

import syncronization.model.Domain;

import java.util.List;

public interface ILdapConnection {

    List<Domain> getFullStructure();

}
