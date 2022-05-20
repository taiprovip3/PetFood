package springboot.petfood.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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
import springboot.petfood.entity.Order;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.User;
import springboot.petfood.service.CartDao;
import springboot.petfood.service.OrderDao;
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
	private OrderDao orderDao;
	
	@Autowired
	public ClientController(ProductDao productDao, UserDao userDao, CartDao cartDao, OrderDao orderDao) {
		this.productDao = productDao;
		this.userDao = userDao;
		this.cartDao = cartDao;
		this.orderDao = orderDao;
	}

	//Index.html
	@GetMapping("/homepage")
	public String showHomepage(Model model, Principal principal2) {
		double balance = GetUserBalanceUtil.getUserBalance(principal2, userDao);
		model.addAttribute("USER_BALANCE", balance);
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
		model.addAttribute("TOTAL_PRODUCT_COUNTER", products.size());
		return "client/shop";
	}
	
	@GetMapping("/filterProduct")
	public String filterProduct(@RequestParam("selectPetType") String petType, @RequestParam("selectCategory") String categoryName, Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		List<Product> products = productDao.filterProduct(petType, categoryName);
		model.addAttribute("LIST_PRODUCT", products);
		model.addAttribute("TOTAL_PRODUCT_COUNTER", products.size());
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
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		return "client/user-info";
	}

	//CART MAPPING
	@GetMapping("/cart")
	public String showListCart(Model model, Principal principal) {
		double total=0;
		String username = principal.getName();
		if(username == null)
			return "/common/login";
		User user = userDao.findUserAccount(username);
		List<Cart> carts=cartDao.getCarts(user.getUserId());
		model.addAttribute("LIST_CART", carts);
		model.addAttribute("TOTAL_PRODUCT_COUNTER", carts.size());
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		return "client/cart";
	}
	
	@PostMapping("/cart/buy")
	public String showPageBuySuccess(@RequestParam("LIST_PRODUCT_CHECKED") List<Integer> listProductChecked, @RequestParam("TOTAL_PRICE") Double totalPrice) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
		    username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		User user = userDao.findUserAccount(username);
		double userBalance = user.getBalance();
		if(userBalance < totalPrice) {
			return "redirect:/client/cart?sufficient=false";
		} else {
			user.setBalance(userBalance - totalPrice);//Cập nhật lại tiền
			for (Integer productId : listProductChecked) {
				Product product = productDao.getProductById(productId);
				Cart cart = cartDao.getCartByProductIdAndUserId(product, user);
				
				Order order = new Order();
				order.setStatus("PENDING");
				order.setOrderDate(new Timestamp(System.currentTimeMillis()));
				Date dateNow = new Date(new Date().getTime() + 86400000);
				order.setShippedDate(new Timestamp(dateNow.getTime()));
				order.setQuantity(cart.getQuantity());
				order.setTotalPrice(cart.getPrice());
				order.setUser(user);
				order.setProduct(product);
				orderDao.saveOrder(order);//Thêm hoá đơn
				
				product.setQuantity(product.getQuantity() - 1);
				productDao.addProduct(product);//Cập nhật lại tồn kho
				cartDao.deleteCart(productId, user.getUserId());//Xoá giỏ hàng user
			}
			return "redirect:/client/cart?sufficient=true";
		}
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
	
	//ORDER MAPPING
	@GetMapping("/orders")
	public String showMyOrderPage(Model model, Principal principal) {
		double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
		model.addAttribute("LIST_ORDER", orderDao.getAllOrder());
		return "client/order";
	}
	@GetMapping("/orders/destroy")
	public String destroyOder(@RequestParam("orderId") int orderId) {
		orderDao.destroyOrderById(orderId);
		return "redirect:/client/orders";
	}
}
