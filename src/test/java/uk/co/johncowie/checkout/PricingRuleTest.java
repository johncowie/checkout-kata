package uk.co.johncowie.checkout;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PricingRuleTest {

    @Test
    public void testPricingRuleWithoutOffer() {
        PricingRule pricingRule = new PricingRule("A", 20);

        assertEquals(0, pricingRule.priceForQuantity(0));
        assertEquals(80, pricingRule.priceForQuantity(4));
        assertEquals(140, pricingRule.priceForQuantity(7));
    }

    @Test
    public void testPricingRuleWithOffer() {
        PricingRule pricingRule = new PricingRule("A", 30, new Offer(3, 70));

        assertEquals(30, pricingRule.priceForQuantity(1));
        assertEquals(60, pricingRule.priceForQuantity(2));
        assertEquals(70, pricingRule.priceForQuantity(3));
        assertEquals(100, pricingRule.priceForQuantity(4));
        assertEquals(140, pricingRule.priceForQuantity(6));
    }

}
