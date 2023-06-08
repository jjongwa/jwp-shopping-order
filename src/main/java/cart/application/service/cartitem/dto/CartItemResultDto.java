package cart.application.service.cartitem.dto;

import cart.application.service.product.dto.ProductResultDto;
import cart.domain.cartitem.CartItem;

public class CartItemResultDto {

    private final Long id;
    private final int quantity;
    private final ProductResultDto productResultDto;

    private CartItemResultDto(Long id, int quantity, ProductResultDto productResultDto) {
        this.id = id;
        this.quantity = quantity;
        this.productResultDto = productResultDto;
    }

    public static CartItemResultDto of(CartItem cartItem) {
        return new CartItemResultDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                ProductResultDto.of(cartItem.getProduct())
        );
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductResultDto getProductResultDto() {
        return productResultDto;
    }

}
