package syncronization.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoService{

    @Autowired
    UserInfoRepository<UserInfo> userInfoRepository;

    @Transactional("jpaTransactionManager")
    public List<UserInfo> getAllPersons() {
        return (List<UserInfo>) userInfoRepository.findAll();
    }


    @Transactional("jpaTransactionManager")
    public boolean addUserInfo(UserInfo userInfo) {
        try {
            userInfoRepository.save(userInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional("jpaTransactionManager")
    public boolean updateUserInfo(UserInfo userInfo) {
        try {
            userInfoRepository.save(userInfo);
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional("jpaTransactionManager")
    public void deleteUserInfo(UserInfo userInfo) {
        userInfoRepository.delete(userInfo);
    }

    @Transactional("jpaTransactionManager")
    public List<UserInfo> findByOrgUnitId(Long id) {
        return (List<UserInfo>)userInfoRepository.findByDomainId(id);
    }

}
