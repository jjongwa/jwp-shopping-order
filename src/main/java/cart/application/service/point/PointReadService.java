package cart.application.service.point;

import cart.application.repository.PointRepository;
import cart.application.service.point.dto.PointResultDto;
import cart.auth.MemberAuth;
import cart.domain.point.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PointReadService {

    private final PointRepository pointRepository;

    public PointReadService(final PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public PointResultDto findPointByMember(final MemberAuth memberAuth) {
        final Point pointByMember = pointRepository.findPointByMemberId(memberAuth.getId());
        return PointResultDto.of(pointByMember.getPointHistories(), pointByMember.calculateTotalPoint());
    }

}
