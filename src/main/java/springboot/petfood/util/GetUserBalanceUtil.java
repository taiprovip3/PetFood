package springboot.petfood.util;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;

import springboot.petfood.entity.User;
import springboot.petfood.service.UserDao;

public class GetUserBalanceUtil {
	
	public static double getUserBalance(Principal principal, UserDao userDao) {
		
		org.springframework.security.core.userdetails.User loggedUser = null;
		double balance = 0.0;
		
		loggedUser = GetUserDetailService.getUserDetails(principal);
		if(loggedUser != null) {
			String username = loggedUser.getUsername();
			User u = userDao.findUserAccount(username);
			balance = u.getBalance();
		}
		
		return balance;
	}
}
