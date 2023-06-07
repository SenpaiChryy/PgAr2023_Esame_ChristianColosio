import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Person {

    private String name;
    private String surname;
    private Calendar birthdate;
    private Sex sex;
    private String birthCity;
    private String taxIdCode;
    private Calendar expirationDate;
    private String realID;

    public Person(ArrayList<String> personData) {
        this.name = personData.get(0);
        this.surname = personData.get(1);
        this.sex = Sex.valueOf(personData.get(2));
        this.birthCity = personData.get(3);

        String[] birthday = personData.get(4).split("-");
        this.birthdate = new GregorianCalendar(Integer.parseInt(birthday[0]), Integer.parseInt(birthday[1]) - 1, Integer.parseInt(birthday[2]));

        this.taxIdCode = personData.get(5);

        String[] expirationDate = personData.get(6).split("-");
        this.expirationDate = new GregorianCalendar(Integer.parseInt(expirationDate[0]), Integer.parseInt(expirationDate[1]) - 1, Integer.parseInt(expirationDate[2]));

        this.realID = new TaxIdCode(surname, name, birthdate, sex, birthCity).getCode();
    }

    public String toStringCompact() {
        return "name: " + name + ", surname: " + surname + ", taxIdCode='" + taxIdCode +
                ", expirationDate: " + expirationDate.get(Calendar.WEEK_OF_YEAR) + "/" + expirationDate.get(Calendar.MONTH) +
                "/" + expirationDate.get(Calendar.YEAR) + ", realID " + realID;
    }

    public boolean compareID() {
        if (this.realID.equals(this.taxIdCode)) {
            return true;
        } else {
            return false;
        }
    }
}
