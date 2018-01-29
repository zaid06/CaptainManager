package bilal.com.captain.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

/**
 * Created by BILAL on 11/16/2017.
 */

public class SaveInSharedPreference {
    Context context;
    static SaveInSharedPreference saveInSharedPreference;

    public SaveInSharedPreference(Context context) {
        this.context = context;
    }

    public static SaveInSharedPreference getInSharedPreference(Context context) {
        if (saveInSharedPreference == null) {
            saveInSharedPreference = new SaveInSharedPreference(context);
        }
        return saveInSharedPreference;
    }

    public void setNotification(String key){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("key", key);
        editor.apply();

    }
    public boolean getNotification(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return key.equals(prefs.getString("key", ""));

    }

    public void setName(String name){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.apply();
    }

    public String getName(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("name", "");

    }

}
