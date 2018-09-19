package syncronization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserCredentialsService {

    @Autowired
    UserCredentialsRepository<UserCredentials> repository;

    @Transactional("jpaTransactionManager")
    public List<UserCredentials> getAllUserCredentials() {
        return (List<UserCredentials>) repository.findAll();

    }

    @Transactional("jpaTransactionManager")
    public void deleteUserCreds(UserCredentials userCredentials) {
        repository.delete(userCredentials);
    }

    @Transactional("jpaTransactionManager")
    public boolean updateUserInfo(UserCredentials userCredentials) {
        try {
            repository.save(userCredentials);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
