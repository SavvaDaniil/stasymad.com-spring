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
@Table(name = "payment")
public class Paymentapp {


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
	
	private int id_of_user;
	private int summa;
	private int status;
	
	private String paypal_payment_id;

	@Column(name = "date_of_add")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_add;
	
	@Column(name = "date_of_done")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date_of_done;


	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}

	public int getId_of_user() {
		return id_of_user;
	}

	public void setId_of_user(int id_of_user) {
		this.id_of_user = id_of_user;
	}

	public int getSumma() {
		return summa;
	}

	public void setSumma(int summa) {
		this.summa = summa;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	

	public String getPaypal_payment_id() {
		return paypal_payment_id;
	}

	public void setPaypal_payment_id(String paypal_payment_id) {
		this.paypal_payment_id = paypal_payment_id;
	}

	public Date getDate_of_add() {
		return date_of_add;
	}

	public void setDate_of_add(Date date_of_add) {
		this.date_of_add = date_of_add;
	}

	public Date getDate_of_done() {
		return date_of_done;
	}

	public void setDate_of_done(Date date_of_done) {
		this.date_of_done = date_of_done;
	}
	@Override
	public String toString() {
		return "Paymentapp [id=" + id + ", id_of_user=" + id_of_user + ", summa=" + summa + ", status=" + status
				+ ", paypal_payment_id=" + paypal_payment_id + ", date_of_add=" + date_of_add + ", date_of_done="
				+ date_of_done + "]";
	}

	
	
}
