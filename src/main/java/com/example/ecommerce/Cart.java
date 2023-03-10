package com.example.ecommerce;

import java.sql.ResultSet;

public class Cart {
    private static boolean check(Product product, Customer customer){
        try{
            String check="SELECT * from cart where product_id="+product.getId()+" and customer_id="+customer.id+" and statuss='not ordered'";
            ResultSet rs=DataBase.getQuery(check);
            return rs != null && rs.next();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean addToCart(Product product, Customer customer){
        if(check(product,customer)) return false;
        String query="INSERT INTO cart (customer_id,product_id) VALUES("+customer.id+","+product.getId()+")";
        try{
            return DataBase.setQuery(query) != 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
//    public static
}
