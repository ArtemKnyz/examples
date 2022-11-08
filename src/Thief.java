public class Thief implements MailService{
    private static int minPrice;
    private static int stolenPrice;

    public Thief(int minPrice) {
        this.minPrice = minPrice;
    }

    int getStolenValue(){
        return stolenPrice;
    }
    @Override
    public Sendable processMail(Sendable mail) {
        if(mail instanceof Package){

            return null;
        }
        return mail;
    }




}
