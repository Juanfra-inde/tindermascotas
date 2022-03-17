
package com.presentacion.repositories;

import com.presentacion.entitys.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepositorie extends JpaRepository<Pet,String>{
    
    @Query("SELECT p FROM Pet p WHERE p.customer.id = :id")
    public List<Pet> findPetByCustomer(@Param("id") String id);
    
}
