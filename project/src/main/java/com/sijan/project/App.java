package com.sijan.project;

import java.util.Scanner;

public class App 
{
	private static Scanner scanner = new Scanner(System.in);
    public static void main( String[] args )
    {	
    UserService userservice = new UserService();
    while(true){
            System.out.println("1. Login");
            System.out.println("2. Sign Up ");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); 
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter valid number.");
                continue;
            }

            switch (choice) {
            	case 1:
            		userservice.login();
                    break;
                case 2:
                    userservice.signup();
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }    	
}   	
        
