import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SendEmail {
    public class EmailUtil {

        //Regex for email validation
        private static final String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        private static boolean isValidEmail(String email) {
            return Pattern.matches(EMAIL_REGEX, email);
        }

        public static void sendEmail() {
            Scanner sc = new Scanner(System.in);

            String fromUser ="gajerayash999@gmail.com";

            String fromUserPassword ="bxsh tnfc dawg jotg";

            System.out.print("Enter receiver email: ");
            String toEmail = sc.nextLine();

            System.out.print("Enter subject: ");
            String subject = sc.nextLine();

            System.out.print("Enter message body: ");
            String body = sc.nextLine();

            //Validate emails
            if (!isValidEmail(toEmail)) {
                System.out.println("Invalid receiver email: " + toEmail);
                return;
            }

            String host = "smtp.gmail.com";

            // Setup mail server properties
            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Create session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromUser, fromUserPassword);
                }
            });

            try {
                // Create message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(fromUser));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
                message.setSubject(subject);
                message.setText(body);

                // Send message
                Transport.send(message);

                System.out.println("Email successfully sent to " + toEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EmailUtil.sendEmail();
    }
}