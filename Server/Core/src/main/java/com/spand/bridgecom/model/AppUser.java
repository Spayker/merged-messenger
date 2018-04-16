package com.spand.bridgecom.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AppUser extends IdEntity{

	private String name;
	private String login;
	private String address;
	private String password;

	public AppUser() {}

	public AppUser(String name, String login, String address, String password){
		this.name = name;
		this.login = login;
		this.address = address;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof AppUser)) return false;
		if (!super.equals(object)) return false;
		AppUser appUser = (AppUser) object;
		return Objects.equals(name, appUser.name) &&
				Objects.equals(login, appUser.login) &&
				Objects.equals(address, appUser.address) &&
				Objects.equals(password, appUser.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, login, address, password);
	}
}
