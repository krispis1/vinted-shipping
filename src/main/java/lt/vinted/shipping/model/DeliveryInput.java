package lt.vinted.shipping.model;

import java.math.BigDecimal;

public class DeliveryInput {
    private String trxDate;
    private String packageSize;
    private String carrier;
    private BigDecimal price;
    private BigDecimal discount;
    private String discountStr;
    private String invalidData;

    public void setDiscountStr(String discountStr) {
        this.discountStr = discountStr;
    }

    public String getInvalidData() {
        return invalidData;
    }

    public void setInvalidData(String invalidData) {
        this.invalidData = invalidData;
    }

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
        if (discount != null && discount.compareTo(new BigDecimal("0")) > 0) {
            this.discountStr = this.discount.toString();
        } else {
            this.discountStr = "-";
        }
    }

    public String getDiscountStr() {
        return discountStr;
    }

    private boolean isValid;

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
