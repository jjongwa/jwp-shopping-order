package cart.application.service.coupon;

import cart.application.repository.CouponRepository;
import cart.application.service.coupon.dto.CouponResultDto;
import cart.auth.MemberAuth;
import cart.domain.coupon.Coupon;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponReadService {

    private final CouponRepository couponRepository;

    public CouponReadService(final CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<CouponResultDto> findByMember(final MemberAuth memberAuth) {
        final List<Coupon> coupons = couponRepository.findByMemberId(memberAuth.getId());

        return coupons.stream()
                .map(CouponResultDto::from)
                .collect(Collectors.toUnmodifiableList());
    }

}
