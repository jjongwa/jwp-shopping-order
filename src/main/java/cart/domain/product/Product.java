package cart.domain.product;

public class Product {

    private Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int calculatePriceBy(final int quantity) {
        return quantity * price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isEqualId(final long productId) {
        return id == productId;
    }

}
