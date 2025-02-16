package com.sijan.project;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InterestCalculator {

    public static void main(String[] args) {
    	HibernateUtil data = new HibernateUtil();
    	
        List<Account> accounts = data.getAllAccounts();
        Interest interestCalculator = 
        		(account, accountType) -> account.getBalance() * (accountType.getInterestRate() / 100) / 12;

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (Account account : accounts) {
            executor.submit(() -> {
                double interest = interestCalculator.calculateInterest(account, account.getAccountType());
                double newBalance = account.getBalance() + interest;
                data.updateAmount(account.getaccountNumber(), newBalance);
            });
        }

        executor.shutdown();
    }
}
