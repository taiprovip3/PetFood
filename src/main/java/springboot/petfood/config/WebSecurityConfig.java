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

import springboot.petfood.service.UserDetailsServiceImpl;

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
		web.ignoring().antMatchers("/resources/**", "/static/**", "/img/**", "/css/**", "/js/**", "/jQuery/**");
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/client/homepage", "/common/login", "/login","/common/register", "/logout").permitAll(); //cho phép tất cả truy cập, ko cần login
        http.authorizeRequests().antMatchers("/admin/homepage").access("hasAnyRole('ADMIN')");//truy cập index cần có role
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
        http.authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
	                    .formLogin() //Cho phép người dùng xác thực bằng form login
	                    .loginProcessingUrl("/SPRING_BOOT_CHECK_LOGIN")//Đường dẫn gọ springboot sử lý
	                    .loginPage("/common/login")
	                    .defaultSuccessUrl("/client/homepage")//Sau khi login success chuyển đến
	                    .failureUrl("/common/login?error=true")
	                    .permitAll()
                    .and()
	                    .logout()
	                    .permitAll();
    }
}
