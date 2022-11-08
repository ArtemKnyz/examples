public class MailPackage extends AbstractSendable{
    private final Package content;

    public MailPackage(String from, String to, Package con) {
        super(from, to);
        content=con;

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
