package Services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Models.Cart;
import Models.CartItem;
import Models.Product;
import Repositories.CartItemRepository;
import Repositories.CartRepository;
import Repositories.ProductRepository;
import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElse(new Cart());
    }

    @Transactional
    public Cart addToCart(Long cartId, Long productId, int quantity) throws Exception {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new Exception("Product not found"));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.updatePrice();
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.updatePrice();
            cart.getCartItems().add(cartItem);
        }

        cart.updateTotalPrice();
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(Long cartId, Long cartItemId) throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found"));

        cart.getCartItems().remove(cartItem);
        cart.updateTotalPrice();
        cartItemRepository.delete(cartItem);

        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateCartItem(Long cartId, Long cartItemId, int quantity) throws Exception {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found"));
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new Exception("CartItem not found"));

        cartItem.setQuantity(quantity);
        cartItem.updatePrice();
        cart.updateTotalPrice();

        return cartRepository.save(cart);
    }

    public Cart viewCart(Long cartId) throws Exception {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new Exception("Cart not found"));
    }
}
