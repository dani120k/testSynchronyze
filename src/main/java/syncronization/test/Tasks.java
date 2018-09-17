package syncronization.test;

import syncronization.ProgrammConfig;
import syncronization.model.Domain;
import syncronization.update.StructureCreating;
import syncronization.update.UpdateUserInfo;

public class Tasks {
    static ProgrammConfig config = new ProgrammConfig();

    public static void updateDomain(Domain newdomain, Domain oldDomain){
        newdomain.setId(oldDomain.getId());
        config.setServerPort("8089");
        config.setServerHost("127.0.0.1");
        try {
            UpdateUserInfo.updateDomain(config, newdomain);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
