package com.arm.atm.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Account implements Serializable {

	@JsonIgnore
	private static final long serialVersionUID = -6641269119519704571L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull @NotEmpty
	@Column(unique = true)
	private Long number;
	
	@ManyToOne
	@NotNull @NotEmpty
	private User owner;
	
	@JsonIgnore
	@NotNull @NotEmpty
	private String password;
	
	@NotEmpty
	private BigDecimal balance;
	
	@ManyToOne
	@NotNull @NotEmpty
	private Bank bank;
	 	
}
