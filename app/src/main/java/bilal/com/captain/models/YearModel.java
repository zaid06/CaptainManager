package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/25/2018.
 */

public class YearModel {

    String year;

    String key;

    public YearModel(String year, String key) {
        this.year = year;
        this.key = key;
    }

    public YearModel() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
