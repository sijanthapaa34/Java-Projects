package com.sijan.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Accounts")
public class Account {
	@Id 
	@Column(name = "AccountNumber", unique = true, nullable = false)
	private String accountNumber;
    private AccountType AccountType;
    private double Balance;
    private String Password;
    
    public Account() {}

    public Account(String accountNumber, AccountType accountType, double balance, String password) {
        this.accountNumber = accountNumber;
        this.AccountType = accountType;
        this.Balance = balance;
        this.Password = password;
    }

    public String getaccountNumber() {
        return accountNumber;
    }
    
    public void setaccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
    
    public AccountType getAccountType() {
        return AccountType;
    }

    public double getBalance() {
        return Balance;
    }
    
	public void setAccountType(AccountType accountType) {
		this.AccountType = accountType;
	}

	public void setBalance(double balance) {
		this.Balance = balance;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		this.Password = password;
	}   
} 


