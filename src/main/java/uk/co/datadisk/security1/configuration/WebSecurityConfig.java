package uk.co.datadisk.security1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private BespokeAuthenticationProvider bespokeAuthenticationProvider;

    public WebSecurityConfig(BespokeAuthenticationProvider bespokeAuthenticationProvider) {
        this.bespokeAuthenticationProvider = bespokeAuthenticationProvider;
    }

//    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("*** - Inside WebSecurityConfig(SecurityConfig1) configure method -- **** ");
        //super.configure(http);

        http
                .authenticationProvider(bespokeAuthenticationProvider)
                .authorizeRequests()
                    .antMatchers("/users/**").hasAuthority("ADMIN")
                    .antMatchers("/**").hasAnyAuthority("USER")

                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .failureUrl("/login?error")
                    .and()
                .logout()
                    .permitAll()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/login?logout")
                    .and()
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .exceptionHandling()
                    .accessDeniedPage("/login")
                    .and()
                .csrf()
                    .disable();
    }

    // Used so that users dont have to authenticate for the below resources
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/images/**", "/js/**", "/css/**");
    }
}