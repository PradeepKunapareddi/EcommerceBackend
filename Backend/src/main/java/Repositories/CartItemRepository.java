package Repositories;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Models.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
