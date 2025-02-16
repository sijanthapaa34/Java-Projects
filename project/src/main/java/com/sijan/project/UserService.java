package com.sijan.project;

import java.util.Random;
import java.util.Scanner;

public class UserService {
	Scanner scanner = new Scanner(System.in);
	HibernateUtil data = new HibernateUtil();
	TransactionService transaction = new TransactionService();
	public void login() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        if(accountNumber.matches("^\\w+\\.manager@bank\\.np$") && data.managerLogin(accountNumber,password)){
        	showMenuForManager();
        }else {
        Account account = data.login(accountNumber,password);
        if (account != null ) {
            System.out.println("Welcome, " + account.getaccountNumber());
            showMenuForCustomer(account);
        }}
    }
	public void signup() {
    	System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        String name = firstName + " "+ lastName;
        
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        if (email.matches("^\\w+\\.manager@bank\\.np$")) { 
            System.out.print("Enter Manager Secret Key: ");
            String secretKey = scanner.nextLine();
            if (!secretKey.equals("BANK1234")) {  
                System.out.println("Invalid secret key. Manager registration denied.");
                return;
                //String name, String email, String password, String contact, String address
            }
            if (!data.managerLogin(email, password)) {
                data.addManager(name, email, password, contact, address);
                System.out.println("Manager account created successfully!");
            } else {
                System.out.println("Manager account already exists.");
            }
            return;  
        }
        
        System.out.print("Enter Account Type (Savings/FixedDeposit/Current): ");
        String accountTypeInput = scanner.nextLine();  
        String enumInput = accountTypeInput.trim().toUpperCase().replace(" ", "");
        
        double balance = 0;
        String accountNumber = generateAccountNumber();
	     try {
	         AccountType accountType = AccountType.valueOf(enumInput);
	         data.signup(accountNumber, name, email,contact, address, password, balance, accountType);
	     } catch (IllegalArgumentException e) {
	         System.out.println("Invalid account type entered.");
	     }
    }

	private String generateAccountNumber() {
		Random random = new Random();
        String accountNumber;
        boolean isUnique;

        do {
            long number = (long) (random.nextDouble() * 9_000_000_000L) + 1_000_000_000L;
            accountNumber = String.valueOf(number);           
            isUnique = data.isAccountNumberUnique(accountNumber);
        } while (!isUnique); 

        return accountNumber;
	}
	private void showMenuForCustomer(Account account) {
		while (true) {
	        System.out.println("\nCurrent Balance: Rs." + account.getBalance());
	        System.out.println("1. Deposit");
	        System.out.println("2. Withdraw");
	        System.out.println("3. Transfer");
	        System.out.println("4. View Transactions");
	        System.out.println("5. Logout");
	        System.out.print("Choose an option: ");
	        
	        int choice = scanner.nextInt();
	        scanner.nextLine();
	        switch (choice) {
	            case 1:
	            	System.out.print("Enter amount to deposit: ");
	                double depositamount = scanner.nextDouble();
	                transaction.deposit(account,depositamount);
	                break;
	            case 2:
	            	System.out.print("Enter amount to withdraw: ");
	                double withdrawamount = scanner.nextDouble();
	                transaction.withdraw(account,withdrawamount);
	                break;
	            case 3:
	            	System.out.print("Enter Destination Account Number: ");
	                String accountNumber = scanner.nextLine();
	                Account targetAccount = data.getAccount(accountNumber);
	                if (targetAccount == null) {
	                    return;
	                }
	                System.out.print("Enter amount to trasfer: ");
	                double transferAmount = scanner.nextDouble();
	                transaction.transfer(account, targetAccount,transferAmount);
	                break;
	            case 4:
	            	System.out.println("\nSelect transaction history time range:");
	                System.out.println("1. Last 24 hours");
	                System.out.println("2. Last 7 days");
	                System.out.println("3. Last 30 days");
	                System.out.println("4. All transactions");
	                System.out.print("Enter your choice: ");

	                int time = scanner.nextInt();

	                switch (time) {
	                    case 1:
	                        data.viewTransactions(account, "24H");
	                        break;
	                    case 2:
	                    	data.viewTransactions(account, "7D");
	                        break;
	                    case 3:
	                    	data.viewTransactions(account, "30D");
	                        break;
	                    case 4:
	                    	data.viewTransactions(account, "ALL");
	                        break;
	                    default:
	                        System.out.println("Invalid choice. Showing all transactions by default.");
	                }
	                break;
	            case 5:
	                System.out.println("Logging out...");
	                return;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }
	    }	
	}
	private void showMenuForManager() {
		while (true) {
	        System.out.println("1. Add Salary");
	        System.out.println("2. Add Staff");
	        System.out.println("3. Exit");
        	System.out.print("Choose an option: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();
	        switch (choice) {
	            case 1:
	            	System.out.print("Enter BankAccount: ");
	                String bankAccount = scanner.nextLine();
	                System.out.print("Enter Salary: ");
	                double Salary = scanner.nextDouble();
	                data.AddSalary(bankAccount, Salary);
	                break;
	            case 2:
	            	String accountNumber = generateAccountNumber();
	            	System.out.print("Enter First Name: ");
	                String firstName = scanner.nextLine();
	                
	                System.out.print("Enter Last Name: ");
	                String lastName = scanner.nextLine();
	                String staffName = firstName + " "+ lastName;
	                
	                System.out.print("Enter Contact: ");
	                String contact = scanner.nextLine();
	                
	                System.out.print("Enter Address: ");
	                String address = scanner.nextLine();
	                
	                String email = firstName+lastName + ".staff.@bank.np";
	                String password = firstName+lastName + ".bank123";
	                double balance = 0;
	                AccountType accountType = AccountType.SALARYACCOUNT;
	                data.signup(accountNumber, staffName, email, contact, address ,password ,balance ,accountType);	
	                break;
	            case 3:
                    System.out.println("Exiting... Thank you!");
                    return;
	            default:
	                System.out.println("Invalid choice. Please try again.");
	        }	
		}
	}
}
