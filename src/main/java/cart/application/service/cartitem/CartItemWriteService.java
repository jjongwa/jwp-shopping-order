package cart.application.service.cartitem;

import cart.application.repository.CartItemRepository;
import cart.application.repository.MemberRepository;
import cart.application.repository.ProductRepository;
import cart.application.service.cartitem.dto.CartItemCreateDto;
import cart.application.service.cartitem.dto.CartItemUpdateDto;
import cart.auth.MemberAuth;
import cart.domain.cartitem.CartItem;
import cart.domain.member.Member;
import cart.domain.product.Product;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartItemWriteService {

    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CartItemWriteService(final CartItemRepository cartItemRepository, final MemberRepository memberRepository, final ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Long createCartItem(MemberAuth memberAuth, CartItemCreateDto cartItemCreateDto) {
        final Member member = memberRepository.findMemberById(memberAuth.getId())
                .orElseThrow(() -> new NoSuchElementException("일치하는 사용자가 없습니다."));
        final Product product = productRepository.findById(cartItemCreateDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("일치하는 상품이 없습니다."));
        return cartItemRepository.createCartItem(new CartItem(product, member));
    }

    public void updateQuantity(MemberAuth memberAuth, Long cartItemId, CartItemUpdateDto cartItemUpdateDto) {
        final Member member = memberRepository.findMemberById(memberAuth.getId())
                .orElseThrow(() -> new NoSuchElementException("일치하는 사용자가 없습니다."));

        final CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 상품이 없습니다."));
        cartItem.checkOwner(member);

        if (cartItemUpdateDto.getQuantity() == 0) {
            cartItemRepository.deleteById(cartItemId);
            return;
        }

        cartItem.changeQuantity(cartItemUpdateDto.getQuantity());
        cartItemRepository.updateQuantity(cartItem);
    }

    public void remove(MemberAuth memberAuth, Long cartItemId) {
        final Member member = memberRepository.findMemberById(memberAuth.getId())
                .orElseThrow(() -> new NoSuchElementException("일치하는 사용자가 없습니다."));

        final CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("일치하는 상품이 없습니다."));
        cartItem.checkOwner(member);

        cartItemRepository.deleteById(cartItemId);
    }

}
