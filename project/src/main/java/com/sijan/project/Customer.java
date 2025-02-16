package com.sijan.project;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer extends User{
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AccountNumber", referencedColumnName = "AccountNumber", nullable = false, unique = true)
	private Account account;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "AccountType", nullable = false)
	private AccountType accountType;
	public Customer( String name, String email, String contact, String location, Account account, AccountType accountType) {
		super(name, email,contact,location);
		this.account = account;
		this.accountType = accountType;
	}
	public Account getAccountNumber() {
		return account;
	}
	public void setAccountNumber(Account account) {
		this.account = account;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}	
}
