package springboot.petfood.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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

import springboot.petfood.dto.ProductDTO;
import springboot.petfood.dto.UserDTO;
import springboot.petfood.entity.Category;
import springboot.petfood.entity.Product;
import springboot.petfood.entity.Role;
import springboot.petfood.entity.User;
import springboot.petfood.service.CategoryDao;
import springboot.petfood.service.ProductDao;
import springboot.petfood.service.RoleDao;
import springboot.petfood.service.UserDao;

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

    @GetMapping("/homepage")
    public String showAdminPage(Model model) {
        return "admin";
    }

    //CATEGORY MAPPING
    @GetMapping("/categories")
    public String showCategoryPage(Model model) {
        model.addAttribute("LIST_CATEGORY", categoryDao.getAllCategory());
        return "category";
    }

    @GetMapping("/categories/add")
    public String showFormCategoryAdd(Model model) {
        model.addAttribute("CATEGORY_DATA", new Category());
        return "category-form-add";
    }

    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("CATEGORY_DATA") Category category) {//Phương thức này gọi hàm merge tự xác định save or update
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
        return "category-form-add";
    }

    //PRODUCT MAPPING
    @GetMapping("/products")
    public String showProductPage(Model model) {
        model.addAttribute("LIST_PRODUCT", productDao.findAllProducts("ALL"));
        return "product";
    }

    @GetMapping("/products/add")
    public String showFormProductAdd(Model model) {
        model.addAttribute("PRODUCT_DATA", new ProductDTO());
        model.addAttribute("LIST_CATEGORY", categoryDao.getAllCategory());
        return "product-form-add";
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

    @GetMapping("/product/update/{id}")
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
        return "product-form-add";
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
        return "role";
    }

    //USER MAPPING
    @GetMapping("/users")
    public String showUserPage(Model model) {
        model.addAttribute("LIST_USER", userDao.getAllUser());
//        System.out.println(userDao.getAllUser());
        return "User";
    }

    @GetMapping("/users/add")
    public String showFormUserAdd(Model model) {
        model.addAttribute("USER_DATA", new UserDTO());
        model.addAttribute("LIST_ROLE", roleDao.getAllRole());
        return "user-form-add";
    }

    @PostMapping("/users/add")
    public String addProduct(@ModelAttribute("USER_DATA") UserDTO userDTO) {
    	
    	User user = new User();
    	user.setUserId(userDTO.getUserId());
    	user.setUsername(userDTO.getUsername());
    	user.setPassword(userDTO.getPassword());
    	user.setEmail(userDTO.getEmail());
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());

	    Role role = roleDao.getRoleById(userDTO.getRoleId());
	    
	    userDao.saveUser(user, role);
 
        return "redirect:/admin/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable int id) {
        userDao.removeUserById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/update")
    public String showFormUserUpdate(@RequestParam("id") int id, Model model) {
    	
        User user = userDao.getUserById(id);

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUsername(user.getUsername());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoleId(user.getRole().getRoleId());

        model.addAttribute("USER_DATA", userDTO);
        model.addAttribute("LIST_ROLE", roleDao.getAllRole());
        return "user-form-add";
    }


}