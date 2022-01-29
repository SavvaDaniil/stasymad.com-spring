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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "accessofuser")
public class Accessofuser {

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
	protected Integer id;
	

	protected int id_of_payment;
	protected int id_of_user;
	protected int status;
	private int isVideoQr;
	
	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	protected Date date_of_add;

	public Date getDate_of_add() {
		return date_of_add;
	}
	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}

	
	
	protected int date_of_activation;
	protected int date_must_be_used;
	protected int id_of_course;
	protected int is_back;
	protected int days;
	protected int kind;
	protected int back;
	protected int operation;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getId_of_payment() {
		return id_of_payment;
	}
	public void setId_of_payment(int id_of_payment) {
		this.id_of_payment = id_of_payment;
	}
	public int getId_of_user() {
		return id_of_user;
	}
	public void setId_of_user(int id_of_user) {
		this.id_of_user = id_of_user;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getIsVideoQr() {
		return isVideoQr;
	}
	public void setIsVideoQr(int isVideoQr) {
		this.isVideoQr = isVideoQr;
	}
	public int getDate_of_activation() {
		return date_of_activation;
	}
	public void setDate_of_activation(int date_of_activation) {
		this.date_of_activation = date_of_activation;
	}
	public int getDate_must_be_used() {
		return date_must_be_used;
	}
	public void setDate_must_be_used(int date_must_be_used) {
		this.date_must_be_used = date_must_be_used;
	}
	
	
	public int getId_of_course() {
		return id_of_course;
	}
	public void setId_of_course(int id_of_course) {
		this.id_of_course = id_of_course;
	}
	public int getIs_back() {
		return is_back;
	}
	public void setIs_back(int is_back) {
		this.is_back = is_back;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getKind() {
		return kind;
	}
	public void setKind(int kind) {
		this.kind = kind;
	}
	public int getBack() {
		return back;
	}
	public void setBack(int back) {
		this.back = back;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	
	
}
