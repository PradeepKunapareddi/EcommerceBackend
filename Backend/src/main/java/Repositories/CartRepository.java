package Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Models.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

