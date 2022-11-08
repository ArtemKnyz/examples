public class Thief implements MailService {
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
