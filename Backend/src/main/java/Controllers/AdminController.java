package Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Models.Product;
import Services.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @GetMapping("/inventory")
    public String getInventory(Model model) {
        // Fetch all products from the database
        List<Product> inventoryList = productService.getAllProducts();

        // Add the list of products to the model
        model.addAttribute("inventory", inventoryList);

        // Return the name of the Thymeleaf template (inventory.html)
        return "inventory";
    }
}
  
