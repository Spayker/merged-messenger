package com.spand.bridgecom.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class User extends IdEntity{

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "LOGIN", nullable = false)
	private String login;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	private User(String name, String login, String address, String email){
		this.name = name;
		this.login = login;
		this.address = address;
		this.email = email;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof User)) return false;
		if (!super.equals(object)) return false;
		User user = (User) object;
		return Objects.equals(name, user.name) &&
				Objects.equals(login, user.login) &&
				Objects.equals(address, user.address) &&
				Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, login, address, email);
	}
}
