package springboot.petfood.util;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserInfoUtil {
	public static String toString(User user) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Username :: ").append(user.getUsername());
		
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		if(authorities != null && !authorities.isEmpty()) {
			sb.append(" (");
			boolean first = true;
			for (GrantedAuthority grantedAuthority : authorities) { //Mỗi role trong tổng roles
				if(first) {
					sb.append(grantedAuthority.getAuthority());
				} else
					sb.append(", ").append(grantedAuthority.getAuthority());//Khoản cách giữa mỗi role '(MEMBER, ADMIN)'
			}//end For
			sb.append(")");
		}
		
		return sb.toString();
	}
}
