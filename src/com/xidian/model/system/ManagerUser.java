package com.xidian.model.system;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**用户管理实体类
 * @author lfq
 *
 */
public class ManagerUser {

	private  IntegerProperty id;
	private final StringProperty username;
	private final StringProperty reallyname;
	private final StringProperty password;
	private final StringProperty typeuser;

	public ManagerUser() {
		this(null, null, null, null);
	}
	public ManagerUser(String username, String reallyname, String password, String typeuser) {

		this.username = new SimpleStringProperty(username);
		this.reallyname = new SimpleStringProperty(reallyname);
		this.password = new SimpleStringProperty(password);
		this.typeuser = new SimpleStringProperty(typeuser);
	}
	public int getId() {
		return id.get();
	}
	public void setId(int id) {
		this.id = new SimpleIntegerProperty(id);
	}
	public IntegerProperty getIdProperty(){
		return id;
	}

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }
    public String getReallyname() {
    	return reallyname.get();
    }

    public void setReallyname(String reallyname) {
    	this.reallyname.set(reallyname);
    }

    public StringProperty reallynameProperty() {
    	return reallyname;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getTypeuser() {
    	return typeuser.get();
    }

    public void setTypeuser(String typeuser) {
    	this.typeuser.set(typeuser);
    }

    public StringProperty typeuserProperty() {
    	return typeuser;
    }


}
