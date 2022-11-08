public class Inspector implements MailService {

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
