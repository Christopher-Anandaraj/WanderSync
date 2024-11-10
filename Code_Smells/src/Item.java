class Item {
    private String name;
    private double price;
    private int quantity;
    private DiscountType discountType;
    private double discountAmount;

    public Item(String name, double price, int quantity, DiscountType discountType, double discountAmount) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discountType = discountType;
        this.discountAmount = discountAmount;
    }

    public static class GiftCardItem extends Item {
        public GiftCardItem(String name, double price, int quantity){
            super(name, price, quantity, DiscountType.AMOUNT, 0);
        }
    }

/* Victor: To address the Bloater code smell (Long Parameter List), we applied Collapse Hierarchy by
* merging GiftCardItem into Item as a nested class. Since GiftCardItem doesn’t differ significantly
* from Item, we removed DiscountType and discountAmount to focus on attributes unique to gift cards.
* Adjustments were also made in Main to reflect this streamlined structure. */

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }
}