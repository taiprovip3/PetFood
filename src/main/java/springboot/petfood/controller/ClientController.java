package springboot.petfood.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.petfood.entity.Cart;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.service.CartDao;
import springboot.petfood.service.ProductDao;
import springboot.petfood.service.UserDao;
import springboot.petfood.util.GetUserBalanceUtil;
import springboot.petfood.util.GetUserDetailService;
import springboot.petfood.util.UserRolesBuilderUtil;

@Controller
@RequestMapping("/client")
public class ClientController {
	
	private boolean autoRedirectAdminPage = true;
	
	private ProductDao productDao;
	private UserDao userDao;
	private CartDao cartDao;
	
	@Autowired
	public ClientController(ProductDao productDao, UserDao userDao, CartDao cartDao) {
		this.productDao = productDao;
		this.userDao = userDao;
		this.cartDao=cartDao;
	}

	//Index.html
	@GetMapping("/homepage")
	public String showHomepage(Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
		    username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		if(!username.equals("anonymousUser")) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
				if(autoRedirectAdminPage) {
					autoRedirectAdminPage = false;
					return "admin/index";
				}
			}
		}
		
		String type = "ALL";
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCT", products);
		return "client/index";
	}
	
	@GetMapping("/getTypeFoods")
	public String getTypeFoods(@RequestParam("type") String type, Model model) {
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCT", products);
		
		return "client/index";
	}
	
	//Shop.html
	@GetMapping("/shop")
	public String showShopPage(Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		String type = "ALL";
		List<Product> products = productDao.findAllProducts(type);
		model.addAttribute("LIST_PRODUCT", products);
		return "client/shop";
	}
	
	@GetMapping("/filterProduct")
	public String filterProduct(@RequestParam("selectPetType") String petType, @RequestParam("selectCategory") String categoryName, Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		List<Product> products = productDao.filterProduct(petType, categoryName);
		model.addAttribute("LIST_PRODUCT", products);
		return "client/shop";
	}
	
	@PostMapping("/addProductToCart")
	public String addProductToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Principal principal) {
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		Product p = productDao.getProductById(productId);
		User u = userDao.findUserAccount(username);
		productDao.addProductToCart(p, u, quantity);
		return "redirect:/client/shop";
	}
	
	@GetMapping("/products")
	public String showProductDetail(@RequestParam("productId") int productId, Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		Product p = productDao.getProductById(productId);
		model.addAttribute("PRODUCT_DATA", p);
		model.addAttribute("PRODUCT_FORM", new Product());
		return "client/product-detail";
	}
	
	//user-info
	@GetMapping("/user-info")
	public String showUserInfo(Model model, Principal principal) {
		org.springframework.security.core.userdetails.User loggedUser = GetUserDetailService.getUserDetails(principal);//Lấy UserDetails của user đang logged
		String userRoles = UserRolesBuilderUtil.toString(loggedUser);//Truyền vào đối tượng UserDetail trả về thuộc tính đã build
		model.addAttribute("USER_ROLES", userRoles);
		String username = loggedUser.getUsername();
		model.addAttribute("USER_DATA", userDao.findUserAccount(username));
		return "client/user-info";
	}

	//CART MAPPING
	@GetMapping("/cart")
	public String showListCart(Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		double total=0;
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		User user = userDao.findUserAccount(username);
		List<Cart> carts=cartDao.getCarts(user.getUserId());
		if(carts!=null)
			for (Cart cart : carts)
				total+=cart.getPrice();
		model.addAttribute("TOTAL", total);
		model.addAttribute("LIST_CART", carts);
		return "client/cart";
	}
	
	@GetMapping("/cart/delete")
	public String deleteCart(@RequestParam("productId") int productId, Principal principal){
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		User user = userDao.findUserAccount(username);
		cartDao.deleteCart(productId, user.getUserId());
		return "redirect:/client/cart";
	}

	@PostMapping("/cart/update")
	public String updateCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Principal principal){
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		User user = userDao.findUserAccount(username);
		Product product = productDao.getProductById(productId);
		cartDao.updateCart(product, user, quantity);
		return "redirect:/client/cart";
	}
}
