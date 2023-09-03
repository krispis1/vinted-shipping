package lt.vinted.shipping.controller;

import lt.vinted.shipping.model.DeliveryPackage;
import lt.vinted.shipping.service.DeliveryPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/deliveryPackage")
public class DeliveryPackageController {
    @Autowired
    private DeliveryPackageService deliveryPackageService;

    @RequestMapping(value = "/{deliveryPackageId}", method = RequestMethod.GET)
    @ResponseBody
    public DeliveryPackage getDeliveryPackage(@PathVariable Long deliveryPackageId) {
        return deliveryPackageService.findByDeliveryPackageId(deliveryPackageId);
    }

    @RequestMapping(value = "/size/{size}", method = RequestMethod.GET)
    @ResponseBody
    public DeliveryPackage getDeliveryPackageBySize(@PathVariable String size) {
        return deliveryPackageService.findDeliveryPackageBySize(size);
    }
}
