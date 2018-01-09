package bilal.com.captain.models;

/**
 * Created by ikodePC-1 on 1/9/2018.
 */

public class ComplainModel {

    String key;

    String imageUrl;

    String recordingUrl;

    public ComplainModel(String key, String imageUrl, String recordingUrl) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.recordingUrl = recordingUrl;
    }

    public ComplainModel() {
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
