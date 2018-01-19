package bilal.com.captain.classes;

/**
 * Created by ikodePC-1 on 1/19/2018.
 */

public class GroupUsersModel {

    private String name;

    private String key;

    private boolean flag;

    public GroupUsersModel(String name, String key, boolean flag) {
        this.name = name;
        this.key = key;
        this.flag = flag;
    }

    public GroupUsersModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
