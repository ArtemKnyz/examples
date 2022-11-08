public class UntrustworthyMailWorker implements MailService {
    RealMailService realMailService;

    Spy spy;
    Thief thief;
    UntrustworthyMailWorker untrustworthyMailWorker;
    Inspector inspector;

    MailService [] ms;


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
