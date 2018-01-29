package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/26/2018.
 */

public class NotificationModel {
    String pushkey;

    String uid;

    String name;

    String message;

    public NotificationModel(String pushkey, String uid, String name, String message) {
        this.pushkey = pushkey;
        this.uid = uid;
        this.name = name;
        this.message = message;
    }

    public NotificationModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPushkey() {
        return pushkey;
    }

    public void setPushkey(String pushkey) {
        this.pushkey = pushkey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
