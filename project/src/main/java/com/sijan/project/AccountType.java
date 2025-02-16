package com.sijan.project;

public enum AccountType {
	SAVINGS("Savings",5), FIXEDDEPOSIT("Fixed Deposit",8), CURRENT("Current",0), SALARYACCOUNT("Salary",4);
	private final double interestRate;
	private final String accountType;


	private AccountType(String accountType, double interestRate) {
		
		this.accountType = accountType;
		this.interestRate = interestRate;
	}

	public String getAccountType() {
		return accountType;
	}

	public double getInterestRate() {
		return interestRate;
	}

}
