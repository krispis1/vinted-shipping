package lt.vinted.shipping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Carrier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carrierId;
    private String name;
    private BigDecimal price;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="delivery_package_id", referencedColumnName = "deliveryPackageId")
    @JsonIgnoreProperties("carriers")
    private DeliveryPackage pkg;

    public Long getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(Long carrierId) {
        this.carrierId = carrierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DeliveryPackage getPkg() {
        return pkg;
    }

    public void setPkg(DeliveryPackage pkg) {
        this.pkg = pkg;
    }
}
