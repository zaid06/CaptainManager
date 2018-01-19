package bilal.com.captain;

/**
 * Created by shame on 2018-01-08.
 */

public class Firebase {

    String username;
    String id;
    String email;
    String password;
    boolean isonline;

    public Firebase() {
    }

    public Firebase(String id, String email, String password,String username, boolean isonline ) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isonline = isonline;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsonline(boolean isonline) {
        this.isonline = isonline;
    }

    public boolean getIsonline() {
        return isonline;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
