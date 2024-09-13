package Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Models.Cart;
import Services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> viewCart(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(cartService.viewCart(cartId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<Cart> addToCart(@PathVariable Long cartId,
                                          @RequestParam Long productId,
                                          @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(cartService.addToCart(cartId, productId, quantity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{cartId}/remove/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartId,
                                               @PathVariable Long cartItemId) {
        try {
            return ResponseEntity.ok(cartService.removeFromCart(cartId, cartItemId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{cartId}/update/{cartItemId}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long cartId,
                                               @PathVariable Long cartItemId,
                                               @RequestParam int quantity) {
        try {
            return ResponseEntity.ok(cartService.updateCartItem(cartId, cartItemId, quantity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
