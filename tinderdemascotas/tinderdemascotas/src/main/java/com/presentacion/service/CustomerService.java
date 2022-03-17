
package com.presentacion.service;

import com.presentacion.entitys.Customer;
import com.presentacion.entitys.Picture;
import com.presentacion.errors.ErrorServices;
import com.presentacion.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerService implements UserDetailsService{
    
    @Autowired
    private CustomerRepository cr;
    
    @Autowired
    private PictureService ps;
    
    @Autowired
    private NotoficationService ns;
    
    @Transactional
    public void Customerregister(MultipartFile file,String name, String lastname, String email, String password) throws ErrorServices{
       validate(name,lastname,email,password);
        
        Customer customer = new Customer();
        customer.setName(name);
        customer.setLastname(lastname);
        customer.setEmail(email);
        customer.setPassword(new BCryptPasswordEncoder().encode(password));
        customer.setAlta(new Date());
        
        Picture pricture = ps.savePicture(file);
        customer.setPicture(pricture);
        
        cr.save(customer);    
        
        // ns.sendEmail("Bienvenido al tinde der mascotas", "tinder de mascotas", customer.getEmail());
    }
    @Transactional
    public void modify(MultipartFile file,String id,String name, String lastname, String email, String password) throws ErrorServices{
        Optional<Customer> answer = cr.findById(id);
        if (answer.isPresent()) {
            validate(name, lastname, email, password);
            Customer customer = answer.get();
            customer.setName(name);
            customer.setLastname(lastname);
            customer.setEmail(email);
            customer.setPassword(new BCryptPasswordEncoder().encode(password));
            customer.setAlta(new Date());

            String idPicture = null;
            if (customer.getPicture() != null) {
                idPicture = customer.getPicture().getId();
            }
            
            Picture picture = ps.update(idPicture, file);
            customer.setPicture(picture);
            
            cr.save(customer);        
        }else{
            throw new ErrorServices("El usuario no ha sido encontrado");
        }
    }
    private void validate(String name, String lastname, String email, String password) throws ErrorServices{
        if(name == null || name.isEmpty() || name.contains(" ")){
            throw new ErrorServices("El nombre ingresado es incorrecto");
        }
        if (lastname== null || lastname.isEmpty() || lastname.contains(" ")) {
            throw new ErrorServices("El apellido ingresado es incorrecto");
        }
        if (email== null || email.isEmpty() || email.contains(" ")) {
            throw new ErrorServices("El mail ingresado es incorrecto");
        }
        if (password== null || password.isEmpty() || password.contains(" ") || password.length()<4) {
            throw new ErrorServices("La clave ingresada es incorrecta o requeire mas de 4 caracteres");
        }
    }
    @Transactional
    private void getDown(String id) throws ErrorServices{
         Optional<Customer> answer = cr.findById(id);
        if (answer.isPresent()) {
            Customer customer = answer.get();
            customer.setBaja(new Date());
            cr.save(customer);
        }else{
            throw new ErrorServices("El usuario no ha sido encontrado");
        }
    }
    @Transactional
    private void getUp(String id) throws ErrorServices{
         Optional<Customer> answer = cr.findById(id);
        if (answer.isPresent()) {
            Customer customer = answer.get();
            customer.setBaja(null);
            cr.save(customer);
        }else{
            throw new ErrorServices("El usuario no ha sido encontrado");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = cr.findByEmail(email);
        if (customer != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority rolePermissions = new SimpleGrantedAuthority("ROLE_" + customer.getRol());
            permisos.add(rolePermissions);
            return new User(customer.getEmail(), customer.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("El usuario no ha sido encontrado");
        }
    }
}
