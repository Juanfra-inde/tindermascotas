
package com.presentacion.repositories;

import com.presentacion.entitys.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture,String>{
    
}
