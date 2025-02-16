package com.sijan.project;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.mindrot.jbcrypt.BCrypt;
import org.hibernate.Transaction;

public class HibernateUtil {
    private SessionFactory sessionFactory;

    public HibernateUtil() {
        try {
            Configuration configuration = new Configuration().configure()
                    .addAnnotatedClass(Account.class)
                    .addAnnotatedClass(Customer.class)
                    .addAnnotatedClass(Manager.class)
                    .addAnnotatedClass(Staff.class)
                    .addAnnotatedClass(Transactions.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error building the SessionFactory");
        }
    }
    public void addManager(String name, String email, String password, String contact, String address) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Manager manager = new Manager(name, email, password, contact, address);
            session.save(manager);           
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Account login(String accountNumber, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Account> query = session.createQuery("FROM Account a WHERE a.accountNumber = :accountNumber", Account.class);
            query.setParameter("accountNumber", accountNumber); 
            Account account = query.uniqueResult();
            if (account != null && BCrypt.checkpw(password, account.getPassword())) {
                System.out.println("Login successful!");
                return account;  
            } else {
                System.out.println("Invalid account number or password.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account signup(String accountNumber, String name, String email,String contact, String address, String password, double balance, AccountType accountType) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            Account account = new Account(accountNumber, accountType, balance, hashedPassword);
            if (email.matches(".*\\.staff\\.@bank\\.np")) {
                Staff staff = new Staff(name, email, contact, address, account, accountType);
                session.save(staff);
            } else {
                Customer customer = new Customer(name, email,contact, address, account, accountType);
                session.save(customer);
            }
            System.out.println("Account created successfully!");
            System.out.println("Your Account Number is: " + accountNumber);         
            session.save(account);
            transaction.commit();
            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean isAccountNumberUnique(String accountNumber) {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM Account a WHERE a.accountNumber = :accountNo",
                    Long.class);
            query.setParameter("accountNo", accountNumber); 

            Long count = query.uniqueResult();
            return count != null && count == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public void saveTransaction(Transactions transaction) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(transaction);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateAmount(String accountNumber, double amount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            
            Account account = session.get(Account.class, accountNumber);
            if (account != null) {
                account.setBalance(amount);
                session.update(account); 
                transaction.commit();
            } else {
            	System.out.println("Account number not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Account getAccount(String accountNumber) {
    	try (Session session = sessionFactory.openSession()) {
            Query<Account> query = session.createQuery("FROM Account a WHERE a.accountNumber = :accountNumber", Account.class);
            query.setParameter("accountNumber", accountNumber); 
            Account account = query.uniqueResult();
            if (account != null ) {
                return account;  
            } else {
                System.out.println("Account number not found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void viewTransactions(Account account, String timeRange) {
    	try (Session session = sessionFactory.openSession()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime startTime = null;
            String accountNumber = account.getaccountNumber();

            if (!"ALL".equals(timeRange)) {
                switch (timeRange) {
                    case "24H":
                        startTime = now.minusHours(24);
                        break;
                    case "7D":
                        startTime = now.minusDays(7);
                        break;
                    case "30D":
                        startTime = now.minusDays(30);
                        break;
                }
            }

            String hql = "FROM Transactions t WHERE t.account.accountNumber = :accountNumber";
            if (startTime != null) {
                hql += " AND t.transactionDate >= :startTime";
            }
            
            Query<Transactions> query = session.createQuery(hql, Transactions.class);
            query.setParameter("accountNumber", accountNumber);
            if (startTime != null) {
                query.setParameter("startTime", startTime);
            }

            List<Transactions> transactions = query.getResultList();
            
            if (transactions.isEmpty()) {
                System.out.println("No transactions found");
            } else {
                for (Transactions transaction : transactions) {
                    System.out.println(transaction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    }

	public List<Account> getAllAccounts() {
		try (Session session = sessionFactory.openSession()) {
	        Query<Account> query = session.createQuery("FROM Account", Account.class);
	        List<Account> accounts = query.getResultList();
	        if (accounts != null && !accounts.isEmpty()) {
	            return accounts;  
            } 
	        else {
	            System.out.println("No accounts found.");
	            return null;  
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public void AddSalary(String accountNumber, double salary) {
		 try (Session session = sessionFactory.openSession()) {
	            Transaction transaction = session.beginTransaction();
	            
	            Account account = session.get(Account.class, accountNumber);
	            if (account != null) {
	            	double totalBalance = account.getBalance() + salary;
	                account.setBalance(totalBalance);
	                session.update(account); 
	                transaction.commit();
	                System.out.println("Salary provided successfully for account: " + accountNumber);
	            } else {
	                System.out.println("No account found with account number: " + accountNumber);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	public boolean managerLogin(String email, String password) {
		try (Session session = sessionFactory.openSession()) {
            Query<String> query = session.createQuery("SELECT m.Password FROM Manager m WHERE m.Email = :email", String.class);
            query.setParameter("email", email); 
            String storedPassword = query.uniqueResult();
            if (storedPassword != null && password.equals(storedPassword)) {
                System.out.println("Login successful!");
                return true;  
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
}
