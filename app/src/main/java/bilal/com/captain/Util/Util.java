package bilal.com.captain.Util;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ikodePC-1 on 1/8/2018.
 */

public class Util {

    public static boolean etValidate(EditText edittext){
        String validate = edittext.getText().toString();
        validate = validate.replaceAll("\\s+", " ").trim();
        if(validate.isEmpty())
        {
            edittext.setError("Required");
            return false;
        }
        return true;
    }

    public static void tvInvalidate(EditText tv){

        tv.setError(null);

    }


}
