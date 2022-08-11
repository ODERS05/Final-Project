package kg.itacademy.sewerfactory.config;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public WebSecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT t.user_name, t.password, t.is_active FROM users t WHERE t.user_name = ?")
                .authoritiesByUsernameQuery(
                        "select\n" +
                                "\tu.user_name,\n" +
                                "\tr.roles\n" +
                                "from\n" +
                                "\tuser_role ur\n" +
                                "inner join users u \n" +
                                "                                on\n" +
                                "\tur.user_id = u.id\n" +
                                "inner join role r\n" +
                                "                                on\n" +
                                "\tur.role_id = r.id\n" +
                                "where\n" +
                                "\tu.user_name = ?\n" +
                                "\tand u.is_active = true");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/users/*").permitAll()
                .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/*").permitAll()

                .antMatchers(HttpMethod.GET,"/sewer/add-sewer").hasRole("SEWER")
                .antMatchers(HttpMethod.GET, "/sewer/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/sewer").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/sewer/*").hasAnyRole("ADMIN", "SEWER")
                .antMatchers(HttpMethod.DELETE, "/sewer/*").hasAnyRole("ADMIN", "SEWER")

                .antMatchers(HttpMethod.POST, "/role").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/role").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/role/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/order/*").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.GET, "/order").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/order/*").hasAnyRole("ADMIN", "CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/order/*").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers(HttpMethod.POST, "/order/add-order").hasRole("CUSTOMER")

                .antMatchers(HttpMethod.POST, "/customer/add-customer").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers(HttpMethod.PUT, "/customer/*").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers(HttpMethod.GET, "/customer").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers(HttpMethod.GET, "/customer/*").hasAnyRole("ADMIN","CUSTOMER")
                .antMatchers(HttpMethod.DELETE, "/customer/*").hasAnyRole("ADMIN","CUSTOMER")

                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
