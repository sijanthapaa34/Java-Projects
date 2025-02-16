package com.sijan.project;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Manager")
public class Manager extends User {
	private String Password;

	public Manager(String name, String email, String password, String contact, String address) {
		super( name, email,contact, address);
		this.Password = password;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}
	

}
