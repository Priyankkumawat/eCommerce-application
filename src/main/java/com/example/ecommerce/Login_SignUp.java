package com.example.ecommerce;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login_SignUp {
    private static byte[] getSha(String pass){
        try{
            MessageDigest md=MessageDigest.getInstance("SHA-256");
//            return md.digest(pass.getBytes(StandardCharsets.UTF_8));
            md.update(pass.getBytes());
            return md.digest();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static String getEncrypt(String pass){
        try{
            BigInteger b=new BigInteger(1,getSha(pass));
            StringBuilder st=new StringBuilder(b.toString(16));
            return st.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static Customer customer=null;
    public static Customer validate(String user,String pass){
        String encrypts=getEncrypt(pass);
        String query="SELECT * FROM `customer` WHERE email = '"+user+"' and password = '"+encrypts+"'";
        String query1="SELECT * FROM `customer` WHERE mobile = '"+user+"' and password = '"+encrypts+"'";

        try {
            ResultSet rs= DataBase.getQuery(query) ;
            ResultSet rs1=DataBase.getQuery(query1);

            if(rs != null && rs.next()){
                customer=new Customer(
                        rs.getInt("cid"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
            else if(rs1 != null && rs1.next()){
                customer=new Customer(
                        rs1.getInt("cid"),
                        rs1.getString("name"),
                        rs1.getString("email")
                );
            }
            return customer;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public  static int addCustomer(String name, String mobile, String email,String address, String pass){
        if(name=="" || mobile=="" || email=="" || address=="" || pass=="") return 0;
        String encrypts=getEncrypt(pass);
        String query="INSERT into customer(name,mobile,email,address,password) " +
                "VALUES('"+name+"','"+mobile+"','"+email+"','"+address+"','"+encrypts+"')";
        try{
            return DataBase.setQuery(query);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        if(validate("12345","123") != null) System.out.println("true");
        else {
            System.out.println("false");}
        System.out.println(getEncrypt("123"));
    }
}
