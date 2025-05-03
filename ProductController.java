package com.codingdojo.Cashx.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codingdojo.Cashx.models.Product;
import com.codingdojo.Cashx.models.User;
import com.codingdojo.Cashx.services.ProductService;
import com.codingdojo.Cashx.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productServ;

	@Autowired
	private UserService userServ;
	
  @GetMapping("")
    public String home(Model m,HttpSession session) {
    	Long userId = (Long) session.getAttribute("user_id");
 		if(userId == null) {
 			return "redirect:/";
 		}
 		User user = userServ.findUserById(userId);
 		List<Product> allProduct=productServ.allProduct();
 		m.addAttribute("allProduct",allProduct);
		m.addAttribute("user",user);
         return "home.jsp";
     }


	@GetMapping("/new")
	public String addProduct(@ModelAttribute("product") Product product, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		return "newProduct.jsp";
	}

	@PostMapping("/processProduct")
	public String processProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, Model model,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			return "newProduct.jsp";
		} else {
			// grab the user by their id
			
			Product newProduct = productServ.createProduct(product);
			return "redirect:/dashboard";
		}
	}

	@GetMapping("/edit/{id}")
	public String getMethodName(Model model, @PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User user = userServ.findUserById(userId);
		model.addAttribute("user", user);
		Product selected = productServ.findProduct(id);
		model.addAttribute("product", selected);
		return "edit.jsp";
	}

	@PutMapping("/editProduct/{id}")
	public String editProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, HttpSession s) {
		Long userId = (Long) s.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}

		if (result.hasErrors()) {
			return "edit.jsp";
		} else {

			productServ.createProduct(product);

			return "redirect:/dashboard";
		}
	}

	@GetMapping("/{id}")
	public String oneProduct(@PathVariable("id") Long id, Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		
		
		User user = userServ.findUserById(userId);
		model.addAttribute("user", user);
		Product selected = productServ.findProduct(id);
		model.addAttribute("product", selected);
		return "oneProduct.jsp";
	}

	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		productServ.deleteProduct(id);
		return "redirect:/dashboard";
	}

}
