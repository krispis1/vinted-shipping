package lt.vinted.shipping.service;

import lt.vinted.shipping.model.DeliveryInput;
import lt.vinted.shipping.util.DeliveryInputUtil;
import lt.vinted.shipping.util.DiscountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private DeliveryInputUtil deliveryInputUtil;

    @Autowired
    private DiscountUtil discountUtil;

    @Override
    public String getTransactions(String input) {
        List<DeliveryInput> deliveryInputs = deliveryInputUtil.readFile(input);

        return discountUtil.getDiscounts(deliveryInputUtil.validate(deliveryInputs));
    }
}
