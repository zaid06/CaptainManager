package bilal.com.captain;

import java.util.Date;

/**
 * Created by shame on 2018-01-10.
 */

public class StartRide {
    String name;
    String key;
    Double StartLatitude;
    Double StartLongitude;
    Double EndLatitude;
    Double EndLongitude;
    String time;


    public StartRide() {
    }



    public StartRide(String key, String name, Double StartLatitude, Double StartLongitude,Double EndLatitude,Double EndLongitude, String time) {
        this.key = key;
        this.name = name;
        this.StartLatitude = StartLatitude;
        this.StartLongitude = StartLongitude;
        this.EndLatitude = EndLatitude;
        this.EndLongitude = EndLongitude;
        this.time = time;
    }

    public Double getStartLatitude() {
        return StartLatitude;
    }

    public Double getStartLongitude() {
        return StartLongitude;
    }

    public Double getEndLatitude() {
        return EndLatitude;
    }

    public Double getEndLongitude() {
        return EndLongitude;
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
