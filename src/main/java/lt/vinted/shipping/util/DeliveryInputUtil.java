package lt.vinted.shipping.util;

import lt.vinted.shipping.model.Carrier;
import lt.vinted.shipping.model.DeliveryInput;
import lt.vinted.shipping.model.DeliveryPackage;
import lt.vinted.shipping.service.DeliveryPackageService;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class DeliveryInputUtil {
    @Autowired
    private DeliveryPackageService deliveryPackageService;

    /*
    * Method for reading the input file and mapping data to DeliveryInput object
    * */
    public List<DeliveryInput> readFile(String input) {
        BufferedReader reader;
        List<DeliveryInput> deliveryInputs = new ArrayList<>();

        try {
            reader = new BufferedReader(new StringReader(input));
            String line = reader.readLine();

            while (line != null) {
                String[] lineVal = line.split(" ");
                DeliveryInput di = new DeliveryInput();

                if (lineVal.length == 3) {
                    try {
                        di.setTrxDate(lineVal[0]);
                        di.setPackageSize(lineVal[1]);
                        di.setCarrier(lineVal[2]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        di.setInvalidData(line);
                        e.printStackTrace();
                    }
                } else {
                    di.setInvalidData(line);
                }

                deliveryInputs.add(di);

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deliveryInputs;
    }

    /*
    * Not a pretty method, but matches the specified output requirement in the task,
    * would rewrite it to map data to objects accordingly for database saving.
    * */
    public String getOutput(List<DeliveryInput> deliveryInputs) {
        StringBuilder sb = new StringBuilder();

        for (DeliveryInput d : deliveryInputs) {
            if (d.isValid()) {
                sb.append(d.getTrxDate()).append(d.getTrxDate().isEmpty() ? "" : " ")
                        .append(d.getPackageSize()).append(d.getPackageSize().isEmpty() ? "" : " ")
                        .append(d.getCarrier()).append(d.getCarrier().isEmpty() ? "" : " ")
                        .append(d.getPrice() != null ? d.getPrice() : "").append(d.getPrice() != null ? " " : "")
                        .append(d.getDiscountStr() != null ? d.getDiscountStr() : "")
                        .append("\n");
            } else {
                sb.append(d.getInvalidData() != null ? d.getInvalidData() : d.getTrxDate() + " " + d.getPackageSize() + " " + d.getCarrier()).append(" ")
                        .append("Ignored")
                        .append("\n");
            }
        }

        return sb.toString();
    }

    /*
    * Validates read inputs and returns a new array of DeliveryInput objects.
    * */
    public List<DeliveryInput> validate(List<DeliveryInput> deliveryInputs) {
        List<DeliveryInput> validatedInputs = new ArrayList<>();

        for (DeliveryInput d : deliveryInputs) {
            d.setValid(trxDateValid(d.getTrxDate()) && packageSizeValid(d.getPackageSize()) && carrierValid(d.getPackageSize(), d.getCarrier()) && d.getInvalidData() == null);
            d.setTrxDate(d.getTrxDate() == null ? "" : d.getTrxDate());
            d.setCarrier(d.getCarrier() == null ? "" : d.getCarrier());
            d.setPackageSize(d.getPackageSize() == null ? "" : d.getPackageSize());
            validatedInputs.add(d);
        }

        return validatedInputs;
    }

    private static boolean trxDateValid(String trxDate) {
        return GenericValidator.isDate(trxDate, "yyyy-MM-dd", true);
    }

    private boolean packageSizeValid(String packageSize) {
        return deliveryPackageService.findDeliveryPackageBySize(packageSize) != null;
    }

    private boolean carrierValid(String packageSize, String carrier) {
        DeliveryPackage dp = deliveryPackageService.findDeliveryPackageBySize(packageSize);

        for (Carrier c : dp.getCarriers()) {
            if (c.getName().equals(carrier)) {
                return true;
            }
        }

        return false;
    }
}
