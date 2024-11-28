package model.bean;

public class UserBean {
	private String fullname;
    private String email;
    private String password;

    public UserBean() {}

    public UserBean(String fullname,String email, String password) {
    	this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    // Getter và Setter
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


