package lt.vinted.shipping.service;

import lt.vinted.shipping.model.DeliveryPackage;
import org.springframework.stereotype.Component;

@Component
public interface DeliveryPackageService {
    public DeliveryPackage findByDeliveryPackageId(Long deliveryPackageId);
    public DeliveryPackage findDeliveryPackageBySize(String size);
}
