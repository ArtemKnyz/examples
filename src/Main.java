import java.io.IOException;
import java.util.Arrays;
import java.util.logging.*;

public class Main {


    //org.stepic.java.logging.ClassA
    private static final Logger LOGGERA = Logger.getLogger(Main.class.getName());

    final Logger LOGGERB = Logger.getLogger("org.stepic.java.logging.ClassB");
    final Logger LOGGER = Logger.getLogger("org.stepic.java");
    private static ConsoleHandler handler = new ConsoleHandler();
    private static  FileHandler fh;

    static {
        try {
            fh = new FileHandler("F:\\Temp\\test.txt");
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.WARNING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final String AUSTIN_POWERS = "Austin Powers";
    public static final String WEAPONS = "weapons";
    public static final String BANNED_SUBSTANCE = "banned substance";

    public static void main(String[] args){
        Logger logger = Logger.getLogger(Main.class.getName());

        Inspector inspector = new Inspector();
        Spy spy = new Spy(logger);
        Thief thief = new Thief(10000);
        MailService variousWorkers[] = new MailService[]{spy, thief, inspector};
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
        Arrays.stream(correspondence).forEach(parcell -> {
            try {
                worker.processMail(parcell);
            } catch (StolenPackageException e) {
                logger.log(Level.WARNING, "Inspector found stolen package: " + e);
            } catch (IllegalPackageException e) {
                logger.log(Level.WARNING, "Inspector found illegal package: " + e);
            }
        });



    }


    private static void configureLogging() throws IOException {
        final Logger LOGGERA = Logger.getLogger("org.stepic.java.logging.ClassA");

        final Logger LOGGERB = Logger.getLogger("org.stepic.java.logging.ClassB");
        final Logger LOGGER = Logger.getLogger("org.stepic.java");

        LOGGERA.setLevel(Level.ALL);
        LOGGERB.setLevel(Level.WARNING);

       ConsoleHandler handler = new ConsoleHandler();

        FileHandler fh = new FileHandler("F:\\Temp\\test.txt");
        fh.setFormatter(new SimpleFormatter());
        fh.setLevel(Level.ALL);

        LOGGERA.addHandler(fh);

        handler.setLevel(Level.ALL);
        handler.setFormatter(new XMLFormatter());
        LOGGER.addHandler(handler);
        LOGGER.setUseParentHandlers(false);


    }

    public static int[] mergeArrays(int[] a1, int[] a2) {
        int[] result = new int[a1.length + a2.length];
        int pos1 = 0;
        int pos2 = 0;
        while (pos1 < a1.length || pos2 < a2.length) {
            result[pos1 + pos2] = (pos1 < a1.length && (pos2 == a2.length || a1[pos1] < a2[pos2]) ?
                    a1[pos1++] : a2[pos2++]);
        }
        return result;


        // return sortedArray; // your implementation here
    }
}
