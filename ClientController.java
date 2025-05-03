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

import com.codingdojo.Cashx.models.Client;
import com.codingdojo.Cashx.models.User;
import com.codingdojo.Cashx.services.ClientsService;
import com.codingdojo.Cashx.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/clients")
public class ClientController {
	 @Autowired
     private UserService userServ;
     @Autowired
     private ClientsService CliServ;
     
     @GetMapping("")
     public String welcome(Model m,HttpSession session) {
    	 Long userId = (Long) session.getAttribute("user_id");
 		if(userId == null) {
 			return "redirect:/";
 		}
 		User user = userServ.findUserById(userId);
 		List<Client> allClients = CliServ.allClients();
		m.addAttribute("allClients",allClients);
		m.addAttribute("user",user);
         return "clients2.jsp";
     }
     
     @GetMapping("/new")
     public String addClient(@ModelAttribute("Client") Client Client,HttpSession session) {
    	 Long userId = (Long) session.getAttribute("user_id");
  		if(userId == null) {
  			return "redirect:/";
  		}
    	 return "newclient.jsp";
     }
     @PostMapping("/processClient")
 	public String createClient(@Valid @ModelAttribute("Client") Client Client,BindingResult result,Model model,HttpSession s) {
    	 Long userId = (Long) s.getAttribute("user_id");
	  		if(userId == null) {
	  			return "redirect:/";
	  		}
    	 if (result.hasErrors()) {
 			return "newclient.jsp";
 		}else {
			// grab the user by their id
			User user = userServ.findUserById(userId);
			Client.setUser(user);
 			Client newClient = CliServ.createClient(Client);
 			return "redirect:/clients";
 		}}
     @GetMapping("/{id}")
     public String oneClient(Model model, @PathVariable("id") Long id,HttpSession session) {
    	 Long userId = (Long) session.getAttribute("user_id");
  		if(userId == null) {
  			return "redirect:/";
  		}
    	 User user = userServ.findUserById(userId);
 		model.addAttribute("user",user);
    	 Client selectedClient=CliServ.findClientById(id);
    	 model.addAttribute("Client",selectedClient);
    	 return "showclient.jsp";
     }
     @DeleteMapping("/delete/{id}")
 		public String deleteClient(@PathVariable("id") Long id,HttpSession session) {
    	 Long userId = (Long) session.getAttribute("user_id");
  		if(userId == null) {
  			return "redirect:/";
  		}
  		CliServ.deleteClient(id);
 		return "redirect:/clients";
 	}
     @GetMapping("/edit/{id}")
		public String getMethodName(Model model, @PathVariable("id") Long id,HttpSession session) {
    	 Long userId = (Long) session.getAttribute("user_id");
  		if(userId == null) {
  			return "redirect:/";
  		}
		
			Client selected = CliServ.findClientById(id);
			model.addAttribute("Client",selected);
			return "updateclient.jsp";
		}
		
		@PutMapping("/update/{id}")
		public String editExp(@Valid @ModelAttribute("Client") Client Client, BindingResult result,HttpSession s) {
			Long userId = (Long) s.getAttribute("user_id");
		  		if(userId == null) {
		  			return "redirect:/";
		  		}
				
			if(result.hasErrors()) {
				return "updateclient.jsp";
			}else {
				
				// grab the user by their id
				User user = userServ.findUserById(userId);
				Client.setUser(user);
				CliServ.createClient(Client);
			
				return "redirect:/clients";
			}
		}
}