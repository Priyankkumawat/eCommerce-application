package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductList {
    public TableView<Product> productTable;
    private String str="";
    private boolean OD=false;
    public void search(String s){
        str=s;
    }

    //Cart List
    private ObservableList<Product> getCartProductList(Customer customer){
        ObservableList<Product> pd=FXCollections.observableArrayList();
        String query = "SELECT * FROM cart C join products P on C.customer_id="+customer.id+" WHERE C.product_id=P.pid";
        ResultSet rs = DataBase.getQuery(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    pd.add(new Product(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }
    public Pane productCartGrid(Customer customer){
        return productStruct(getCartProductList(customer));
    }

    //Ordered List
    private ObservableList<Product> getOrderedProduct(Customer customer){
        ObservableList<Product> pd=FXCollections.observableArrayList();
        String query = "SELECT * FROM orders O join products P on O.customer_id="+customer.id+" WHERE O.product_id=P.pid";
        ResultSet rs = DataBase.getQuery(query);
        try {
            if (rs != null) {
                while (rs.next()) {
                    pd.add(new Product(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ));
                }
                OD=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pd;
    }

    public Pane productOrderedGrid(Customer customer) {return productStruct(getOrderedProduct(customer));}

    // general List
    private ObservableList<Product> getAllProduct(){
        String query;
        if(str.equals(""))
            query="SELECT * FROM `products` WHERE quantity > 0";
        else
            query="SELECT * FROM `products` WHERE quantity>0 && name Like \""+str+"%\"";

        str="";

        ResultSet rs=DataBase.getQuery(query);
        ObservableList<Product> list=FXCollections.observableArrayList();
        try {
            if (rs != null) {
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("pid"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    ));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public Pane productGrid(){
        return productStruct(getAllProduct());
    }
    private Pane productStruct(ObservableList<Product> fun){
        TableColumn id=new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name=new TableColumn("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price=new TableColumn("Price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Ordered Table Extra Function
//        TableColumn date=new TableColumn("Date");
//        date.setCellValueFactory(new PropertyValueFactory<>("date"));

//        ObservableList<Product> data=FXCollections.observableArrayList();
//        data.addAll(new Product(1,"Band",1120.50),
//                new Product(2,"Shoe",250.40));

        productTable=new TableView<>();
        productTable.setItems(fun);
        productTable.getColumns().addAll(id,name,price);

        Pane productPane=new Pane();
        productPane.setPrefSize(600,400);
        productPane.getChildren().addAll(productTable);
        productPane.setTranslateY(50);
        productPane.setTranslateX(50);
        return productPane;
    }
    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();
    }
}
