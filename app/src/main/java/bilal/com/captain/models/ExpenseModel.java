package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/9/2018.
 */

public class ExpenseModel {

    String key;

    long expence;

    String type;

    String date;

    public ExpenseModel(String key, long expence, String type, String date) {
        this.key = key;
        this.expence = expence;
        this.type = type;
        this.date = date;
    }

    public ExpenseModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setExpence(long expence) {
        this.expence = expence;
    }

    public String getKey() {
        return key;
    }

    public long getExpence() {
        return expence;
    }
}
