
package com.presentacion.controllers;

import com.presentacion.entitys.Customer;
import com.presentacion.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class IndexController {
    
    @Autowired
    private CustomerService cs;
    
    
    @GetMapping
    public String index(){
        return "index.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model){
        if (error != null) {
            model.put("error", "El nombre o contraseña ingresada es incorrecta");
        }
        return "/login.html";
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

    @PostMapping("/register")
    public String registersave(MultipartFile file,@ModelAttribute Customer customer, ModelMap model){
        try {
            cs.customeRregister(file, customer);
            return "/login.html";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.put("customer", customer);
            return "/register.html";
        }
                
    }
    
    @GetMapping("/altabaja/{id}")
    public String altabaja(@PathVariable String id) {
        try {
            //cs.altabaja(id);
            return "redirect:/clientes";
        } catch (Exception e) {
            return "redirect:/cientes";
        }
    }
}
