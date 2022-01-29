package com.nastyabagdasarova.Model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.nastyabagdasarova.Component.ParseTimeStampComponent;

@Entity
@Table(name = "contact")
public class Contact {

	
	
	public Contact(String mail, String name, String message) {
		super();
		this.mail = mail;
		this.name = name;
		this.message = message;

		String timestamp = LocalDateTime.now(ZoneId.of("UTC"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		ParseTimeStampComponent ptsc = new ParseTimeStampComponent();
		this.date_of_add = ptsc.parseTimestamp(timestamp);
		
		timestamp = LocalDateTime.now(ZoneId.of("UTC"))
		.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.day_of_add = ptsc.parseDatestamp(timestamp);
		
		timestamp = null;
		ptsc = null;
		System.gc();
	}

	@SuppressWarnings("unused")
	private Contact() {}
	
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

	private String mail;
	private String name;
	private String message;

	
	@Column(name = "day_of_add")
	@Temporal(TemporalType.DATE)
	private Date day_of_add;
	
	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	
	private String ip;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDay_of_add() {
		return day_of_add;
	}

	public void setDay_of_add(Date day_of_add) {
		this.day_of_add = day_of_add;
	}

	public Date getDate_of_add() {
		return date_of_add;
	}

	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
