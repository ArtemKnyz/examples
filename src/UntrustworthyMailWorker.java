import java.util.logging.Logger;

public class UntrustworthyMailWorker implements MailService {
    RealMailService realMailService;

    private Logger loger = null;
    Spy spy = null;
    Thief thief = new Thief(100);
    //UntrustworthyMailWorker untrustworthyMailWorker;
    Inspector inspector = new Inspector();

    MailService [] ms = {spy, inspector, thief};


    public UntrustworthyMailWorker(MailService[] mailServices) {
        ms = mailServices;
    }

    RealMailService getRealMailService() {
        return realMailService;
    }

    @Override
    public Sendable processMail(Sendable mail) {
        if(mail instanceof Sendable){
            
            return null;
        }
        return null;
    }
}
