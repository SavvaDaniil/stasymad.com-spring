package com.nastyabagdasarova.Model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
public class User {

  @Id
  //@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(
		    strategy= GenerationType.AUTO,
		    generator="native"
	)
	@GenericGenerator(
	    name = "native",
	    strategy = "native"
	)
  @Column(name="id")
  private Integer id;

  @Column(name="username")
  private String username;
  
  @Column(name="password")
  private String password;
  
  @Column(name="authKey")
  private String authKey;
  
  @Column(name="accessToken")
  private String accessToken;
  
  @Column(name="fio")
  private String fio;
  
  @Column(name="active")
  private boolean active;
  
  @Column(name="date_of_add")
  private String date_of_add;
  
  @Column(name="forgetCode")
  private String forgetCode;
  
  @Column(name="forgetTry")
  private int forgetTry;
  
  @Column(name="forgetHash")
  private String forgetHash;
  
  @Column(name="forgetCount")
  private int forgetCount;
  
  @Column(name="forgetLast")
  private Integer forgetLast;
  
  @Column(name="dynamic_video_index")
  private Integer dynamic_video_index;
  
  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "id_of_user"))
  @Enumerated(EnumType.STRING)
  private Set<Role> roles;



  public Set<Role> getRoles() {
	return roles;
}

public void setRoles(Set<Role> roles) {
	this.roles = roles;
}

public Integer getId() {
    return id;
  }
public void setId(Integer id) {
	this.id = id;
}

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getAuthKey() {
	return authKey;
}
public void setAuthKey(String authKey) {
	this.authKey = authKey;
}
public String getAccessToken() {
	return accessToken;
}
public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}
public String getFio() {
	return fio;
}
public void setFio(String fio) {
	this.fio = fio;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean active) {
	this.active = active;
}
public String getDate_of_add() {
	return date_of_add;
}
public void setDate_of_add(String date_of_add) {
	this.date_of_add = date_of_add;
}
public String getForgetCode() {
	return forgetCode;
}
public void setForgetCode(String forgetCode) {
	this.forgetCode = forgetCode;
}
public int getForgetTry() {
	return forgetTry;
}
public void setForgetTry(int forgetTry) {
	this.forgetTry = forgetTry;
}
public String getForgetHash() {
	return forgetHash;
}
public void setForgetHash(String forgetHash) {
	this.forgetHash = forgetHash;
}
public int getForgetCount() {
	return forgetCount;
}
public void setForgetCount(int forgetCount) {
	this.forgetCount = forgetCount;
}
public int getForgetLast() {
	return forgetLast;
}
public void setForgetLast(Integer forgetLast) {
	this.forgetLast = forgetLast;
}
public Integer getDynamic_video_index() {
	return dynamic_video_index;
}
public void setDynamic_video_index(Integer dynamic_video_index) {
	this.dynamic_video_index = dynamic_video_index;
}

@Override
public String toString() {
	return "User [id=" + id + ", username=" + username + ", password=" + password + ", authKey=" + authKey
			+ ", accessToken=" + accessToken + ", fio=" + fio + ", active=" + active + ", date_of_add=" + date_of_add
			+ ", forgetCode=" + forgetCode + ", forgetTry=" + forgetTry + ", forgetHash=" + forgetHash
			+ ", forgetCount=" + forgetCount + ", forgetLast=" + forgetLast + ", roles=" + roles + "]";
}

	
}
