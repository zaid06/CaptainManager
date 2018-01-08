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

    //making session method start driver
    public void setIsLogin(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLogin", value);
        editor.apply();
    }

    public boolean getIsLogin() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("isLogin", false);
    }

    public void setSyncDataStatus(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sync_data", value);
        editor.apply();
    }

    public boolean getSyncDataStatus() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("sync_data", false);
    }

    public void setPopId(String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pop_id", value);
        editor.apply();
    }

    public String getPopId() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("pop_id", "");
    }

    public void setVisitStatus(boolean value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("visit_status", value);
        editor.apply();
    }

    public boolean getVisitStatus() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("visit_status", false);
    }

    public void saveUserData(String userId, String fname, String lname, String email, String phone,
                             String pasw, String role_id, String data_role_id, String user_status,
                             String created_at,String created_by, String modified_at, String modified_by,
                             String town_id,String distributor_id, String trade_type_id){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userId", userId);
        editor.putString("fname", fname);
        editor.putString("lname", lname);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("pasw", pasw);
        editor.putString("role_id", role_id);
        editor.putString("data_role_id", data_role_id);
        editor.putString("user_status", user_status);
        editor.putString("created_at", created_at);
        editor.putString("created_by", created_by);
        editor.putString("modified_at", modified_at);
        editor.putString("modified_by", modified_by);
        editor.putString("town_id", town_id);
        editor.putString("distributor_id", distributor_id);
        editor.putString("trade_type_id", trade_type_id);
        editor.apply();
    }

    public HashMap<String, String> getUserData(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("userId", prefs.getString("userId", ""));
        hashMap.put("fname", prefs.getString("fname", ""));
        hashMap.put("lname", prefs.getString("lname", ""));
        hashMap.put("email", prefs.getString("email", ""));
        hashMap.put("phone", prefs.getString("phone", ""));
        hashMap.put("pasw", prefs.getString("pasw", ""));
        hashMap.put("role_id", prefs.getString("role_id", ""));
        hashMap.put("data_role_id", prefs.getString("data_role_id", ""));
        hashMap.put("user_status", prefs.getString("user_status", ""));
        hashMap.put("created_at", prefs.getString("created_at", ""));
        hashMap.put("created_by", prefs.getString("created_by", ""));
        hashMap.put("modified_at", prefs.getString("modified_at", ""));
        hashMap.put("modified_by", prefs.getString("modified_by", ""));
        hashMap.put("town_id", prefs.getString("town_id", ""));
        hashMap.put("distributor_id", prefs.getString("distributor_id", ""));
        hashMap.put("trade_type_id", prefs.getString("trade_type_id", ""));
        return hashMap;
    }

    public void setNewID(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public HashMap<String, String> getNewIDs(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("DISPLAY_ID", prefs.getString("DISPLAY_ID", "new_0"));
        hashMap.put("DISPLAY_DETAIL_ID", prefs.getString("DISPLAY_DETAIL_ID", "new_0"));
        hashMap.put("ISSUE_ID", prefs.getString("ISSUE_ID", "new_0"));
        hashMap.put("SUB_ISSUE_ID", prefs.getString("SUB_ISSUE_ID", "new_0"));
        hashMap.put("ACTIVITY_ID", prefs.getString("ACTIVITY_ID", "new_0"));
        hashMap.put("SUB_ACTIVITY_ID", prefs.getString("SUB_ACTIVITY_ID", "new_0"));
        hashMap.put("COMPETITOR_ACTIVITY_ID", prefs.getString("COMPETITOR_ACTIVITY_ID", "new_0"));
        hashMap.put("SUB_COMPETITOR_ACTIVITY_ID", prefs.getString("SUB_COMPETITOR_ACTIVITY_ID", "new_0"));
        hashMap.put("SUB_PLANOGRAM_ID", prefs.getString("SUB_PLANOGRAM_ID", "new_0"));

        return hashMap;
    }


    // TODO BAKHTIYAR


    public void setPermission(int check) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("permission", check);
        editor.apply();
    }

    public int getPermission() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt("permission", 0);
    }


    public void deleteShared(){

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//        preferences.Editor

    }


    public void deleteUserFromLocalWhenLogOut(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("userId");
        editor.remove("fname");
        editor.remove("lname");
        editor.remove("email");
        editor.remove("pasw");
        editor.remove("role_id");
        editor.remove("data_role_id");
        editor.remove("user_status");
        editor.remove("created_at");
        editor.remove("created_by");
        editor.remove("modified_at");
        editor.remove("modified_by");
        editor.remove("town_id");
        editor.remove("distributor_id");
        editor.remove("trade_type_id");
        editor.commit();

    }

}
