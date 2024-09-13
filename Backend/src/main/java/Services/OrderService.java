package Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Models.Cart;
import Models.CartItem;
import Models.Order;
import Models.OrderItem;
import Models.Product;
import Repositories.CartRepository;
import Repositories.OrderRepository;
import Repositories.ProductRepository;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public synchronized Order placeOrder(Long cartId) throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found"));

        // Create an Order
        Order order = new Order();
        Set<OrderItem> orderItems = new HashSet<>();
        double totalAmount = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            // Fetch the product with a pessimistic write lock
            Product product = productRepository.findById(cartItem.getProduct().getId())
                    .orElseThrow(() -> new Exception("Product not found"));

            // Check if enough stock is available
            if (product.getQuantity() < cartItem.getQuantity()) {
                throw new Exception("Not enough stock for product: " + product.getName());
            }

            // Deduct stock
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product); // Save stock update

            // Create OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getPrice());
            orderItems.add(orderItem);

            totalAmount += cartItem.getPrice();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus("PLACED");

        // Clear the cart after placing the order
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return orderRepository.save(order);
    }
}
