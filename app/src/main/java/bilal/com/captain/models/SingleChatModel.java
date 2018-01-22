package bilal.com.captain.models;

import java.util.ArrayList;

/**
 * Created by ikodePC-1 on 1/17/2018.
 */

public class SingleChatModel {
    String msgs;

    String from;

    boolean flag;

    String user;

    String date;

    String key;

    ArrayList<String> selectDownloadUrls;

    public SingleChatModel(String msgs, String from, boolean flag, String user, String date, String key, ArrayList<String> selectDownloadUrls) {
        this.msgs = msgs;
        this.from = from;
        this.flag = flag;
        this.user = user;
        this.date = date;
        this.key = key;
        this.selectDownloadUrls = selectDownloadUrls;
    }

    public SingleChatModel() {
    }

    public ArrayList<String> getSelectDownloadUrls() {
        return selectDownloadUrls;
    }

    public void setSelectDownloadUrls(ArrayList<String> selectDownloadUrls) {
        this.selectDownloadUrls = selectDownloadUrls;
    }

    public String getMsgs() {
        return msgs;
    }

    public void setMsgs(String msgs) {
        this.msgs = msgs;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
}
