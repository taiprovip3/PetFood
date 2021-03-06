package springboot.petfood.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import springboot.petfood.dao.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/img/**", "/css/**", "/js/**", "/jQuery/**", "/product-img/**");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").denyAll()
			.antMatchers("/logout", "/login", "/common/login", "/common/register", "/client/homepage").permitAll()
			.antMatchers("/client/user-info", "/client/cart", "/client/order").hasAnyAuthority("MEMBER", "ADMIN")
			.antMatchers("/admin/**").hasAnyAuthority("ADMIN")
			.and()
            .formLogin()
            	.loginPage("/common/login")
            	.loginProcessingUrl("/SPRING_BOOT_CHECK_LOGIN")
                .defaultSuccessUrl("/client/homepage")
                .failureUrl("/common/login?error=true")
                .permitAll()
		        .and()
		            .logout().permitAll()
				.and()
					.exceptionHandling().accessDeniedPage("/common/403");
        http.csrf().disable();
    }
}
