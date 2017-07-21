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
                new PricingRule("A", 50, new Offer(3, 130)),
                new PricingRule("B", 30, new Offer(2, 45)),
                new PricingRule("C", 20),
                new PricingRule("D", 15)
        );
        CheckoutTransaction transaction = CheckoutTransaction.startTransaction(pricingRules);

        transaction.scanItem("B");
        assertEquals(30, transaction.calculateTotalPrice());

        transaction.scanItem("A");
        assertEquals(80, transaction.calculateTotalPrice());

        transaction.scanItem("B");
        assertEquals(95, transaction.calculateTotalPrice());

        transaction.scanItem("C");
        assertEquals(115, transaction.calculateTotalPrice());

        transaction.scanItem("D");
        transaction.scanItem("D");
        assertEquals(145, transaction.calculateTotalPrice());
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
