package Practice2;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products = new ArrayList<>();

    public void removeProduct(Product product) {
        for(Product prod: products)
        {
            if(prod.equals(product)){
                products.remove(prod);
            }
            //products.removeIf(prod.equals(product));
        }}



    public void addProduct(Product product) {
        if(product.getStock()>0){
            products.add(product);
    }}

    public double calculateTotal() {
        return products.stream()
                .mapToDouble((prod -> prod.getTotalIndividualprice()))
                .sum();
    }

    public List<Product> getProducts(){
        return products;
    }
}


