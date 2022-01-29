package com.nastyabagdasarova.Model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "chat")
public class Chat {

	@Id
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

	private int id_of_back;
	private int from_who;
	private int to_who;
	private String message;
	private int deleted;

	@Column(name = "date_of_readed")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_readed;
	
	private int user0_admin1;

	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	
	private int is_file;
	

	@Transient
	protected String date_of_readed_to_print;

	@Transient
	protected String date_of_add_to_print;
	

	public Integer getId() {
		return id;
	}
	
	public int getId_of_back() {
		return id_of_back;
	}

	public void setId_of_back(int id_of_back) {
		this.id_of_back = id_of_back;
	}

	public int getFrom_who() {
		return from_who;
	}

	public void setFrom_who(int from_who) {
		this.from_who = from_who;
	}

	public int getTo_who() {
		return to_who;
	}

	public void setTo_who(int to_who) {
		this.to_who = to_who;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public Date getDate_of_readed() {
		return date_of_readed;
	}

	public void setDate_of_readed(Date date_of_readed) {
		this.date_of_readed = date_of_readed;
	}

	public int getUser0_admin1() {
		return user0_admin1;
	}

	public void setUser0_admin1(int user0_admin1) {
		this.user0_admin1 = user0_admin1;
	}

	public Date getDate_of_add() {
		return date_of_add;
	}

	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}

	public int getIs_file() {
		return is_file;
	}

	public void setIs_file(int is_file) {
		this.is_file = is_file;
	}

	public String getDate_of_readed_to_print() {
		return date_of_readed_to_print;
	}

	public void setDate_of_readed_to_print(String date_of_readed_to_print) {
		this.date_of_readed_to_print = date_of_readed_to_print;
	}

	public String getDate_of_add_to_print() {
		return date_of_add_to_print;
	}

	public void setDate_of_add_to_print(String date_of_add_to_print) {
		this.date_of_add_to_print = date_of_add_to_print;
	}
	
	
}
