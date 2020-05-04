package uk.gergely.kiss.emailclient.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImplementation implements UserDetailsService {

    private final AppService appService;

    @Autowired
    public UserDetailServiceImplementation(AppService appService) {
        this.appService = appService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new UserDetailsDTO(appService.findByApplicationId(username));
    }
}
