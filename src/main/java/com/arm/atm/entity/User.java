package com.arm.atm.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.arm.atm.resource.security.Profile;
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
public class User implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 2514706234876532222L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String email;
	
	private String name;
	
	@JsonIgnore
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", 
			targetEntity = Account.class)
	private List<Account> accounts;
	
	@ManyToOne
	private Profile profile;
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	@Override
	public String toString() {
	    return "User [id=" + id + ", name=" + name + "]";
	}
}
