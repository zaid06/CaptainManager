package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/15/2018.
 */

public class IncomeModel {

    int income;

    String key;

    String incometype;

    String latitude;

    String longitude;

    String date;

    public IncomeModel(int income, String key, String incometype, String date,String latitude, String longitude) {
        this.income = income;
        this.key = key;
        this.incometype = incometype;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public IncomeModel() {
    }

    public String getIncometype() {
        return incometype;
    }

    public void setIncometype(String incometype) {
        this.incometype = incometype;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
