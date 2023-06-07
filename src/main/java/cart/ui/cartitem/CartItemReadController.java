package cart.ui.cartitem;

import cart.application.service.cartitem.CartItemReadService;
import cart.application.service.cartitem.dto.CartResultDto;
import cart.auth.MemberAuth;
import cart.ui.cartitem.dto.CartResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-items")
public class CartItemReadController {

    private final CartItemReadService cartItemReadService;

    public CartItemReadController(final CartItemReadService cartItemReadService) {
        this.cartItemReadService = cartItemReadService;
    }

    @GetMapping
    public ResponseEntity<CartResponse> showCartItems(MemberAuth memberAuth) {
        final CartResultDto cartResult = cartItemReadService.findByMember(memberAuth);
        return ResponseEntity.ok(CartResponse.from(cartResult));
    }

}
