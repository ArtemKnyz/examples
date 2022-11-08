import java.util.logging.Level;
import java.util.logging.Logger;

public class Spy implements MailService {
    Logger logger;
    String fromName = null;
    String toName = null;

    public Spy(Logger logger) {
        this.logger = logger;
    }

    MailMessage mailMessage = new MailMessage("from", "to", "someMessage");


    @Override
    public Sendable processMail(Sendable mail) {
        if (mail instanceof MailMessage) {
            if (((MailMessage) mail).from.equals("Austin Powers")) {
                logger.log(Level.WARNING, "Detected target mail correspondence: from {from} to {to} \"{message}", new String[]{mailMessage.from, mailMessage.to, mailMessage.getMessage()});
            } else {
                logger.log(Level.INFO, "Usual correspondence: from {from} to {to}", new String[]{mailMessage.from, mailMessage.to});
            }
        }
        return mail;
    }
}
