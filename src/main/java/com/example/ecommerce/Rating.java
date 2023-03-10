package com.example.ecommerce;

public class Rating {
    public static int submitRating(Double rating, Customer customer){
        try{
            String query="INSERT INTO ratings(rating,customer_id) VALUES("+rating+","+customer.id+")";
            return DataBase.setQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}