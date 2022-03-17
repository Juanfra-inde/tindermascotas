
package com.presentacion.repositories;

import com.presentacion.entitys.Voto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto,String>{
    
    @Query("SELECT v FROM Voto v WHERE v.pet1.id = :id ORDER BY v.datevoto DESC")
    public List<Voto> findGivenVoto(@Param("id") String id);
        
    @Query("SELECT v FROM Voto v WHERE v.pet2.id = :id ORDER BY v.datevoto DESC")
    public List<Voto> findRecivedVoto(@Param("id") String id);
    
}
