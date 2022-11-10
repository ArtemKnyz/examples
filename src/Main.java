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
        Thief thief = new Thief(100);
        MailService variousWorkers[] = {spy, thief, inspector};
        UntrustworthyMailWorker worker = new UntrustworthyMailWorker(variousWorkers);

        Sendable correspondence[] = {
                new MailMessage("Oxxxymiron", "Гнойный", "Я здесь чисто по фану, поглумиться над слабым\n" +
                        "Ты же вылез из мамы под мой дисс на Бабана...."),
                new MailMessage("Гнойный", "Oxxxymiron", "....Что? Так болел за Россию, что на нервах терял ганглии.\n" +
                        "Но когда тут проходили митинги, где ты сидел? В Англии!...."),
                new MailMessage("Жриновский", AUSTIN_POWERS, "Бери пацанов, и несите меня к воде."),
                new MailMessage(AUSTIN_POWERS, "Пацаны", "Го, потаскаем Вольфовича как Клеопатру"),
                new MailPackage("берег", "море", new Package("ВВЖ", 32)),
                new MailMessage("NASA", AUSTIN_POWERS, "Найди в России ракетные двигатели и лунные stones"),
                new MailPackage(AUSTIN_POWERS, "NASA", new Package("рпакетный двигатель ", 2500000)),
                new MailPackage("AUSTIN", "NASA", new Package("new sto", 100500)),
                new MailPackage("Китай", "КНДР", new Package("banned substance", 113)),
                new MailPackage(AUSTIN_POWERS, "ИГИЛ (запрещенная группировка", new Package("tiny bomb", 92)),
                new MailPackage("Иран", "Террорист", new Package("weap to somebody", 100)),
                new MailPackage("Pit", "Тom", new Package("sun for all", 101)),
                new MailMessage("defog", "Психиатр", "something really good"),
        };

        for (Sendable as : correspondence) {
            try {
                worker.processMail(as);
            } catch (Exception e) {
                logger.log(Level.WARNING, "Inspector found somthing interesting");
            }
        }

    }

    interface Sendable {
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
        private String content;
        private final int price;

        public Package(String con, int price) {
            content = con;
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
        private RealMailService realMailService = new RealMailService();
        private MailService[] ms;

        public UntrustworthyMailWorker(MailService[] mailServices) {
            ms = mailServices;
        }

        public MailService getRealMailService() {
            return realMailService;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            Sendable sm = mail;
            for (MailService notTrsutPerson : ms) {
                sm = notTrsutPerson.processMail(sm);
            }
            return realMailService.processMail(mail);
        }
    }


    public static class Spy implements MailService {
        private Logger logger;

        public Spy(Logger logg) {
            logger = logg;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailMessage) {
                if (((MailMessage) mail).from.equals(AUSTIN_POWERS) || ((MailMessage) mail).to.equals(AUSTIN_POWERS)) {
                    logger.log(Level.WARNING, "Detected target mail correspondence: from {0} to {1} \"{2}\"", new String[]{((MailMessage) mail).from, ((MailMessage) mail).to, ((MailMessage) mail).message});
                } else {
                    logger.log(Level.INFO, "Usual correspondence: from {0} to {1}", new String[]{((MailMessage) mail).from, ((MailMessage) mail).to});
                }
            }
            return mail;
        }
    }

    public static class Thief implements MailService {
        private final int minPricePackage;
        private int stolenPrice;

        public Thief(int minPrice) {
            minPricePackage = minPrice;
        }

        public int getStolenValue() {
            return stolenPrice;
        }

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                Package pac = ((MailPackage) mail).getContent();
                if (pac.price >= minPricePackage) {
                    stolenPrice += pac.getPrice();
                    mail = new MailPackage(((MailPackage) mail).from, ((MailPackage) mail).to, new Package("stones instead of " + pac.getContent(), 0));
                }
            }
            return mail;
        }
    }


    public static class Inspector implements MailService {

        @Override
        public Sendable processMail(Sendable mail) {
            if (mail instanceof MailPackage) {
                Package pac = ((MailPackage) mail).getContent();
                String content = pac.getContent();
                if ((content.contains(WEAPONS)) || (content.contains(BANNED_SUBSTANCE))) {
                    throw new IllegalPackageException();
                }
                if (content.contains("stones instead of")) {
                    throw new StolenPackageException();
                }
            }
            return mail;
        }
    }

    public static class StolenPackageException extends RuntimeException {
        public StolenPackageException() {
        }
    }

    public static class IllegalPackageException extends RuntimeException {
        public IllegalPackageException() {
        }
    }


    interface MailService {
        Sendable processMail(Sendable mail);
    }


}
