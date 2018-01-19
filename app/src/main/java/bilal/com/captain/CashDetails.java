package bilal.com.captain;

/**
 * Created by shame on 2018-01-17.
 */

public class CashDetails {
    String date;
    String cash;

    public CashDetails(String date, String cash) {
        this.date = date;
        this.cash = cash;
    }

    public CashDetails() {
    }

    public String getDate() {
        return date;
    }

    public String getCash() {
        return cash;
    }
}
