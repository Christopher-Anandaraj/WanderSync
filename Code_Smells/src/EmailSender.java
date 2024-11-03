import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailSender {
    static Logger logger = Logger.getLogger(EmailSender.class.getName());
    private EmailSender() {}

    public static void sendOrderConfirmationEmail(Customer customer, List<Item> items, double totalPrice) {
        String message = "Thank you for your order, " + customer.getCustomerName() + "!\n\n" +
                "Your order details:\n";
        for (Item item : items) {
            message += item.getName() + " - " + item.getPrice() + "\n";
        }
        message += "Total: " + totalPrice;

        sendEmail(customer.getCustomerEmail(), "Order Confirmation", message);
    }

    public static void sendEmail(String customerEmail, String subject, String message) {
        logger.log(Level.INFO, "Email to: {0}", customerEmail);
        logger.log(Level.INFO, "Subject: {0}", subject);
        logger.log(Level.INFO, "Body: {0}", message);
    }
}
