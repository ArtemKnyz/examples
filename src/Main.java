import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static final String AUSTIN_POWERS = "Austin Powers";
    public static final String WEAPONS = "weapons";
    public static final String BANNED_SUBSTANCE = "banned substance";

    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());

        Inspector inspector = new Inspector();
        Spy spy = new Spy(logger);
        Thief thief = new Thief(10000);
        MailService variousWorkers[] = {spy, thief, inspector};
        UntrustworthyMailWorker worker = new UntrustworthyMailWorker(variousWorkers);

        AbstractSendable correspondence[] = {
                new MailMessage("Oxxxymiron", "Гнойный", "Я здесь чисто по фану, поглумиться над слабым\n" +
                        "Ты же вылез из мамы под мой дисс на Бабана...."),
                new MailMessage("Гнойный", "Oxxxymiron", "....Что? Так болел за Россию, что на нервах терял ганглии.\n" +
                        "Но когда тут проходили митинги, где ты сидел? В Англии!...."),
                new MailMessage("Жриновский", AUSTIN_POWERS, "Бери пацанов, и несите меня к воде."),
                new MailMessage(AUSTIN_POWERS, "Пацаны", "Го, потаскаем Вольфовича как Клеопатру"),
                new MailPackage("берег", "море", new Package("ВВЖ", 32)),
                new MailMessage("NASA", AUSTIN_POWERS, "Найди в России ракетные двигатели и лунные stones"),
                new MailPackage(AUSTIN_POWERS, "NASA", new Package("рпакетный двигатель ", 2500000)),
                new MailPackage(AUSTIN_POWERS, "NASA", new Package("stones", 1000)),
                new MailPackage("Китай", "КНДР", new Package("banned substance", 99)),
                new MailPackage(AUSTIN_POWERS, "ИГИЛ (запрещенная группировка", new Package("tiny bomb", 9000)),
                new MailMessage(AUSTIN_POWERS, "Психиатр", "Помогите"),
        };

        for (AbstractSendable as : correspondence) {
            worker.processMail(as);
        }


    }

    static interface Sendable {
        String getFrom();

        String getTo();
    }

    public static abstract class AbstractSendable implements Sendable {
        protected final String from;
        protected final String to;

        public AbstractSendable(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AbstractSendable that = (AbstractSendable) o;

            if (!from.equals(that.from)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }
    }

    public static class MailMessage extends AbstractSendable {
        private final String message;

        public MailMessage(String from, String to, String message) {
            super(from, to);
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailMessage that = (MailMessage) o;

            if (message != null ? !message.equals(that.message) : that.message != null) return false;

            return true;
        }
    }

    public static class MailPackage extends AbstractSendable {
        private final Package content;

        public MailPackage(String from, String to, Package con) {
            super(from, to);
            content = con;

        }


        public Package getContent() {
            return content;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            MailPackage that = (MailPackage) o;

            if (!content.equals(that.content)) return false;

            return true;
        }
    }

    public static class Package {
        private static String content;
        private final int price;

        public Package(String content, int price) {
            this.content = content;
            this.price = price;
        }

        public String getContent() {
            return content;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Package aPackage = (Package) o;

            if (price != aPackage.price) return false;
            if (!content.equals(aPackage.content)) return false;

            return true;
        }
    }

    public static class RealMailService implements MailService {
        @Override
        public Sendable processMail(Sendable mail) {

            return mail;
        }
    }


    public static class UntrustworthyMailWorker implements MailService {
        RealMailService realMailService = new RealMailService();
        private Logger loger = Logger.getLogger(UntrustworthyMailWorker.class.getName());
        Spy spy = new Spy(loger);
        Thief thief = new Thief(100);
        Inspector inspector = new Inspector();

        MailService[] ms = {spy, inspector, thief};

        public UntrustworthyMailWorker(MailService[] mailServices) {
            ms = mailServices;
        }

        RealMailService getRealMailService() {
            return realMailService;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof Sendable) {
                spy.processMail(mail);
                thief.processMail(mail);
                inspector.processMail(mail);
                realMailService.processMail(mail);
            }
            return mail;
        }
    }

    public static class Spy implements MailService {
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
                if (((MailMessage) mail).from.equals(AUSTIN_POWERS)) {
                    logger.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1} \"{2}\"", new String[]{((MailMessage) mail).from, ((MailMessage) mail).to, ((MailMessage) mail).message});
                } else {
                    logger.log(Level.INFO, "Usual correspondence: from {0} to {1}", new String[]{((MailMessage) mail).from, ((MailMessage) mail).to});
                }
            }
            return mail;
        }
    }

    public static class Thief implements MailService {
        private static int minPricePackage;
        private static int stolenPrice;
        String cont = null;


        public Thief(int minPrice) {
            minPricePackage = minPrice;
        }

        int getStolenValue(int costPack) {
            stolenPrice = stolenPrice + costPack;
            return stolenPrice;
        }


        @Override
        public Sendable processMail(Sendable mail) {

            if (mail instanceof Package) {
                if (((Package) mail).getPrice() > minPricePackage) {
                    getStolenValue(((Package) mail).getPrice());
                    cont = ((Package) mail).getContent();
                    Package p = new Package("stones instead of " + cont, 0);
                    mail = (Sendable) p;
                }
            }
            return mail;
        }
    }

    public static class Inspector implements MailService {

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof Package) {
                if (((Package) mail).getContent().matches("weapons") || ((Package) mail).getContent().matches("banned substance")) {
                    throw new IllegalPackageException();
                }
                if (((Package) mail).getContent().matches("stones")) {
                    throw new StolenPackageException();
                }
            }
            return mail;
        }
    }

    public static class StolenPackageException extends RuntimeException {
        public StolenPackageException() {
        }

        public StolenPackageException(String message) {
            super(message);
        }
    }

    public static class IllegalPackageException extends RuntimeException {
        public IllegalPackageException() {
        }

        public IllegalPackageException(String message) {
            super(message);
        }
    }


    static interface MailService {
        Sendable processMail(Sendable mail);
    }


}
