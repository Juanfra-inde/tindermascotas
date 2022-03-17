
package com.presentacion.service;

import com.presentacion.entitys.Pet;
import com.presentacion.entitys.Voto;
import com.presentacion.errors.ErrorServices;
import com.presentacion.repositories.PetRepositorie;
import com.presentacion.repositories.VotoRepository;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotoService {
    
    @Autowired
    private VotoRepository vr;
    @Autowired
    private PetRepositorie pr;
    @Transactional
    public void votar(String idCustomer, String idPet,String idPet2) throws ErrorServices{
        if (idPet.equals(idPet2)) {
            throw new ErrorServices("No puede votarse a si mismo");
        }
        
        Voto voto = new Voto();
        voto.setDatevoto(new Date());
        
        Optional<Pet> answer = pr.findById(idPet);
        if (answer.isPresent()) {
            Pet pet1 = answer.get();
            if (pet1.getCustomer().getId().equals(idCustomer)) {
                voto.setPet1(pet1);
            }else{
                throw new ErrorServices("No tine el permiso para realizar la operacion solicitada");
            }
        }else{
            throw new ErrorServices("No existe una mascota vinculada con ese Id");
        }
        
        Optional<Pet>answer2 = pr.findById(idPet2);
        if (answer2.isPresent()) {
            Pet pet2 = answer2.get();
            voto.setPet2(pet2);
        }else{
            throw new ErrorServices("No existe una mascota vinculada con ese Id");
        }
        vr.save(voto);
    }
    @Transactional
    public void answerVoto(String idCustomer,String idVoto) throws ErrorServices{
        Optional<Voto> answer = vr.findById(idVoto);
        if (answer.isPresent()) {
            Voto voto = answer.get();
            voto.setDateanswer(new Date());
            if (voto.getPet2().getCustomer().getId().equals(idCustomer)) {
                vr.save(voto);
            }else{
                throw new ErrorServices("No tiene permiso para realizar esa operacion");
            }
            
        }else{
            throw new ErrorServices("No existe el voto solicitado");
        }
    }
    
}
