package lt.vinted.shipping.service;

import lt.vinted.shipping.model.DeliveryPackage;
import lt.vinted.shipping.repository.DeliveryPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPackageServiceImpl implements DeliveryPackageService {

    @Autowired
    private DeliveryPackageRepository deliveryPackageRepository;

    @Override
    public DeliveryPackage findByDeliveryPackageId(Long deliveryPackageId) {
        return deliveryPackageRepository.findByDeliveryPackageId(deliveryPackageId);
    }

    @Override
    public DeliveryPackage findDeliveryPackageBySize(String size) {
        return deliveryPackageRepository.findDeliveryPackageBySize(size);
    }
}
