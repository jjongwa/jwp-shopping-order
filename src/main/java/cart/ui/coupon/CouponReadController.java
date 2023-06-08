package cart.ui.coupon;

import cart.application.service.coupon.CouponReadService;
import cart.application.service.coupon.dto.CouponResultDto;
import cart.auth.MemberAuth;
import cart.ui.coupon.dto.CouponResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponReadController {

    private final CouponReadService couponReadService;

    public CouponReadController(final CouponReadService couponReadService) {
        this.couponReadService = couponReadService;
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> findCoupons(final MemberAuth memberAuth) {
        final List<CouponResultDto> couponResultDtos = couponReadService.findByMember(memberAuth);
        final List<CouponResponse> couponResponses = couponResultDtos.stream()
                .map(CouponResponse::from)
                .collect(Collectors.toUnmodifiableList());
        return ResponseEntity.ok(couponResponses);
    }

}
