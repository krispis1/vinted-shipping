package lt.vinted.shipping.controller;

import lt.vinted.shipping.model.Carrier;
import lt.vinted.shipping.repository.CarrierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/carrier")
public class CarrierController {
    @Autowired
    private CarrierRepository carrierRepository;

    @ResponseBody
    @RequestMapping(value = "/carriers")
    public List<Carrier> getDeliveryPackage() {
        return carrierRepository.findAll();
    }
}
