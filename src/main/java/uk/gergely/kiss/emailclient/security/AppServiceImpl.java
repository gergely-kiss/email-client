package uk.gergely.kiss.emailclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppServiceImpl implements uk.gergely.kiss.emailclient.security.AppService{

    private final AppEntityRepository repository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AppServiceImpl(AppEntityRepository repository) {
        this.repository = repository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public AppEntity register(String appID, String appInfo) {
        return register(appID, appInfo, SecurityConstants.ROLE_APPLICATION);
    }

    @Override
    public AppEntity register(String appID, String appInfo, String role) {
        if (isApplicationAlreadyRegistered(appID)) {
            throw new KeyAlreadyExistsException();
        }
        return repository.save(new AppEntity(appID, encode(appInfo), role));
    }

    @Override
    public void unRegister(String appId) {
        repository.delete(findByApplicationId(appId));
    }

    @Override
    public AppEntity findByApplicationId(String appID) {
        return repository.findById(appID).orElseThrow(()-> new NoResultException("No application find by application name: " + appID));
    }

    @Override
    public Set<AppEntity> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public AppEntity updatePassword(AppEntity appEntity, String newPassword) {
        AppEntity savedApplicationEntity = findByApplicationId(appEntity.getAppId());
        savedApplicationEntity.setAppInfo(encode(newPassword));
        return repository.save(savedApplicationEntity);

    }


    private boolean isApplicationAlreadyRegistered(String appName) {
        return repository.findAll().stream().anyMatch(registeredApplicationEntity -> appName.equalsIgnoreCase(registeredApplicationEntity.getAppId()));
    }

    @Override
    public String encode(String appInfo) {
        return bCryptPasswordEncoder.encode(appInfo);
    }

    @Override
    public boolean isMatch(String appInfo, String encodedPassword) {
        return bCryptPasswordEncoder.matches(appInfo, encodedPassword);
    }
}
