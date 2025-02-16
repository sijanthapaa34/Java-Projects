package com.sijan.project;

public class TransactionService {
    private HibernateUtil hibernateUtil;
    private double updatedAmount;

    public TransactionService() {
        this.hibernateUtil = new HibernateUtil();
    }
    public void deposit(Account account, double amount) {
        if (amount <= 0) {throw new IllegalArgumentException("Deposit amount must be positive.");}
        updatedAmount = account.getBalance() + amount;
        account.setBalance(updatedAmount);
        Transactions transaction = new Transactions(account, amount, TransactionType.DEPOSIT, account.getBalance());
        saveTransaction(transaction);
        updateAmount(account.getaccountNumber(), updatedAmount);
        System.out.println("Successfully deposited $" + amount + " into account " + account.getaccountNumber() + 
        		". New balance: $" + updatedAmount);
    }

    public void withdraw(Account account, double amount) {
        if (amount <= 0) {
        	System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (account.getBalance() < amount) {
        	System.out.println("Insufficient balance. Your current balance is Rs." + account.getBalance());
        	return;
        }
        updatedAmount = account.getBalance() - amount;
        account.setBalance(updatedAmount);        
        Transactions transaction = new Transactions(account, amount, TransactionType.WITHDRAW, account.getBalance());
        saveTransaction(transaction);
        updateAmount(account.getaccountNumber(), updatedAmount);        
        System.out.println("✅ Successfully withdrew $" + amount + " from account " + account.getaccountNumber() + 
                           ". New balance: $" + updatedAmount);
    }
    public void transfer(Account sourceAccount, Account targetAccount, double amount) {
        withdraw(sourceAccount, amount);
        deposit(targetAccount, amount);        
    }
    private void saveTransaction(Transactions transaction) {
        hibernateUtil.saveTransaction(transaction);
    }
    private void updateAmount(String accountNumber, double amount) {
        hibernateUtil.updateAmount(accountNumber, amount);
    }
}
