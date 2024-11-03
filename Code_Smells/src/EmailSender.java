import java.util.List;

public class EmailSender {

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
        System.out.println("Email to: " + customerEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + message);
    }
}
