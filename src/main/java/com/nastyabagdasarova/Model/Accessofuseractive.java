package com.nastyabagdasarova.Model;

import java.util.Date;

import javax.persistence.Transient;

public class Accessofuseractive extends Accessofuser {

	
	private Accessofuser accessofuser;
	private String name_of_course;
	private String description_of_course;
	
	@Transient
	private Accessofuseractive backActiveAccess;

	@Transient
	private String date_of_add_to_print;

	@Transient
	private String date_of_activation_to_print;

	@Transient
	private String date_must_be_used_to_print;

	@Transient
	private boolean prolong;

	@Transient
	private int last_played_number;

	@Transient
	protected String posterSrc;
	
	
	
	public Accessofuseractive(Integer id, int id_of_payment, int id_of_user, int status, Date date_of_add, int date_of_activation,
			int date_must_be_used, int id_of_course, int is_back, int days, int kind, int back,
			int operation) {
		super();
		this.id = id;
		this.id_of_payment = id_of_payment;
		this.id_of_user = id_of_user;
		this.status = status;
		this.date_of_add = date_of_add;
		this.date_of_activation = date_of_activation;
		this.date_must_be_used = date_must_be_used;
		this.id_of_course = id_of_course;
		this.is_back = is_back;
		this.days = days;
		this.kind = kind;
		this.back = back;
		this.operation = operation;
	}
	
	public Accessofuseractive(Accessofuser accessofuser, String name_of_course, String description_of_course) {
		this.accessofuser = accessofuser;
        this.name_of_course = name_of_course;
        this.description_of_course = description_of_course;
    }
	public Accessofuseractive() {

    }
	
	public Accessofuser getAccessofuser() {
		return accessofuser;
	}
	public void setAccessofuser(Accessofuser accessofuser) {
		this.accessofuser = accessofuser;
	}

	
	public String getName_of_course() {
		return name_of_course;
	}

	public void setName_of_course(String name_of_course) {
		this.name_of_course = name_of_course;
	}

	public String getDescription_of_course() {
		return description_of_course;
	}

	public void setDescription_of_course(String description_of_course) {
		this.description_of_course = description_of_course;
	}

	public Accessofuseractive getBackActiveAccess() {
		return backActiveAccess;
	}
	public void setBackActiveAccess(Accessofuseractive backActiveAccess) {
		this.backActiveAccess = backActiveAccess;
	}
	public String getDate_of_add_to_print() {
		return date_of_add_to_print;
	}
	public void setDate_of_add_to_print(String date_of_add_to_print) {
		this.date_of_add_to_print = date_of_add_to_print;
	}
	public String getDate_of_activation_to_print() {
		return date_of_activation_to_print;
	}
	public void setDate_of_activation_to_print(String date_of_activation_to_print) {
		this.date_of_activation_to_print = date_of_activation_to_print;
	}
	public String getDate_must_be_used_to_print() {
		return date_must_be_used_to_print;
	}
	public void setDate_must_be_used_to_print(String date_must_be_used_to_print) {
		this.date_must_be_used_to_print = date_must_be_used_to_print;
	}
	public boolean isProlong() {
		return prolong;
	}
	public void setProlong(boolean prolong) {
		this.prolong = prolong;
	}

	public int getLast_played_number() {
		return last_played_number;
	}

	public void setLast_played_number(int last_played_number) {
		this.last_played_number = last_played_number;
	}
	public String getPosterSrc() {
		return posterSrc;
	}
	public void setPosterSrc(String posterSrc) {
		this.posterSrc = posterSrc;
	}
	
	
}
