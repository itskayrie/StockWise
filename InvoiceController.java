package com.codingdojo.Cashx.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.Cashx.models.Client;
import com.codingdojo.Cashx.models.Invoice;
import com.codingdojo.Cashx.models.InvoicesProduct;
import com.codingdojo.Cashx.models.Product;
import com.codingdojo.Cashx.models.User;
import com.codingdojo.Cashx.services.ClientsService;
import com.codingdojo.Cashx.services.InvoiceService;
import com.codingdojo.Cashx.services.InvoicesProductService;
import com.codingdojo.Cashx.services.ProductService;
import com.codingdojo.Cashx.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {


	@Autowired
	private InvoiceService invoiceServ;
	@Autowired
	private InvoicesProductService invoiceProductServ;

	@Autowired
	private ProductService productServ;
	@Autowired
	private UserService userServ;
	@Autowired
	private ClientsService clientServ;

	@GetMapping("")
	public String getAllInvoices(Model model,HttpSession session) {
    	Long userId = (Long) session.getAttribute("user_id");
    	User user = userServ.findUserById(userId);
 		if(userId == null) {
 			return "redirect:/";
 		}
		List<Invoice> invoices = invoiceServ.allInvoice();
		model.addAttribute("invoices", invoices);
		model.addAttribute("user",user);
		return "invoices.jsp";
	}

	@GetMapping("/{id}")
	public String oneInvoice(Model model, @PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		User user = userServ.findUserById(userId);
		model.addAttribute("user", user);
		Invoice selected = invoiceServ.findInvoice(id);
		model.addAttribute("invoice", selected);
		List<InvoicesProduct> list= invoiceProductServ.allproductbyInvoice(id);
		model.addAttribute("list", list);
		double total=0.0;
		for (int i = 0; i < list.size(); i++) {
			total+=list.get(i).getProductQuantity()*list.get(i).getProduct().getPrice();
		}
		model.addAttribute("total", total);  
		return "showinvoice.jsp";
	}

	// Affiche le formulaire pour créer une nouvelle facture
	@GetMapping("/new")
	public String showNewInvoiceForm(Model model, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			// Redirige vers la page de connexion si l'utilisateur n'est pas connecté
			return "redirect:/";
		}
		List<Client> clients = clientServ.allClients();
		List<Product> products = productServ.allProduct();
		model.addAttribute("clients", clients);
		model.addAttribute("products", products);
		return "newinvoice.jsp";
	}

	/////////////////////////
	@PostMapping("/new")
	public String createInvoice(@RequestParam Long clientId,
	                            @RequestParam(name = "productIds[]") List<Long> productIds,
	                            @RequestParam(name = "quantities[]") List<Integer> quantities,
	                            HttpSession session) {

	    Long userId = (Long) session.getAttribute("user_id");
	    if (userId == null) {
	        return "redirect:/"; // Redirige vers la page de connexion si l'utilisateur n'est pas connecté
	    }

	    // Récupérer le créateur de la facture
	    User creator = userServ.findUserById(userId);
	    // Récupérer le client associé à la facture
	    Client client = clientServ.findClientById(clientId);

	    // Créer la facture sans les produits initialement
	    Invoice invoice = new Invoice();
	    invoice.setClient(client);
	    invoice.setCreator(creator);
	    invoice = invoiceServ.createInvoice(invoice); // Enregistrez la facture et récupérez-la avec l'ID généré

	    // Liste pour stocker les produits associés à la facture
	    List<InvoicesProduct> invoiceProducts = new ArrayList<>();

	    // Parcourir les listes de produits et de quantités
	    for (int i = 0; i < productIds.size(); i++) {
	        Long productId = productIds.get(i);
	        Integer qte = quantities.get(i);

	        // Récupérer le produit à partir de l'identifiant
	        Product product = productServ.findProduct(productId);
	        
	        // Mettre à jour la quantité du produit dans la base de données
	        product.setQuantity(product.getQuantity() - qte);
	        productServ.updateproduct(product); // Assurez-vous que la mise à jour est persistée

	        // Créer une entrée InvoicesProduct pour l'association entre le produit et la facture
	        InvoicesProduct invoiceProduct = new InvoicesProduct();
	        invoiceProduct.setInvoice(invoice);
	        invoiceProduct.setProduct(product);
	        invoiceProduct.setProductQuantity(qte);

	        invoiceProducts.add(invoiceProduct);
	    }

	    invoiceProductServ.createInvoicesProduct(invoiceProducts);

	    return "redirect:/invoices/" + invoice.getId();
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id, HttpSession session) {
		Long userId = (Long) session.getAttribute("user_id");
		if (userId == null) {
			return "redirect:/";
		}
		invoiceServ.deleteInvoice(id);
		return "redirect:/invoices";
	}
}
