import java.util.List;

public class Order {
    private List<Item> items;
    private Customer customer;

    public Order(List<Item> items, String customerName, String customerEmail) {
        this.items = items;
        this.customer = new Customer(customerName, customerEmail);
        customer.setCustomerName(customerName);
        customer.setCustomerEmail(customerEmail);
    }

    public double calculateTotalPrice() {
    	double total = 0.0;
    	for (Item item : items) {
        	double price = item.getPrice();
        	switch (item.getDiscountType()) {
            	case PERCENTAGE:
                	price -= item.getDiscountAmount() * price;
                	break;
            	case AMOUNT:
                	price -= item.getDiscountAmount();
                	break;
            	default:
                	// no discount
                	break;
        	}
        	total += price * item.getQuantity();
       	    if (item instanceof TaxableItem) {
                TaxableItem taxableItem = (TaxableItem) item;
                double tax = taxableItem.getTaxRate() / 100.0 * item.getPrice();
                total += tax;
            }
        }
    	if (hasGiftCard()) {
        	total -= 10.0; // subtract $10 for gift card
    	}
    	if (total > 100.0) {
        	total *= 0.9; // apply 10% discount for orders over $100
    	}
    	return total;
    }

    public void sendConfirmationEmail() {
        double totalPrice = calculateTotalPrice();
        EmailSender.sendOrderConfirmationEmail(customer, items, totalPrice);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public boolean hasGiftCard() {
        boolean has_gift_card = false;
        for (Item item : items) {
            if (item instanceof Item.GiftCardItem) {
                has_gift_card = true;
                break;
            }
        }
        return has_gift_card;
    }

   public void printOrder() {
        System.out.println("Order Details:");
        for (Item item : items) {
            System.out.println(item.getName() + " - " + item.getPrice());
        }
   }

   public void addItemsFromAnotherOrder(Order otherOrder) {
        for (Item item : otherOrder.getItems()) {
            items.add(item);
        }
   }

}

