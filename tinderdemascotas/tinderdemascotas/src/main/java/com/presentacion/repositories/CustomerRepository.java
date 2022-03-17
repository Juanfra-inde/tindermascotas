
package com.presentacion.repositories;

import com.presentacion.entitys.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
    
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    public Customer findByEmail(@Param("email") String email);
    
}
