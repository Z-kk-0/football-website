package ch.zkk0.football.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.zkk0.football.model.User;
import ch.zkk0.football.repository.UserRepository;
import ch.zkk0.football.security.UserDetailsImpl;

/**
 * Service for loading user-specific data for authentication.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Repository for user entities.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Loads the user details by username.
     *
     * @param username the username to search for
     * @return UserDetails for the given username
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
