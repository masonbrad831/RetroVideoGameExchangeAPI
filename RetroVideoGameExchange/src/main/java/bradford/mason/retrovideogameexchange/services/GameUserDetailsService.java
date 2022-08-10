package bradford.mason.retrovideogameexchange.services;

import bradford.mason.retrovideogameexchange.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;


public class GameUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo users;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.findById(username).orElse(null);

    }

}
