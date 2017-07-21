package uk.co.johncowie.checkout;

public class PricingRule {

    private final String sku;
    private final int price;
    private final Offer offer;

    public PricingRule(String sku, int price) {
        this(sku, price, Offer.noOffer(price));
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
        int offerPrice = offer.getPrice();
        int offerGroups = quantity / offer.getQuantity();
        int outsideOffer = quantity % offer.getQuantity();
        return offerGroups * offerPrice + outsideOffer * price;
    }

}
