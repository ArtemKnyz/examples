import java.util.logging.Level;
import java.util.logging.Logger;

public class Spy implements MailService {
    Logger  logger;

    public Spy(Logger logger) {
        this.logger = logger;
    }
    MailMessage mailMessage = new MailMessage("", "", "");



    @Override
    public Sendable processMail(Sendable mail) {
        if(mail instanceof MailMessage){
            if(mail.equals("Austin Powers")) {
                logger.log(Level.WARNING, "Detected target mail correspondence: from {from} to {to} \"{message}");
            } else {
                logger.log(Level.INFO, "Usual correspondence: from {from} to {to}");
            }
        }
        return mail;
    }
}
