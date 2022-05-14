package springboot.petfood.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.petfood.entity.Cart;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.service.CartDao;
import springboot.petfood.service.ProductDao;
import springboot.petfood.service.UserDao;
import springboot.petfood.util.GetUserDetailService;
import springboot.petfood.util.UserRolesBuilderUtil;

@Controller
@RequestMapping("/client")
public class ClientController {
	
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
		User user = new User();
		if (principal instanceof UserDetails) {
		    username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		model.addAttribute("USER_DATA", username);
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
		return "shop";
	}
	
	@GetMapping("/filterProduct")
	public String filterProduct(@RequestParam("selectPetType") String petType, @RequestParam("selectCategory") String categoryName, Model model) {
		List<Product> products = productDao.filterProduct(petType, categoryName);
		model.addAttribute("LIST_PRODUCTS", products);
		return "shop";
	}
	
	@PostMapping("/addProductToCart")
	public String addProductToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Principal principal, Model model) {
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		Product p = productDao.getProductById(productId);
		User u = userDao.findUserAccount(username);
		productDao.addProductToCart(p, u, quantity);
		return "redirect:/client/shop";
	}
	@GetMapping("/products")
	public String showProductDetail(@RequestParam("productId") int productId, Model model) {
		Product p = productDao.getProductById(productId);
		model.addAttribute("PRODUCT_DATA", p);
		model.addAttribute("PRODUCT_FORM", new Product());
		return "product-detail";
	}

	@GetMapping("/cart")
	public String showListCart(Model model, Principal principal) {
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		User user = userDao.findUserAccount(username);
		List<Cart> carts=cartDao.getCarts(user.getUserId());
		model.addAttribute("LIST_CART", carts);
		return "listCart";
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
