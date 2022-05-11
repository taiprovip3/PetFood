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

import springboot.petfood.dao.ProductDao;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.util.UserInfoUtil;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	private ProductDao productDao;
	
	@Autowired
	public ClientController(ProductDao productDao) {
		this.productDao = productDao;
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
		model.addAttribute("SESSION_USERNAME", username);
		String type = "ALL";
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCTS", products);
		return "index";
	}
	
	@GetMapping("/getTypeFoods")
	public String getTypeFoods(@RequestParam("type") String type, Model model) {
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCTS", products);
		return "index";
	}
	
	//Shop.html
	@GetMapping("/shop")
	public String showShopPage(Model model) {
		String type = "ALL";
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCTS", products);
		model.addAttribute("PRODUCT_DATA", new Product());
		return "shop";
	}
	
	@GetMapping("/filterProduct")
	public String filterProduct(@RequestParam("selectPetType") String petType, @RequestParam("selectCategory") String categoryName, Model model) {
		List<Product> products = productDao.filterProduct(petType, categoryName);
		model.addAttribute("LIST_PRODUCTS", products);
		return "shop";
	}
	
	@PostMapping("/addProductToCart")
	public String addProductToCart(@RequestParam("productId") int productId, Model model) {
		System.out.println(productId);
		return "redirect:/client/shop";
	}

	//ShowUserInfo.html
	@GetMapping("/showUserInfo")
	public String showUserInfo(Model model, Principal principal) {
		String username = principal.getName();
		org.springframework.security.core.userdetails.User logUser = (org.springframework.security.core.userdetails.User) ((Authentication) principal).getPrincipal();
		String userInfo = UserInfoUtil.toString(logUser);//Truyền vào đối tượng UserDetail trả về thuộc tính đã build
		model.addAttribute("USER_INFO", userInfo);
		return "showUserInfo";
	}

}
