package com.Cognizant.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	private Long id;
	private String name;
	private String author;
	private String publisher;
	private String edition;
	private int discount;
	private int stock;
	private String orderStatus;
	private Long orderId;
	private LocalDate orderDate;
	private double price;

}