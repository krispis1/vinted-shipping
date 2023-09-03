package lt.vinted.shipping.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class DeliveryPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long deliveryPackageId;
    private String size;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pkg", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pkg")
    private List<Carrier> carriers = new ArrayList<>();

    public Long getPackageId() {
        return deliveryPackageId;
    }

    public void setPackageId(Long packageId) {
        this.deliveryPackageId = packageId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Carrier> getCarriers() {
        return carriers;
    }

    public void setCarriers(List<Carrier> carriers) {
        this.carriers = carriers;
    }
}
