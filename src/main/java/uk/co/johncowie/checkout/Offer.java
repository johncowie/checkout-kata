package uk.co.johncowie.checkout;

public class Offer {

    private final int quantity;
    private final int price;

    public Offer(int quantity, int price) {
        this.quantity = quantity;
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public static Offer noOffer(int price) {
        return new Offer(1, price);
    }
}
