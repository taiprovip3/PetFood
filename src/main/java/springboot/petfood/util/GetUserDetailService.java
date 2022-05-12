package springboot.petfood.util;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public class GetUserDetailService {
	public static User getUserDetails(Principal principal) {
		User loggedUser = (User) ((Authentication) principal).getPrincipal();
		return loggedUser;
	}
}
