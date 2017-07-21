package uk.co.johncowie.checkout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutTransaction {

    private final Map<String, PricingRule> prices;
    private final Map<String, Integer> itemQuantities;

    public static CheckoutTransaction startTransaction(List<PricingRule> pricingRules) {
        try {
            Map<String, PricingRule> prices = pricingRules
                    .stream()
                    .collect(Collectors.toMap(PricingRule::getSku, Function.identity()));
            return new CheckoutTransaction(prices);
        } catch (IllegalStateException e) {
            throw new MultiplePricesForItemException();
        }
    }

    private CheckoutTransaction(Map<String, PricingRule> prices) {
        this.prices = prices;
        this.itemQuantities = new HashMap<>();
    }

    public void scanItem(String sku) {
        if (prices.containsKey(sku)) {
            itemQuantities.putIfAbsent(sku, 0);
            itemQuantities.compute(sku, (s, i) -> ++i);
        } else {
            throw new NoPriceForItemException();
        }
    }

    private int totalPriceForSku(Map.Entry<String, Integer> skuEntry) {
        PricingRule pricingRule = prices.get(skuEntry.getKey());
        return pricingRule.priceForQuantity(skuEntry.getValue());
    }

    public int calculateTotalPrice() {
        return itemQuantities
                .entrySet()
                .stream()
                .mapToInt(this::totalPriceForSku)
                .sum();
    }

}

class MultiplePricesForItemException extends RuntimeException {}
class NoPriceForItemException extends RuntimeException {}

