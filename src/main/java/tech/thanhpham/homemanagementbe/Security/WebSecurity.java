package tech.thanhpham.homemanagementbe.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Value("${tech.thanhpham.secret-key}")
    public static String secretKey;
    public static String[] roleMember = {"Member", "Admin", "Owner"};
    public static String[] roleAdmin = {"Admin", "Owner"};

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors();

        http.authorizeRequests().antMatchers(HttpMethod.POST,"/account/*").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/passcode/get").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/passcode/set").permitAll();
        http.authorizeRequests().antMatchers("/image-verify/*").permitAll();


        http.exceptionHandling().authenticationEntryPoint(
                (httpServletRequest, httpServletResponse, e)
                        -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }
}
