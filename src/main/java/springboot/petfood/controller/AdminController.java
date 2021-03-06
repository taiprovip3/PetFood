package springboot.petfood.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import springboot.petfood.dto.CartDTO;
import springboot.petfood.dto.OrderDTO;
import springboot.petfood.dto.ProductDTO;
import springboot.petfood.dto.UserDTO;
import springboot.petfood.entity.Cart;
import springboot.petfood.entity.Category;
import springboot.petfood.entity.Order;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.service.CartDao;
import springboot.petfood.service.CategoryDao;
import springboot.petfood.service.OrderDao;
import springboot.petfood.service.ProductDao;
import springboot.petfood.service.RoleDao;
import springboot.petfood.service.UserDao;
import springboot.petfood.util.GetUserBalanceUtil;

@Controller
@RequestMapping("/admin")
public class AdminController {

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/product-img";

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;
    
    @Autowired
    CartDao cartDao;
    
    @Autowired
    OrderDao orderDao;

    @GetMapping("/homepage")
    public String showAdminPage(Model model, Principal principal) {
        double balance = GetUserBalanceUtil.getUserBalance(principal, userDao);
		model.addAttribute("USER_BALANCE", balance);
        return "admin/index.html";
    }

    //CATEGORY MAPPING
    @GetMapping("/categories")
    public String showCategoryPage(Model model) {
        model.addAttribute("LIST_CATEGORY", categoryDao.getAllCategory());
        return "admin/category";
    }

