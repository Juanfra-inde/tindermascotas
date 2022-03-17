
package com.presentacion.entitys;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Voto {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date datevoto;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateanswer;
    
    @ManyToOne
    private Pet pet1;
    
    @ManyToOne
    private Pet pet2;

    public Voto() {
    }

    public Voto(String id, Date datevoto, Date dateanswer, Pet pet1, Pet pet2) {
        this.id = id;
        this.datevoto = datevoto;
        this.dateanswer = dateanswer;
        this.pet1 = pet1;
        this.pet2 = pet2;
    }
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDatevoto() {
        return datevoto;
    }

    public void setDatevoto(Date datevoto) {
        this.datevoto = datevoto;
    }

    public Date getDateanswer() {
        return dateanswer;
    }

    public void setDateanswer(Date dateanswer) {
        this.dateanswer = dateanswer;
    }

    /**
     * @return the pet1
     */
    public Pet getPet1() {
        return pet1;
    }

    /**
     * @param pet1 the pet1 to set
     */
    public void setPet1(Pet pet1) {
        this.pet1 = pet1;
    }

    /**
     * @return the pet2
     */
    public Pet getPet2() {
        return pet2;
    }

    /**
     * @param pet2 the pet2 to set
     */
    public void setPet2(Pet pet2) {
        this.pet2 = pet2;
    }

    
    
}
