package com.example.ecommerce;

public class Order {
    private static boolean deleteCartProduct(Customer customer, Product product){
        try{
            String query="DELETE FROM cart WHERE product_id = "+product.getId()+" and customer_id = "+customer.id;
            return DataBase.setQuery(query) != 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public static boolean getOrder(Customer customer, Product product){
        try {
            deleteCartProduct(customer,product);
            String query="INSERT into orders(customer_id,product_id) VALUES\n" +
                    "\t("+customer.id+","+product.getId()+")";

            String q1="UPDATE products set quantity = quantity-1 WHERE pid = "+product.getId();

            return DataBase.setQuery(query) != 0 && DataBase.setQuery(q1) != 0;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
