package ch.zkk0.football.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.zkk0.football.model.User;

/**
 * Implementation of Spring Security's UserDetails for application users.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    /**
     * The user's unique identifier.
     */
    private Long id;
    /**
     * The username of the user.
     */
    private String username;
    /**
     * The email address of the user.
     */
    private String email;
    /**
     * The hashed password of the user (ignored in JSON output).
     */
    @JsonIgnore
    private String password;
    /**
     * The authorities granted to the user.
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs a UserDetailsImpl instance.
     * @param id user ID
     * @param username username
     * @param email email address
     * @param password hashed password
     * @param authorities granted authorities
     */
    public UserDetailsImpl(Long id, String username, String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Builds a UserDetailsImpl from a User entity.
     * @param user the User entity
     * @return UserDetailsImpl instance
     */
    public static UserDetailsImpl build(User user) {
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName().name());

        List<GrantedAuthority> authorities = List.of(authority);

        long id = user.getId().longValue();
        return new UserDetailsImpl(
                id,
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Returns the authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the user's unique identifier.
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the user's email address.
     */
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null
                || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
