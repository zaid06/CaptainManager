package bilal.com.captain.Util;

import android.content.Context;

import com.valdesekamdem.library.mdtoast.MDToast;

/**
 * Created by ikodePC-1 on 1/8/2018.
 */

public class CustomToast {
    public static void showToast(Context c, String message, int type){
        MDToast mdToast = MDToast.makeText(c,message, MDToast.LENGTH_LONG,type);
        mdToast.show();
    }
}
