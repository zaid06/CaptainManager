package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/9/2018.
 */

public class ComplainModel {

    String key;

    String imageUrl;

    String recordingUrl;

    String date;

    public ComplainModel(String key, String imageUrl, String recordingUrl,String date) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.recordingUrl = recordingUrl;
        this.date = date;
    }

    public ComplainModel() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRecordingUrl() {
        return recordingUrl;
    }

    public void setRecordingUrl(String recordingUrl) {
        this.recordingUrl = recordingUrl;
    }
}
