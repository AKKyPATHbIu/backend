package embasa.config;

import embasa.filters.CheckFrontendVersionFilter;
import embasa.filters.EcpAuthFilter;
import embasa.security.CustomAuthenticationProvider;
import embasa.security.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(
            AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/embas").permitAll()
                    .antMatchers("/general").permitAll()
                    .antMatchers("/webhook").permitAll()
                .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic()
                .and()
                .formLogin().disable()
                .logout().disable();
        http.requiresChannel().anyRequest().requiresSecure();
//        http.addFilterBefore(checkFrontendVersionFilter(), BasicAuthenticationFilter.class);
        http.addFilterBefore(ecpAuthFilter(), BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    @Bean
    public Filter ecpAuthFilter() { return new EcpAuthFilter(); }

    @Bean
    public Filter checkFrontendVersionFilter() { return new CheckFrontendVersionFilter(); }
}
