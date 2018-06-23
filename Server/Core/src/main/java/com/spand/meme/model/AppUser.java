package com.spand.meme.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Data
@Entity
public class AppUser extends IdEntity implements UserDetails {

	private String 	name;
	private String 	login;
	private String 	address;
	private String 	password;
	private Boolean isActive;

	public AppUser() {}

	public AppUser(String name, String login, String address, String password, Boolean isActive){
		this.name = name;
		this.login = login;
		this.address = address;
		this.password = password;
		this.isActive = isActive;
	}

	public AppUser(Long id, String name, String login, String address, String password, Boolean isActive){
		setId(id);
		this.name = name;
		this.login = login;
		this.address = address;
		this.password = password;
		this.isActive = isActive;
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
				Objects.equals(password, appUser.password) &&
				Objects.equals(isActive, appUser.isActive);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), name, login, address, password, isActive);
	}

	@Override
	public List<GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
