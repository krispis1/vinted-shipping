package lt.vinted.shipping.model;

import java.math.BigDecimal;

public class DeliveryPrice {
    private BigDecimal price;
    private BigDecimal discount;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
}
