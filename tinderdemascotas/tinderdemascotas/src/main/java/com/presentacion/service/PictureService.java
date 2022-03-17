
package com.presentacion.service;

import com.presentacion.entitys.Picture;
import com.presentacion.errors.ErrorServices;
import com.presentacion.repositories.PictureRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PictureService {
    
    @Autowired
    private PictureRepository pr;
    
    @Transactional
    public Picture savePicture(MultipartFile file) throws ErrorServices{
        if(file != null){
            try {
                Picture picture = new Picture();
                picture.setMime(file.getContentType());
                picture.setNombre(file.getName());
                picture.setContenido(file.getBytes());
                return pr.save(picture);
            } catch (Exception e) {
                System.err.println(e.getMessage());   
            }            
        }
        return null;        
    }
    @Transactional
    public Picture update(String idPicture, MultipartFile file) throws ErrorServices{
        if(file != null){
            try {
                Picture picture = new Picture();
                
                if (idPicture != null) {
                    Optional<Picture> answer = pr.findById(idPicture);
                    if (answer.isPresent()) {
                        picture = answer.get();
                        picture.setMime(file.getContentType());
                        picture.setNombre(file.getName());
                        picture.setContenido(file.getBytes());
                        return pr.save(picture);
                    }else{
                        throw new ErrorServices("Error al encontrar la foto");
                    }
                }else{
                    throw new ErrorServices("No se ha encontrado la foto deseada");
                }
                
            } catch (Exception e) {
                System.err.println(e.getMessage());   
            }            
        }
        return null;           
    }
}
