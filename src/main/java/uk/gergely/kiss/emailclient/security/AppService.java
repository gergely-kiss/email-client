package uk.gergely.kiss.emailclient.security;

import java.util.Set;

public interface AppService {


    String encode(String appInfo);
    boolean isMatch(String appInfo, String encodedPassword);
    AppEntity register(String appId, String appInfo);
    void unRegister(String appId);
    AppEntity findByApplicationId(String appId);
    Set<AppEntity> findAll();
    AppEntity updatePassword(AppEntity appEntity, String newPassword);
    AppEntity register(String appName, String appInfo, String role);
}
