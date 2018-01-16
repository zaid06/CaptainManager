package bilal.com.captain;

import java.util.Date;

/**
 * Created by shame on 2018-01-10.
 */

public class StartRide {
    String name;
    String key;
    Double startlatitude;
    Double startlongitude;
    Double endlatitude;
    Double endlongitude;
    String time;


    public StartRide() {
    }



    public StartRide(String key, String name, Double startlatitude, Double startlongitude,Double endlatitude,Double endlongitude, String time) {
        this.key = key;
        this.name = name;
        this.startlatitude = startlatitude;
        this.startlongitude = startlongitude;
        this.endlatitude = endlatitude;
        this.endlongitude = endlongitude;
        this.time = time;
    }

    public Double getStartlatitude() {
        return startlatitude;
    }

    public Double getStartlongitude() {
        return startlongitude;
    }

    public Double getEndlatitude() {
        return endlatitude;
    }

    public Double getEndlongitude() {
        return endlongitude;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getTime() {
        return time;
    }
}
