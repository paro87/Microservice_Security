package com.paro.zjweauthorizationservice.authuser;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ResourceOwnerUserDetailsService implements UserDetailsService {

    private final ResourceOwnerRepository repository;

    public ResourceOwnerUserDetailsService(ResourceOwnerRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResourceOwner resourceOwner = repository.findByUsername(username).orElseThrow(RuntimeException::new);
        return new ResourceOwnerUserDetails(resourceOwner);
    }
}