    @GetMapping("/categories/add")
    public String showFormCategoryAdd(Model model) {
        model.addAttribute("CATEGORY_DATA", new Category());
        return "admin/category-form-add";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("CATEGORY_DATA") Category category) {
        categoryDao.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryDao.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String showFormCategoryUpdate(@PathVariable int id, Model model) {
        Category category = categoryDao.getCategoryById(id);
        model.addAttribute("CATEGORY_DATA", category);
        return "admin/category-form-add";
    }

    //PRODUCT MAPPING
    @GetMapping("/products")
    public String showProductPage(Model model) {
        model.addAttribute("LIST_PRODUCT", productDao.findAllProducts("ALL"));
        return "admin/product";
    }

    @GetMapping("/products/add")
    public String showFormProductAdd(Model model) {
        model.addAttribute("PRODUCT_DATA", new ProductDTO());
        model.addAttribute("LIST_CATEGORY", categoryDao.getAllCategory());
        return "admin/product-form-add";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute("PRODUCT_DATA") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile file,
                             @RequestParam("image") String image) throws IOException {
        Product product = new Product();
        product.setProductId(productDTO.getProductId());
        product.setName(productDTO.getName());
        product.setType(productDTO.getType());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setQuantity(productDTO.getQuantity());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = image;
        }
        product.setImage(imageUUID);
        product.setCategory(categoryDao.getCategoryById(productDTO.getCategoryId()));
        productDao.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/update/{id}")
    public String showFormProductUpdate(@PathVariable int id, Model model) {
        Product product = productDao.getProductById(id);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(product.getProductId());
        productDTO.setName(product.getName());
        productDTO.setType(product.getType());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setImage(product.getImage());
        productDTO.setCategoryId(product.getCategory().getCategoryId());
        productDTO.setCarts(null);


        model.addAttribute("LIST_CATEGORY", categoryDao.getAllCategory());
        model.addAttribute("PRODUCT_DATA", productDTO);
        return "admin/product-form-add";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProductById(@PathVariable int id) {
        productDao.removeProductById(id);
        return "redirect:/admin/products";
    }

    //ROLE MAPPING
    @GetMapping("/roles")
    public String showRolePage(Model model) {
        model.addAttribute("LIST_ROLE", roleDao.getAllRole());
        return "admin/role";
    }
    
    @PostMapping("/roles")
    public String addRole(@ModelAttribute("ROLE_DATA") Role role) {
    	roleDao.saveRole(role);
    	return "redirect:/admin/roles";
    }
    
    @GetMapping("/roles/add")
    public String showFormRoleAdd(Model model) {
    	model.addAttribute("ROLE_DATA", new Role());
    	return "admin/role-form-add";
    }
    
    @GetMapping("/roles/update")
    public String showFormRoleUpdate(@RequestParam("roleId") int roleId, Model model) {
    	Role role = roleDao.getRoleById(roleId);
    	model.addAttribute("ROLE_DATA", role);
    	return "admin/role-form-add";
    }
    
    @GetMapping("/roles/delete")
    public String deleteRole(@RequestParam("roleId") int roleId) {
    	roleDao.deleteRoleById(roleId);
    	return "redirect:/admin/roles";
    }
    
    //USER MAPPING
    @GetMapping("/users")
    public String showUserPage(Model model) {
        model.addAttribute("LIST_USER", userDao.getAllUser());
        return "admin/user";
    }

    @GetMapping("/users/add")
    public String showFormUserAdd(Model model) {
        model.addAttribute("USER_DATA", new UserDTO());
        model.addAttribute("LIST_ROLE", roleDao.getAllRole());
        return "admin/user-form-add";
    }

    @PostMapping("/users/add")
    public String addUser(@ModelAttribute("USER_DATA") UserDTO userDTO) {
    	String oldPassword = "";
    	if(userDTO.getUserId() != 0) {
    		User temp = userDao.getUserById(userDTO.getUserId());
        	oldPassword = temp.getPassword();
    	}
    	User user = new User();
    	user.setUserId(userDTO.getUserId());
    	user.setUsername(userDTO.getUsername());
    	user.setPassword(userDTO.getPassword());
    	user.setEmail(userDTO.getEmail());
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());
	    user.setBalance(userDTO.getBalance());
	    user.setAddress(userDTO.getAddress());

	    Role role = roleDao.getRoleById(userDTO.getRoleId());
	    userDao.saveUser(user, role, oldPassword);
 
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable int id) {
        userDao.removeUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/update")
    public String showFormUserUpdate(@RequestParam("id") int userid, Model model) {
    	
        User user = userDao.getUserById(userid);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setBalance(user.getBalance());
        userDTO.setAddress(user.getAddress());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleId(user.getRole().getRoleId());

        model.addAttribute("USER_DATA", userDTO);
        model.addAttribute("LIST_ROLE", roleDao.getAllRole());
        return "admin/user-form-add";
    }

    //CART MAPPING
    @GetMapping("/carts")
    public String showCartPage(Model model) {
        model.addAttribute("LIST_CART", cartDao.getAllCart());
        return "admin/cart";
    }
    
    @GetMapping("/carts/formAdd")
    public String showFromCartAdd(Model model) {
        model.addAttribute("CART_DATA", new CartDTO());
        model.addAttribute("LIST_USER", userDao.getAllUser());
        model.addAttribute("LIST_PRODUCT", productDao.findAllProducts("ALL"));
        return "admin/cart-form-add";
    }
    
    @PostMapping("/carts")
    public String addCart(@ModelAttribute("CART_DATA") CartDTO cartDTO) {
    	cartDao.updateCart(productDao.getProductById(cartDTO.getProductId()), userDao.getUserById(cartDTO.getUserId()), cartDTO.getQuantity());
    	return "redirect:/admin/carts";
    }
    
    @GetMapping("/carts/update")
    public String showFromCartUpdate(@RequestParam("productId") int productId, @RequestParam("userId") int userId, Model model) {
    	Product product = productDao.getProductById(productId);
    	User user = userDao.getUserById(userId);
    	Cart cart = cartDao.getCartByProductIdAndUserId(product, user);
    	CartDTO cartDTO = new CartDTO();
    	cartDTO.setProductId(productId);
    	cartDTO.setUserId(userId);
    	cartDTO.setQuantity(cart.getQuantity());
    	cartDTO.setPrice(cart.getPrice());
    	model.addAttribute("CART_DATA", cartDTO);
    	model.addAttribute("LIST_USER", userDao.getAllUser());
        model.addAttribute("LIST_PRODUCT", productDao.findAllProducts("ALL"));
        return "admin/cart-form-add";
    }
    
    @GetMapping("/carts/delete")
    public String deleteCart(@RequestParam("productId") int productId, @RequestParam("userId") int userId) {
    	cartDao.deleteCart(productId, userId);
    	return "redirect:/admin/carts";
    }
    
    //ORDER MAPPING
    @GetMapping("/orders")
    public String showOrderPage(Model model) {
    	model.addAttribute("LIST_ORDER", orderDao.getAllOrder());
    	return "admin/order";
    }
    @PostMapping("/orders")
    public String updateOrder(@ModelAttribute("ORDER_DATA") OrderDTO orderDTO) {
    	User user = userDao.getUserById(orderDTO.getUserId());
    	user.setAddress(orderDTO.getAddress());
    	Order order = new Order();
    	order.setOrderId(orderDTO.getOrderId());
    	order.setStatus(orderDTO.getStatus());
    	order.setOrderDate(orderDTO.getOrderDate());
    	order.setShippedDate(orderDTO.getShippedDate());
    	order.setQuantity(orderDTO.getQuantity());
    	order.setTotalPrice(orderDTO.getTotalPrice());
    	order.setUser(user);
    	order.setProduct(productDao.getProductById(orderDTO.getProductId()));
    	orderDao.saveOrder(order);
    	return "redirect:/admin/orders";
    }
    @GetMapping("/orders/commit")
    public String commitOrder(@RequestParam("orderId") int orderId) {
    	Order order = orderDao.getOrderById(orderId);
    	order.setStatus("COMMITED");
    	orderDao.saveOrder(order);
    	return "redirect:/admin/orders";
    }
    @GetMapping("/orders/update")
    public String showFormOrderUpdate(@RequestParam("orderId") int orderId, Model model) {
    	Order order = orderDao.getOrderById(orderId);
    	OrderDTO orderDTO = new OrderDTO();
    	orderDTO.setOrderId(orderId);
    	orderDTO.setStatus(order.getStatus());
    	orderDTO.setOrderDate(order.getOrderDate());
    	orderDTO.setShippedDate(order.getShippedDate());
    	orderDTO.setAddress(order.getUser().getAddress());
    	orderDTO.setQuantity(order.getQuantity());
    	orderDTO.setTotalPrice(order.getTotalPrice());
    	orderDTO.setUserId(order.getUser().getUserId());
    	orderDTO.setProductId(order.getProduct().getProductId());
    	model.addAttribute("ORDER_DATA", orderDTO);
    	return "admin/order-form-add";
    }
    @GetMapping("/orders/destroy")
    public String destroyOrder(@RequestParam("orderId") int orderId) {
    	orderDao.destroyOrderById(orderId);
    	return "redirect:/admin/orders";
    }
}