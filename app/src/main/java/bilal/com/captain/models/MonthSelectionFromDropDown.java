package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/26/2018.
 */

public class MonthSelectionFromDropDown {

    String monthly;

    String monthname;

    public MonthSelectionFromDropDown(String monthly) {
        this.monthly = monthly;
        this.monthname = monthname;
    }

    public String getMonthly() {
        return this.monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    public String getMonthname() {
        return this.monthname;
    }

    public void setMonthname(String monthname) {
        this.monthname = monthname;
    }

    @Override
    public String toString() {
        if(this.monthly.substring(0,2).equals("01")){
            return  "January:";
        }else if(this.monthly.substring(0,2).equals("02")){
            return "February:";
        }else if(this.monthly.substring(0,2).equals("03")){
            return "March:";
        }else if(this.monthly.substring(0,2).equals("04")){
            return"April:";
        }else if(this.monthly.substring(0,2).equals("05")){
            return "May:";
        }else if(this.monthly.substring(0,2).equals("06")){
            return "June:";
        }else if(this.monthly.substring(0,2).equals("07")){
            return "July:";
        }else if(this.monthly.substring(0,2).equals("08")){
            return "August:";
        }else if(this.monthly.substring(0,2).equals("09")){
            return "September:";
        }else if(this.monthly.substring(0,2).equals("10")){
            return "October:";
        }else if(this.monthly.substring(0,2).equals("11")){
            return "November:";
        }else if(this.monthly.substring(0,2).equals("12")){
            return "December:";
        }else {
            return "";
        }
    }
}
