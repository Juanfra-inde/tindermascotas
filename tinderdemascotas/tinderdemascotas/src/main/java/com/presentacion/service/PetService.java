
package com.presentacion.service;

import com.presentacion.entitys.Customer;
import com.presentacion.entitys.Pet;
import com.presentacion.entitys.Picture;
import com.presentacion.enums.Sex;
import com.presentacion.errors.ErrorServices;
import com.presentacion.repositories.CustomerRepository;
import com.presentacion.repositories.PetRepositorie;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetService {
 
    @Autowired
    private PetRepositorie pr;
    
    @Autowired 
    private CustomerRepository cr;
    
    @Autowired
    private PictureService ps;
    @Transactional
    public void addPet(MultipartFile file, String idCustomer, String name, Sex sex) throws ErrorServices{
        
        Customer customer = cr.findById(idCustomer).get();
        
        validate(name,sex);
        
        Pet pet = new Pet();
        
        pet.setName(name);
        pet.setSex(sex);
        pet.setAlta(new Date());
        
        Picture picture = ps.savePicture(file);
        pet.setPicture(picture);
        
        pr.save(pet);
    }

    private void validate(String name, Sex sex) throws ErrorServices {
        if(name.isEmpty() || name.equals(" ") || name == null){
            throw new ErrorServices("El nombre asignado es invalido");
        }
        if (sex == null) {
            throw new ErrorServices("El sexo de la mascota no puede ser nulo");
        }
    }
    @Transactional
    public void modify(MultipartFile file, String idPet,String idCustomer, String name, Sex sex) throws ErrorServices{
        
        validate(name,sex);
        
        Optional<Pet> answer = pr.findById(idPet);
        if (answer.isPresent()) {
            Pet pet = answer.get();
            if (pet.getCustomer().getId().equals(idCustomer)) {
                pet.setName(name);
                pet.setSex(sex);
                
                String idPicture = null;
                if (pet.getPicture() != null) {
                    idPicture = pet.getPicture().getId();
                }
                
                Picture picture = ps.update(idPicture, file);
                pet.setPicture(picture);
                
                pr.save(pet);
            }else{
                throw new ErrorServices("No pose el permiso requerido para realizar esa accion");
            }
            
        }else{
            throw new ErrorServices("La mascota desiganada no existe");
        }        
    }
    @Transactional
    public void delete(String idCustomer,String idPet) throws ErrorServices{
        Optional<Pet> answer = pr.findById(idPet);
        if (answer.isPresent()) {
            Pet pet = answer.get();
            if (pet.getCustomer().getId().equals(idCustomer)) {
                pet.setBaja(new Date());
            }else{
                throw new ErrorServices("No pose el permiso requerido para realizar esa accion");
            }
        }else{
            throw new ErrorServices("La mascota desiganada no existe");
        }
    }
    
    
}
