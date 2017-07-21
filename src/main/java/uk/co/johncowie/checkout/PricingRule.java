package uk.co.johncowie.checkout;

public class PricingRule {

    private final String sku;
    private final int price;
    private final Offer offer;

    public PricingRule(String sku, int price) {
        this(sku, price, null);
    }

    public PricingRule(String sku, int price, Offer offer) {
        this.sku = sku;
        this.price = price;
        this.offer = offer;
    }

    public String getSku() {
        return sku;
    }

    public int priceForQuantity(int quantity) {
        if (offer != null) {
            return (quantity / offer.getQuantity()) * offer.getPrice() + quantity % offer.getQuantity() * price;
        }
        return price * quantity;
    }

}
