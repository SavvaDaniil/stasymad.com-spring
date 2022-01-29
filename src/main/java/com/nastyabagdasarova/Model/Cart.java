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
@Table(name = "cart")
public class Cart {

	
	public Cart() {
		super();
	}

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
	
	private int id_of_user;
	private int id_of_payment;
	private int status;
	private int days;
	
	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;

	public Date getDate_of_add() {
		return date_of_add;
	}
	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}
	
	private int id_of_course;
	private int is_back;
	private int kind;
	private int back;
	private int operation;
	private int price;
	
	
	@Transient
	protected int number;

	@Transient
	protected String name;

	@Transient
	protected String category;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getId_of_user() {
		return id_of_user;
	}
	public void setId_of_user(int id_of_user) {
		this.id_of_user = id_of_user;
	}
	public int getId_of_payment() {
		return id_of_payment;
	}
	public void setId_of_payment(int id_of_payment) {
		this.id_of_payment = id_of_payment;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public Cart(int id_of_user, int id_of_payment, int status, int days, Date date_of_add, int id_of_course,
			int is_back, int kind, int back, int operation, int price) {
		super();
		this.id_of_user = id_of_user;
		this.id_of_payment = id_of_payment;
		this.status = status;
		this.days = days;
		this.date_of_add = date_of_add;
		this.id_of_course = id_of_course;
		this.is_back = is_back;
		this.kind = kind;
		this.back = back;
		this.operation = operation;
		this.price = price;
	}
	
}
