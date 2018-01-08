package bilal.com.captain;

/**
 * Created by shame on 2018-01-08.
 */

public class Firebase {

    String username;
    String id;
    String email;
    String password;

    public Firebase() {
    }

    public Firebase(String id, String email, String password,String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
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
