package Practice2;

import practice1.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static DatabaseConnection database = new DatabaseConnection();
    private static Cart cart = new Cart();
    static DiscountManager dm = new DiscountManager();
    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nWelcome to the Online Shopping System!");
            System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Checkout");
            System.out.println("6. Exit");
            System.out.println("7. Admin");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    addProducts();
                    break;
                case 3:
                   removeProducts();
                    break;
                case 4:
                    displayCartItems();
                    break;
                case 5:
                    checkout();
                    break;
                case 6:
                    System.out.println("Exiting... Goodbye!");
                    exit = true;
                    break;
                case 7:
                    admin();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Method to add a student
    private static void addProducts() {
        System.out.print("Enter ID of Product: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter quantity of Product: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        Product producttoadd = database.getproductbyId(id);
        if(producttoadd!=null){
            cart.addProduct(producttoadd);
            database.updateStock(id,quantity);
            producttoadd.setStock(producttoadd.getStock()-quantity);
            producttoadd.setQuantity(quantity);
            System.out.println(producttoadd.getName() + " added in cart");
        }
        else{
            System.out.println("Product not found");
        }


    }
    private static void viewAllProducts() {
        System.out.println("\nProducts ");
        database.fetchProducts();
    }



    private static void displayCartItems() {
        System.out.println("Total Products in Cart");
        System.out.println("\n Product Id, "+ "Product Name, "+ "Price, "+ "Categories, "+" Quantity, "+ "Unit price ");
        for(Product prod : cart.getProducts())
        {
            System.out.println(prod.getId() +", "+ prod.getName()+" "+ prod.getPrice()+" "+ prod.getCatogery()+" "+prod.getQuantity()+ " "+prod.getTotalIndividualprice());

        }
    }
    private static void removeProducts() {
        System.out.print("Enter Product ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Product producttoDelete = database.getproductbyId(id);
        if(producttoDelete!=null){
            cart.removeProduct(producttoDelete);
            System.out.println(producttoDelete.getName() + " removed from cart");
        }
        else{
            System.out.println("Product not found");
        }
    }
    private static void checkout(){
        double total = cart.calculateTotal();
        Discount discount = amount -> amount*0.1;
        double finalPrice = dm.applyDiscount(total, discount.applydiscount(total));
        Order order = new Order(cart);
        order.generateBill(total ,discount, finalPrice);
        //order.log();

    }
    private static void admin(){
        System.out.println("Enter password: ");
        int pw = scanner.nextInt();
        scanner.nextLine();
        if(pw == 1738){
            boolean exit = false;
            while (!exit) {
                System.out.println("1. Add Products");
                System.out.println("2. Remove Product");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        add();
                        exit = true;
                        break;
                    case 2:
                        remove();
                        exit =true;
                        break;

        }}}
        else{
            System.out.println("Invalid Password");
        }
    }
    private static void add(){
        Productcategory selectcategory = null;
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter id: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Categories:");
        for (Productcategory category : Productcategory.values()) {
            System.out.println(category);
        }

        System.out.print("Enter a product category: ");
        String input = scanner.next().toUpperCase();  // Get input and convert to uppercase
        try {
            selectcategory = Productcategory.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category. Please choose from the available options.");
        }

        System.out.print("Enter stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        Product product = new Product(id, name, price, selectcategory,stock);
        database.addNew(product);
        System.out.println("Product added successfully.");
    }

    private static void remove(){
        System.out.print("Enter Product ID to delete: ");
        int Id = scanner.nextInt();
        scanner.nextLine();
        boolean isDeleted = database.delete(Id);
        if (isDeleted) {
            Logutil.log("Deleted Product with ID: " + Id);
            System.out.println("Student deleted successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }
}

