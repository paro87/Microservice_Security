package com.paro.clientpasswordgt.security;


import com.paro.clientpasswordgt.clientUser.ClientUser;
import com.paro.clientpasswordgt.clientUser.ClientUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientUserDetailsService implements UserDetailsService {

    private final ClientUserRepository clientUserRepository;
    @Autowired
    public ClientUserDetailsService(ClientUserRepository clientUserRepository) {
        this.clientUserRepository = clientUserRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String clientUser) throws UsernameNotFoundException {

        Optional<ClientUser> optionalClientUser=clientUserRepository.findByUsername(clientUser);
        if(!optionalClientUser.isPresent()){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new ClientUserDetails(optionalClientUser.get());
    }


}
