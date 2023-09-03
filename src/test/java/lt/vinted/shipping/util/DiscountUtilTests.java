package lt.vinted.shipping.util;

import static org.junit.jupiter.api.Assertions.*;
import lt.vinted.shipping.model.DeliveryInput;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DiscountUtilTests {
    @Autowired
    private DiscountUtil discountUtil;

    @Test
    public void getDiscountForTransactionLpFullLimit() {
        DeliveryInput di = new DeliveryInput();
        di.setTrxDate("2015-02-01");
        di.setPackageSize("S");
        di.setCarrier("MR");

        List<DeliveryInput> dli = new ArrayList<>();
        dli.add(di);

        assertEquals("2015-02-01 S MR 1.50 0.50", discountUtil.getDiscounts(dli));
    }
}
