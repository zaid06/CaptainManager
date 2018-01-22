package bilal.com.captain.Util;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ikodePC-1 on 1/8/2018.
 */

public class Util {

    public static final int REQUEST_CODE_CAPTURE_IMAGE = 124;

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

    public static boolean emailValidate(EditText edittext){
        String validate = edittext.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(validate).matches()){

            edittext.setError("Email Format Is Invalid");

            return false;

        }

        return true;
    }


    public static void tvInvalidate(EditText tv){

        tv.setError(null);

    }


}
