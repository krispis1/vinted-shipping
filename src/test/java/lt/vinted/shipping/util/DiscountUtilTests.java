package lt.vinted.shipping.util;

import static org.junit.jupiter.api.Assertions.*;
import lt.vinted.shipping.model.DeliveryInput;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DiscountUtilTests {
    @Autowired
    private DiscountUtil discountUtil;

    @Test
    public void getDiscountForTransactionMrFullLimitValid() {
        DeliveryInput di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        List<DeliveryInput> dli = new ArrayList<>();
        dli.add(di);

        assertEquals("2015-02-01 S MR 1.50 0.50\n", discountUtil.getDiscounts(dli));
    }

    @Test
    public void getDiscountForTransactionMrFullLimitInvalid() {
        DeliveryInput di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(false);

        List<DeliveryInput> dli = new ArrayList<>();
        dli.add(di);

        assertEquals("2015-02-01 S MR Ignored\n", discountUtil.getDiscounts(dli));
    }

    @Test
    public void getDiscountForTransactionsLpThirdFreeLValid() {
        List<DeliveryInput> dli = new ArrayList<>();
        DeliveryInput di;

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        assertEquals("2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 0 6.90\n", discountUtil.getDiscounts(dli));
    }

    @Test
    public void getDiscountForTransactionsLpThirdFreeLSameMonthValid() {
        List<DeliveryInput> dli = new ArrayList<>();
        DeliveryInput di;

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        assertEquals("2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 0 6.90\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n", discountUtil.getDiscounts(dli));
    }

    @Test
    public void getDiscountForTransactionsLpThirdFreeLNewMonthValid() {
        List<DeliveryInput> dli = new ArrayList<>();
        DeliveryInput di;

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-03-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-03-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-03-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        assertEquals("2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 0 6.90\n2015-03-01 L LP 6.90 -\n2015-03-01 L LP 6.90 -\n2015-03-01 L LP 0 6.90\n", discountUtil.getDiscounts(dli));
    }

    @Test
    public void getDiscountForTransactionsExceedMonthlyLimitValid() {
        List<DeliveryInput> dli = new ArrayList<>();
        DeliveryInput di;

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("L");
        di.setCarrier("LP");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");
        di.setValid(true);

        dli.add(di);

        assertEquals("2015-02-01 L LP 6.90 -\n2015-02-01 L LP 6.90 -\n2015-02-01 L LP 0 6.90\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.50 0.50\n2015-02-01 S MR 1.90 0.10\n2015-02-01 S MR 2.00 -\n", discountUtil.getDiscounts(dli));
    }
}
