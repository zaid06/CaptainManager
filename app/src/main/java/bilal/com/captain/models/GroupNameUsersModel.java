package bilal.com.captain.models;

import java.util.ArrayList;

import bilal.com.captain.classes.GroupUsersModel;

/**
 * Created by ikodePC-1 on 1/19/2018.
 */

public class GroupNameUsersModel {

    String primarypushkey;

    String groupname;

    String adminkey;

    ArrayList<GroupUsersModel> users;

    public GroupNameUsersModel(String primarypushkey, String groupname, String adminkey, ArrayList<GroupUsersModel> users) {
        this.primarypushkey = primarypushkey;
        this.groupname = groupname;
        this.adminkey = adminkey;
        this.users = users;
    }

    public GroupNameUsersModel() {
    }

    public String getPrimarypushkey() {
        return primarypushkey;
    }

    public void setPrimarypushkey(String primarypushkey) {
        this.primarypushkey = primarypushkey;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getAdminkey() {
        return adminkey;
    }

    public void setAdminkey(String adminkey) {
        this.adminkey = adminkey;
    }

    public ArrayList<GroupUsersModel> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<GroupUsersModel> users) {
        this.users = users;
    }
}

