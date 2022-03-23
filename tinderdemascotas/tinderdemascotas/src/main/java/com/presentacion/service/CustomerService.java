
package com.presentacion.service;

import com.presentacion.entitys.Customer;
import com.presentacion.entitys.Picture;
import com.presentacion.errors.ErrorServices;
import com.presentacion.repositories.CustomerRepository;
import java.util.ArrayList;
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
    public void customeRregister(MultipartFile file,Customer customer) throws ErrorServices{
        validate(customer.getName(),customer.getLastname(),customer.getEmail(),customer.getPassword());               
        
        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));        
        customer.setBaja(false);
        Picture pricture = ps.savePicture(file);
        customer.setPicture(pricture);
        
        cr.save(customer);    
        
        // ns.sendEmail("Bienvenido al tinde der mascotas", "tinder de mascotas", customer.getEmail());
    }
    @Transactional
    public void modify(MultipartFile file,String id,Customer customer) throws ErrorServices{
        Optional<Customer> answer = cr.findById(id);
        if (answer.isPresent()) {
            Customer newcustomer = answer.get();
            validate(customer.getName(),customer.getLastname(),customer.getEmail(),customer.getPassword());               
            
            newcustomer.setName(customer.getName());
            newcustomer.setLastname(customer.getLastname());
            newcustomer.setEmail(customer.getEmail());
            newcustomer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
            newcustomer.setAlta(customer.getAlta());

            String idPicture = null;
            if (customer.getPicture() != null) {
                idPicture = customer.getPicture().getId();
            }
            
            Picture picture = ps.update(idPicture, file);
            customer.setPicture(picture);
            
            cr.save(newcustomer);        
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
            throw new ErrorServices("La clave ingresada es incorrecta o requiere mas de 4 caracteres");
        }
    }
    @Transactional
    private void getDown(String id) throws ErrorServices{
         Optional<Customer> answer = cr.findById(id);
        if (answer.isPresent()) {
            Customer customer = answer.get();
            customer.setBaja(true);
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
            customer.setBaja(false);
            cr.save(customer);
        }else{
            throw new ErrorServices("El usuario no ha sido encontrado");
        }
    }
    
    public Customer finById(String id){
        return cr.findById(id).get();
    }
    
    public Customer findByEmail(String email){
        return cr.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("ACA LLEGAMOS");
        Customer customer = findByEmail(email);
        
        if (customer != null) {
            System.out.println("Customer email: " +customer.getEmail());
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority rolePermissions = new SimpleGrantedAuthority("ROLE_" + customer.getRol());
            permisos.add(rolePermissions);
            return new User(customer.getEmail(), customer.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("El usuario no ha sido encontrado");
        }
    }
}
