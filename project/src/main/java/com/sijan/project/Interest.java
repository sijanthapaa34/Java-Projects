package com.sijan.project;

@FunctionalInterface 
public interface Interest {
double calculateInterest(Account account, AccountType accountype);
}
