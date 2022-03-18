
package com.presentacion.controllers;

import com.presentacion.entitys.Customer;
import com.presentacion.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {
    
    @Autowired
    private CustomerService cs;
    
    @GetMapping
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/login")
    public String login(){
        return "/login.html";
    }
    
    @PostMapping("/login")
    public String loginsave(){
        return "/index.html";
    }
    
    @GetMapping("/register")
    public String register(ModelMap model, @RequestParam(required = false) String id){
        try {
            if (id != null) {
                Customer customer = cs.finById(id);
                model.addAttribute("customer", customer);
                return "/register.html";
            }else{
                model.addAttribute("customer", new Customer());
                return "/register.html";
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/register.html";
        }    
    }
//    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public String registersave(@ModelAttribute Customer customer, ModelMap model){
        try {
            System.out.println("Emial: " +customer.getEmail());
            return "/register.html";
        } catch (Exception e) {
            System.out.println("Emial: " +customer.getEmail());
            return "/register.html";
        }
                
    }
}
