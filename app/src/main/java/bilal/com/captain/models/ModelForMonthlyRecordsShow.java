package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/25/2018.
 */

public class ModelForMonthlyRecordsShow {
    int income;

    String key;

    String incometype;

    String latitude;

    String longitude;

    String date;

    String monthly;

    String year;

    String type;

    int total;
    // month, record, total
    /* format 2/2018
                            month/year
                                    */
    public ModelForMonthlyRecordsShow(int income, String key, String incometype, String date,String latitude, String longitude,String monthly,String year, String type) {
        this.income = income;
        this.key = key;
        this.incometype = incometype;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.monthly = monthly;
        this.year = year;
        this.type = type;
    }

    public ModelForMonthlyRecordsShow(int total, String type){

        this.total = total;

        this.type = type;

    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ModelForMonthlyRecordsShow() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
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
