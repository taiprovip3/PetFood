package springboot.petfood.dao;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{//Lớp này dc gọi tự động bởi spring security
	
	@Autowired
	private UserDaoJpaImpl userDaoJpaImpl;
	
	@Autowired
	private RoleDaoJpaImpl roleDaoJpaImpl;
	
	@Override
	@Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        springboot.petfood.entity.User user = userDaoJpaImpl.findUserAccount(username);
        if (user == null) {
        	System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        List<String> roles = roleDaoJpaImpl.getUserRoles(user.getUserId());
        for (String role : roles) {
        	GrantedAuthority authority = new SimpleGrantedAuthority(role);//Tạo 1 phân quyền có tên role
            grantedAuthorities.add(authority);
        }
      
        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        return userDetails;
    }
}
