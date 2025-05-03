package com.codingdojo.Cashx.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.codingdojo.Cashx.models.Invoice;
import com.codingdojo.Cashx.models.InvoicesProduct;
import com.codingdojo.Cashx.models.LoginUser;
import com.codingdojo.Cashx.models.Product;
import com.codingdojo.Cashx.models.User;
import com.codingdojo.Cashx.services.InvoiceService;
import com.codingdojo.Cashx.services.InvoicesProductService;
import com.codingdojo.Cashx.services.ProductService;
import com.codingdojo.Cashx.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	private InvoiceService invoiceServ;
    // Add once service is implemented:
     @Autowired
     private UserService userServ;
     @Autowired
     private ProductService productServ;
     @Autowired
     private InvoicesProductService invoiceProductServ;
   
    
     
    @GetMapping("/")
    public String index(Model model) {
    
        // Bind empty User and LoginUser objects to the JSP
        // to capture the form input
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index.jsp";
    }
    
    @GetMapping("/dashboard")
    public String home(Model m, HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/"; // Redirect to login if user is not logged in
        }
        
        // Assuming you have a UserService instance injected
        User user = userServ.findUserById(userId);
        
        // Get top products from the service
        List<InvoicesProduct> topProduct = invoiceProductServ.TopProduct();
        Double turnover=invoiceProductServ.LastMonth();
        
        // Add attributes to the model
        m.addAttribute("topProduct", topProduct);
        m.addAttribute("user", user);
        m.addAttribute("turnover", turnover);
        List<Invoice> invoices = invoiceServ.lastInvoice();
        
		m.addAttribute("invoices", invoices);
		List<Product> allProduct=productServ.allProduct();
 		m.addAttribute("allProduct",allProduct);
 		List<Object[]> topcl=invoiceServ.topclient();
 		m.addAttribute("topcl",topcl);
 		double total=0.0;
		for (int i = 0; i < allProduct.size(); i++) {
			total+=allProduct.get(i).getQuantity()*allProduct.get(i).getPrice();
		}
		m.addAttribute("total", total);
        
        return "dashboard.jsp"; // Return the view name
    }
     
    
    
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, 
            BindingResult result, Model model, HttpSession session) {
        
        // TO-DO Later -- call a register method in the service 
        // to do some extra validations and create a new user!
    	userServ.register(newUser, result);
        if(result.hasErrors()) {
            // Be sure to send in the empty LoginUser before 
            // re-rendering the page.
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        
        // No errors! 
        // TO-DO Later: Store their ID from the DB in session, 
        // in other words, log them in.
        session.setAttribute("user_id", newUser.getId());
        return "redirect:/dashboard";
    }
    
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
            BindingResult result, Model model, HttpSession session) {
        
        // Add once service is implemented:
        // User user = userServ.login(newLogin, result);
    	User user=userServ.login(newLogin, result);
        if(result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
    
        // No errors! 
        // TO-DO Later: Store their ID from the DB in session, 
        // in other words, log them in.
        session.setAttribute("user_id", user.getId());
        return "redirect:/dashboard";
    }
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
