import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Victor: Refactored Main by renaming the class to follow proper naming conventions,
 * changing main.java to Main.java for consistency and readability. */

public class Main {

        static Logger logger = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) {
        Item item1 = new Item("Book", 20, 1, DiscountType.AMOUNT, 5);
        Item item2 = new TaxableItem("Laptop", 1000, 1, DiscountType.PERCENTAGE, 0.1);
        Item item3 = new Item.GiftCardItem("Gift Card", 10, 1);

/* To address the Bloater code smell (Long Parameter List) for GiftCardItem,
 * we focused on the attributes that make it distinct as a gift card.
 * We removed unnecessary parameters, such as DiscountType and discountAmount,
 * and encapsulated GiftCardItem as a nested static class within Item with simplified parameters.*/

        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Order order = new Order(items, "John Doe", "johndoe@example.com");

        logger.log(Level.INFO,"Total Price: {0}", order.calculateTotalPrice());

        order.sendConfirmationEmail();

        order.printOrder();
    }
}
