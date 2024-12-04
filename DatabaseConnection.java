package Practice2;

import java.sql.*;
import java.util.Locale;

public class DatabaseConnection {

    private Connection connect() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "hr";
        String password = "hr";
        return DriverManager.getConnection(url, user, password);
    }

    public void fetchProducts() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "select id, product_name, price, category, stock from products";
            Statement st = connect().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Product Name: " + rs.getString("product_name") + " Price: " + rs.getDouble("price") +
                        " Category: " + rs.getString("category") + " Stock: " + rs.getInt("stock"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


   public Product getproductbyId(int id){
       Product prod = null;
       try {
           Class.forName("oracle.jdbc.driver.OracleDriver");
           String query = "select product_name, price,category, stock from products where id = "+id;
           Statement st = connect().createStatement();
           ResultSet rs = st.executeQuery(query);
           if(rs.next()){
               String product = rs.getString("product_name");
               double price = rs.getInt("price");
               Productcategory category = Productcategory.valueOf(rs.getString("category"));
               int stock = rs.getInt("stock");
               prod = new Product(id, product, price,category, stock);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
       }
       return prod;

    }
    public void addNew(Product product){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "insert into products values(?,?,?,?,?)";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setInt(1, product.getId());
            ps.setString(2 , product.getName());
            ps.setDouble(3, product.getPrice());
            ps.setString(4, String.valueOf(product.getCatogery()));
            ps.setInt(5, product.getStock());
            ps.executeUpdate();
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Error: Duplicate entry for product ID: " + product.getName());
            e.printStackTrace();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean delete(int id){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "delete from products where product_id = ?";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateStock(int productId, int quantityPurchased) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String query = "UPDATE products SET stock = stock - ? WHERE id = ?";
            PreparedStatement ps = connect().prepareStatement(query);
            ps.setInt(1, quantityPurchased); // Quantity to reduce from stock
            ps.setInt(2, productId); // ID of the product
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Stock updated");
            } else {
                System.out.println("Product not found with ID: " + productId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
