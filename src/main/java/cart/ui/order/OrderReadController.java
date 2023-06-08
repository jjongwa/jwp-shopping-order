package cart.ui.order;

import cart.application.service.order.OrderReadService;
import cart.application.service.order.dto.OrderDto;
import cart.auth.MemberAuth;
import cart.ui.order.dto.OrderResponse;
import cart.ui.order.dto.OrdersResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderReadController {

    private final OrderReadService orderReadService;

    public OrderReadController(final OrderReadService orderReadService) {
        this.orderReadService = orderReadService;
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> findOrders(final MemberAuth memberAuth) {
        final List<OrderDto> orderDtos = orderReadService.findAllByMember(memberAuth);
        return ResponseEntity.ok(OrdersResponse.from(orderDtos));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findSingleOrder(final MemberAuth memberAuth,
                                                         @PathVariable("orderId") Long orderId) {
        OrderDto orderDto = orderReadService.findByOrderId(memberAuth, orderId);
        return ResponseEntity.ok(OrderResponse.from(orderDto));
    }

}
