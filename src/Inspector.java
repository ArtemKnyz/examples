public class Inspector implements MailService {

    @Override
    public Sendable processMail(Sendable mail) {
        if (mail instanceof Package) {
            if (mail.equals("weapons") || mail.equals("banned substance")) {
                throw new IllegalPackageException();
            }
            if (mail.equals("stones")) {
                throw new StolenPackageException();
            }
            //return null;
        }
        return mail;
    }
}
