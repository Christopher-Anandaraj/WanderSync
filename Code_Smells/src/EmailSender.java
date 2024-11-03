import java.util.List;
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
        logger.info("Email to: " + customerEmail);
        logger.info("Subject: " + subject);
        logger.info("Body: " + message);
    }
}
