package lt.vinted.shipping.repository;

import lt.vinted.shipping.model.DeliveryPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPackageRepository extends JpaRepository<DeliveryPackage, Long> {
    public DeliveryPackage findByDeliveryPackageId(Long deliveryPackageId);
    public DeliveryPackage findDeliveryPackageBySize(String size);
}
