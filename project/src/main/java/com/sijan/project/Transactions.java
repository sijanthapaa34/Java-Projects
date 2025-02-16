package com.sijan.project;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Transaction")
public class Transactions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long TransactionId;
	@Column(name = "Date", nullable = false)
    private LocalDateTime transactionDate;
	@ManyToOne
	@JoinColumn(name = "AccountNumber", referencedColumnName = "AccountNumber", nullable =  false)
	private Account account;	
	private double Amount;
	@Enumerated(EnumType.STRING)
	@Column(name = "TrancactionType", nullable = false)
	private TransactionType transactionType;
	private double Balance;
	
	public Transactions() {}
	public Transactions(Account account, double amount, TransactionType transactionType, double balance) {
        this.transactionDate = LocalDateTime.now();
        this.account = account;
        this.Amount = amount;
        this.transactionType = transactionType;
        this.Balance = balance;
    }


	public Long getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(Long transactionId) {
		this.TransactionId = transactionId;
	}
	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public double getAmount() {
		return Amount;
	}
	public void setAmount(double amount) {
		this.Amount = amount;
	}
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public double getBalance() {
		return Balance;
	}
	public void setBalance(double balance) {
		this.Balance = balance;
	}
	@Override
	public String toString() {
	    return String.format(
	        "+---------------------+----------------------------+---------------------+-------------------+-------------------------+--------------------+\n" +
	        "| Transaction ID      | Transaction Date           | Account Number      | Amount            | Transaction Type        | Balance            |\n" +
	        "+---------------------+----------------------------+---------------------+-------------------+-------------------------+--------------------+\n" +
	        "| %-19d | %-26s | %-19s | %-17.2f | %-23s | %-18.2f |\n" +
	        "+---------------------+----------------------------+---------------------+-------------------+-------------------------+--------------------+",
	        TransactionId, transactionDate, account.getaccountNumber(), Amount, transactionType, Balance
	    );
	}

	
}
