package uk.co.johncowie.checkout;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertEquals;

public class CheckoutTransactionTest {

    @Test
    public void testTotalPriceForPricingRules() {
        List<PricingRule> pricingRules = asList(
                new PricingRule("A", 10),
                new PricingRule("B", 16, new Offer(2, 16))
        );
        CheckoutTransaction transaction = CheckoutTransaction.startTransaction(pricingRules);

        transaction.scanItem("A");
        assertEquals(10, transaction.calculateTotalPrice());

        transaction.scanItem("B");
        assertEquals(26, transaction.calculateTotalPrice());

        transaction.scanItem("B");
        assertEquals(26, transaction.calculateTotalPrice());

        transaction.scanItem("A");
        assertEquals(36, transaction.calculateTotalPrice());
    }

    @Test(expected = NoPriceForItemException.class)
    public void testScanningItemWithoutPrice() {
        List<PricingRule> pricingRules = singletonList(new PricingRule("A", 10));

        CheckoutTransaction transaction = CheckoutTransaction.startTransaction(pricingRules);

        transaction.scanItem("B");
    }

    @Test(expected = MultiplePricesForItemException.class)
    public void testMultiplePricesForSameSku() {
        List<PricingRule> pricingRules = asList(new PricingRule("A", 10), new PricingRule("A", 20));

        CheckoutTransaction.startTransaction(pricingRules);
    }

}
