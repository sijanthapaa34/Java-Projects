package Practice2;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Order {
    private Cart cart;

    public Order(Cart cart) {
        this.cart = cart;
    }


    public void generateBill(double total ,Discount discount, double finalPrice) {
        System.out.println("Product Id, "+ "Product Name, "+ "Price, "+ "Categories, "+ "Quantity, "+ "Unit price");
        String bill = "Product Id, "+ "Product Name, "+ "Price, "+ "Categories, "+ "Quantity, "+ "Unit price";
        for(Product prod: cart.getProducts()){
            String line = "\n"+prod.getId()+" " + prod.getName()+" "+ prod.getPrice()+" "+ prod.getCatogery()+" "+prod.getQuantity()+" "+ prod.getTotalIndividualprice();
            System.out.println(line);
            bill +=line+ "\n";
        }
        double discountAmount = discount.applydiscount(total);
        System.out.println("Total: "+total);
        System.out.println("Discout: "+ discountAmount);
        System.out.println("Total after 10% discount: "+finalPrice);
         bill += "\nTotal: "+total;
        bill +="\nDiscout: "+ discountAmount;
        bill+= "\nTotal after 10% discount: "+finalPrice;
        log(bill);
    }
    public void log(String message) {
        try (FileWriter fw = new FileWriter("Bill.txt", true)) {
            fw.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
