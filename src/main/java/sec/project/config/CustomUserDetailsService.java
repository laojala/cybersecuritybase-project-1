package sec.project.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private Map<String, String> accountDetails;

    @PostConstruct
    public void init() {
        // this data would typically be retrieved from a database
        this.accountDetails = new TreeMap<>();
        this.accountDetails.put("ted", "password");
        this.accountDetails.put("admin", "admin");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!this.accountDetails.containsKey(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));
        if (username.equals("admin")) {
            roles.add(new SimpleGrantedAuthority("ADMIN"));
        }

        return new org.springframework.security.core.userdetails.User(
                username,
                this.accountDetails.get(username),
                true,
                true,
                true,
                true,
                roles);
    }
}
