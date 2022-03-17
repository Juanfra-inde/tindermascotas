
package com.presentacion.repositories;

import com.presentacion.entitys.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRpository extends JpaRepository<Zone,String>{
   
}
