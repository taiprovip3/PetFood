package springboot.petfood.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.petfood.dao.UserDaoJpaImpl;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.service.ProductDao;
import springboot.petfood.service.UserDao;
import springboot.petfood.util.GetUserDetailService;
import springboot.petfood.util.UserRolesBuilderUtil;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	private ProductDao productDao;
	private UserDao userDao;
	
	@Autowired
	public ClientController(ProductDao productDao, UserDao userDao) {
		this.productDao = productDao;
		this.userDao = userDao;
	}

	//Index.html
	@GetMapping("/homepage")
	public String showHomepage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		User user = new User();
		if (principal instanceof UserDetails) {
		    username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("USER_DATA", username);
		String type = "ALL";
		List<Product> products = productDao.findAllProduct(type);
		model.addAttribute("LIST_PRODUCTS", products);
		return "index";
	}
	
	@GetMapping("/getTypeFoods")
	public String getTypeFoods(@RequestParam("type") String type, Model model) {
		List<Product> products = productDao.findAllProduct(type);
		model.addAttribute("LIST_PRODUCTS", products);
		return "index";
	}
	
	//Shop.html
	@GetMapping("/shop")
	public String showShopPage(Model model) {
		String type = "ALL";
		List<Product> products = productDao.findAllProduct(type);
		model.addAttribute("LIST_PRODUCTS", products);
		return "shop";
	}
	
	@GetMapping("/filterProduct")
	public String filterProduct(@RequestParam("selectPetType") String petType, @RequestParam("selectCategory") String categoryName, Model model) {
		List<Product> products = productDao.filterProduct(petType, categoryName);
		model.addAttribute("LIST_PRODUCTS", products);
		return "shop";
	}
	
	@GetMapping("/addProductToCart")
	public String addProductToCart(@RequestParam("productId") int id, Principal principal, Model model) {
		Product p = productDao.getProductById(id);
		String username = null;
		try {
			username = principal.getName();
		} catch (Exception e) {
			return "redirect:/common/login?error=true";
		}
		User u = userDao.findUserAccount(username);
		productDao.addProductToCart(p, u);
		return "redirect:/client/shop";
	}
	@GetMapping("/products")
	public String showProductDetail(@RequestParam("productId") int productId, Model model) {
		Product p = productDao.getProductById(productId);
		model.addAttribute("PRODUCT_DATA", p);
		model.addAttribute("PRODUCT_FORM", new Product());
		return "product-detail";
	}

	//ShowUserInfo.html
	@GetMapping("/showUserInfo")
	public String showUserInfo(Model model, Principal principal) {
		org.springframework.security.core.userdetails.User loggedUser = GetUserDetailService.getUserDetails(principal);//Lấy UserDetails của user đang logged
		String userRoles = UserRolesBuilderUtil.toString(loggedUser);//Truyền vào đối tượng UserDetail trả về thuộc tính đã build
		model.addAttribute("USER_ROLES", userRoles);
		String username = loggedUser.getUsername();
		model.addAttribute("USER_DATA", userDao.findUserAccount(username));
		return "showUserInfo";
	}

}
