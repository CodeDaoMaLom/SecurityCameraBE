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
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableSwagger2
@RequiredArgsConstructor
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Value("${tech.thanhpham.secret-key}")
    public static String secretKey;
    public static String[] roleMember = {"Member", "Admin", "Owner"};
    public static String[] roleAdmin = {"Admin", "Owner"};
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs"
    };

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
        http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();


        http.exceptionHandling().authenticationEntryPoint(
                (httpServletRequest, httpServletResponse, e)
                        -> httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
