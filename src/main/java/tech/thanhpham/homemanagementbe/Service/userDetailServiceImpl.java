package tech.thanhpham.homemanagementbe.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.thanhpham.homemanagementbe.Entity.Account;
import tech.thanhpham.homemanagementbe.Entity.Role;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class userDetailServiceImpl implements UserDetailsService {
    private final accountService accountService;

    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
        Username = Username.toLowerCase();
        Account account = accountService.findByUsername(Username);
        if (account == null) {
            System.out.println("User not found!" + Username);
            throw new UsernameNotFoundException("User " + Username + " was not found in the database");
        }
        System.out.println("Found user!" + Username);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), getRole(account));
        System.out.println(userDetails);
        return userDetails;
    }

    public Set<SimpleGrantedAuthority> getRole(Account account) {
        Role role = account.getRole();
        return Collections.singleton(new SimpleGrantedAuthority(role.getRole_name()));
    }
}
