package lt.vinted.shipping.util;

import lt.vinted.shipping.model.Carrier;
import lt.vinted.shipping.model.DeliveryInput;
import lt.vinted.shipping.model.DeliveryPackage;
import lt.vinted.shipping.model.DeliveryPrice;
import lt.vinted.shipping.service.DeliveryPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DiscountUtil {
    @Autowired
    private DeliveryPackageService deliveryPackageService;

    @Autowired
    private DeliveryInputUtil deliveryInputUtil;

    /*
    * Quick solution for a discount monthly limit variable. Ideally should come from some config.
    * */
    private static final BigDecimal DISCOUNT_LIMIT = new BigDecimal("10");

    /*
    * Main method to apply discounts for delivery input rows
    * */
    public String getDiscounts(List<DeliveryInput> deliveryInputs) {
        List<DeliveryInput> discountList = new ArrayList<>();

        for (DeliveryInput d : deliveryInputs) {
            if (!d.isValid()) {
                discountList.add(d);
                continue;
            }

            DeliveryPrice deliveryPrice = validateDiscount(discountList, d);
            d.setPrice(deliveryPrice.getPrice());
            d.setDiscount(deliveryPrice.getDiscount());
            discountList.add(d);
        }

        return deliveryInputUtil.getOutput(discountList);
    }

    /*
    * Get the lowest available delivery price for specified package size from any carrier
    * */
    private BigDecimal getLowestPrice(List<Carrier> carriers) {
        BigDecimal price = BigDecimal.valueOf(Long.MAX_VALUE);

        for (Carrier c : carriers) {
            if (c.getPrice().compareTo(price) < 0) {
                price = c.getPrice();
            }
        }

        return price;
    }

    /*
    * Get the regular delivery price with no discounts
    * */
    private BigDecimal getDeliveryPrice(List<Carrier> carriers, String carrierName) {
        for (Carrier c : carriers) {
            if (c.getName().equals(carrierName)) {
                return c.getPrice();
            }
        }

        return null;
    }

    /*
    * validateDiscount - method which uses switch statement for package sizes S, M and L and applies
    * needed discount logic accordingly. An improvement might be made to refactor the switch statement
    * hardcoded values and take them directly from configuration in database.
    * */
    private DeliveryPrice validateDiscount(List<DeliveryInput> deliveryInputs, DeliveryInput currentDelivery) {
        BigDecimal accumulatedDiscounts = new BigDecimal("0");
        LocalDate currentDeliveryDate = LocalDate.parse(currentDelivery.getTrxDate());
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        DeliveryPackage dp = deliveryPackageService.findDeliveryPackageBySize(currentDelivery.getPackageSize());

        for (DeliveryInput d : deliveryInputs) {
            if (!d.isValid()) {
                continue;
            }

            LocalDate date = LocalDate.parse(d.getTrxDate());

            if (currentDeliveryDate.getMonthValue() == date.getMonthValue() && d.getDiscount() != null) {
                accumulatedDiscounts = accumulatedDiscounts.add(d.getDiscount());
            }
        }

        /*
        * Would move this logic to a separate config class and load all the rules from a database
        * configuration table.
        * */
        switch (currentDelivery.getPackageSize()) {
            case "S" -> {
                if (accumulatedDiscounts.compareTo(DISCOUNT_LIMIT) >= 0) {
                    deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()));
                } else {
                    BigDecimal discount = getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()).subtract(getLowestPrice(dp.getCarriers()));

                    if (accumulatedDiscounts.add(discount).compareTo(DISCOUNT_LIMIT) <= 0) {
                        deliveryPrice.setPrice(getLowestPrice(dp.getCarriers()));
                        deliveryPrice.setDiscount(discount);
                    } else {
                        discount = DISCOUNT_LIMIT.subtract(accumulatedDiscounts);

                        deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()).subtract(discount));
                        deliveryPrice.setDiscount(discount);
                    }
                }
            }
            case "M" -> deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()));
            case "L" -> {
                if (!currentDelivery.getCarrier().equals("LP") || accumulatedDiscounts.compareTo(DISCOUNT_LIMIT) >= 0) {
                    deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()));
                } else {
                    int discountCnt = 0;

                    for (DeliveryInput d : deliveryInputs) {
                        if (!d.isValid()) {
                            continue;
                        }

                        LocalDate date = LocalDate.parse(d.getTrxDate());

                        if (currentDeliveryDate.getMonthValue() == date.getMonthValue() && d.getCarrier().equals("LP") && d.getPackageSize().equals("L")) {
                            discountCnt++;
                        }
                    }

                    if (discountCnt == 2) {
                        BigDecimal discount = getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier());

                        if (accumulatedDiscounts.add(discount).compareTo(DISCOUNT_LIMIT) <= 0) {
                            deliveryPrice.setPrice(new BigDecimal("0"));
                            deliveryPrice.setDiscount(discount);
                        } else {
                            discount = DISCOUNT_LIMIT.subtract(accumulatedDiscounts);

                            deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()).subtract(discount));
                            deliveryPrice.setDiscount(discount);
                        }
                    } else {
                        deliveryPrice.setPrice(getDeliveryPrice(dp.getCarriers(), currentDelivery.getCarrier()));
                    }
                }
            }
        }

        return deliveryPrice;
    }
}
