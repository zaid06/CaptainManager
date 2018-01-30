package bilal.com.captain;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

import bilal.com.captain.Util.OpenLocation;

/**
 * Created by shame on 2018-01-30.
 */

public class UiThread {

    private Timer timer;
    private TimerTask timerTask;

    OpenLocation openLocation = new OpenLocation();

    public void thread(final Context context){

        timer = new Timer();

        timerTask = new TimerTask() {
            @Override
            public void run() {

                openLocation.openLocation(context);

            }
        };

        timer.schedule(timerTask,10000,10000);
    }

}
