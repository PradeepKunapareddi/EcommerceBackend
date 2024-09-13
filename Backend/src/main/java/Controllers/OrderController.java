package Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Models.Order;
import Services.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place/{cartId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(orderService.placeOrder(cartId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}

