package Ecom.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Ecom.Exception.CartException;
import Ecom.Exception.ProductException;
import Ecom.Exception.UserException;
import Ecom.Model.Cart;
import Ecom.Model.Product;
import Ecom.Service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-product")
    public ResponseEntity<Cart> addProductToCart(@RequestParam Integer userId, @RequestParam Integer productId) {
        try {
            Cart cart = cartService.addProductToCart(userId, productId);
            return ResponseEntity.ok(cart);
        } catch (CartException | ProductException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/increase-productQty")
    public ResponseEntity<Cart> increaseProductQuantity(@RequestParam Integer userId, @RequestParam Integer productId) {
        try {
            Cart cart = cartService.increaseProductQuantity(userId, productId);
            return ResponseEntity.ok(cart);
        } catch (CartException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/decrease-productQty")
    public ResponseEntity<Cart> decreaseProductQuantity(@RequestParam Integer userId, @RequestParam Integer productId) {
        try {
            Cart cart = cartService.decreaseProductQuantity(userId, productId);
            return ResponseEntity.ok(cart);
        } catch (CartException | UserException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/remove-product/{cartId}/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Integer cartId, @PathVariable Integer productId) {
        try {
            cartService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok().build();
        } catch (CartException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/products/{cartId}")
    public ResponseEntity<List<Product>> getAllCartProducts(@PathVariable Integer cartId) {
        try {
            List<Product> products = cartService.getAllCartProduct(cartId);
            return ResponseEntity.ok(products);
        } catch (CartException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/remove-all-product/{cartId}")
    public ResponseEntity<Void> removeAllProducts(@PathVariable Integer cartId) {
        try {
            cartService.removeAllProduct(cartId);
            return ResponseEntity.ok().build();
        } catch (CartException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
