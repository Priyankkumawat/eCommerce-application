package com.example.ecommerce;

import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    Login_SignUp lg=new Login_SignUp();
    ProductList productList=new ProductList();
    private int height=520,width=480,headerBar=50;
    Pane root;
    Label welcome =new Label("Welcome to Ichiba(市場)");
    private Customer customerDetails=null;
    Button backToBuy=new Button("Buy More");
//    private
    private GridPane feedbackPane(Product prod){
        GridPane home=new GridPane();
        home.setTranslateY(100);
//        home.setHgap(20);
        home.setVgap(20);
        home.setTranslateX(50);
        // label
        Label thankYou=new Label("Thank you for using Ichiba(市場)");
        Label message=new Label("Your Order "+prod.getName()+" will be deliver in 4-5 working days.");
        Label rate=new Label("Please rate us on scale 1 to 5");

        //Button
        Button backShop=new Button("Back");
        Button Submit=new Button("Submit");

        //slider
        Slider rating=new Slider(1,5,1);
        rating.setPrefSize(50,10);

        backShop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().addAll(
                        header(),
                        productList.productGrid(),
                        footer()
                );
            }
        });

        Submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Double slide=rating.getValue();
                if(Rating.submitRating(slide,customerDetails) != 0){
                    root.getChildren().clear();
                    root.getChildren().addAll(
                            header(),
                            productList.productGrid(),
                            footer()
                    );
                }
            }
        });

        home.add(thankYou,0,0);
        home.add(message,0,1);
        home.add(rating,0,2);
        home.add(rate,0,3);
        home.add(backShop,0,4);
        home.add(Submit,2,4);
        return home;
    }
    private GridPane footer(){
        GridPane foot=new GridPane();
        foot.setPrefSize(width,headerBar);
        foot.setTranslateY(465);
        foot.setTranslateX(50);
        foot.setHgap(10);
        Button buy=new Button("Buy Now");
        Button addToCartButton=new Button("Add To Cart");
        Button backSearch=new Button("Back");

        buy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product p=productList.getSelectedProduct();
                if(p != null){
                    if(Order.getOrder(customerDetails,p)){
                        root.getChildren().clear();
                        root.getChildren().addAll(
                                header(),
                                feedbackPane(p)
                        );
                    }
                    else{
                        Dialog<String> dialog=new Dialog<>();
                        dialog.setTitle("Buy Detail");
                        ButtonType type=new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                        dialog.getDialogPane().getButtonTypes().add(type);
                        dialog.setContentText("Error occur try after few minutes");
                        dialog.showAndWait();
                    }
                }
            }
        });

        backSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().addAll(
                        header(),
                        productList.productGrid(),
                        footer()
                );
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product p=productList.getSelectedProduct();
                if( p != null){
                    Dialog<String> dialog=new Dialog<>();
                    dialog.setTitle("Cart Detail");
                    ButtonType type=new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(type);
                    if(Cart.addToCart(p,customerDetails)){
                        dialog.setContentText(p.getName()+" added to your cart");
                        dialog.showAndWait();
                    }
                    else{
                        dialog.setContentText("Already in Cart");
                        dialog.showAndWait();
                    }
                }
            }
        });

        foot.add(buy,0,0);
        foot.add(addToCartButton,2,0);
        foot.add(backSearch,3,0);
        return foot;
    }
    private GridPane header(){
        GridPane head=new GridPane();
        head.setPrefSize(width,headerBar);
        head.setHgap(5);
        head.setTranslateY(10);
        head.setTranslateX(25);

        TextField searchbar=new TextField();
        Button searchButton =new Button("Search");
        Button cartButton=new Button("Cart");
        Button myOrder=new Button("Order's");

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=searchbar.getText();
                productList.search(name);
                root.getChildren().clear();
                root.getChildren().addAll(
                        header(),
                        productList.productGrid(),
                        footer()
                );
            }
        });

        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().addAll(
                        header(),
                        productList.productCartGrid(customerDetails),
                        footer()
                );
            }
        });

        myOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().addAll(
                        header(),
                        productList.productOrderedGrid(customerDetails),
                        footer()
                );
            }
        });

//        searchbar.setPrefSize(0,0);
        head.add(searchbar,0,0);
        head.add(searchButton,1,0);
        head.add(welcome,2,0);
        head.add(cartButton,3,0);
        head.add(myOrder,4,0);
        return head;
    }


    private GridPane signUpPage() {
        //labels
        Label namelabel = new Label("Full Name");
        Label mobilelabel = new Label("Mobile");
        Label emaillabel = new Label("Email");
        Label addreslabel = new Label("Address");
        Label passwordlabel = new Label("Password");
        Label error=new Label("This is signUp Page");

        //textfield
        TextField nametext = new TextField();
        TextField mobiletext = new TextField();
        TextField emailtext = new TextField();
        TextField addresstext = new TextField();
        PasswordField passwordtext = new PasswordField();

        //button
        Button backToLogin=new Button("Back");
        Button signUpButton=new Button("Sign Up");

        backToLogin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().add(loginPage());
            }
        });

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name=nametext.getText();
                String mobile=mobiletext.getText();
                String email=emailtext.getText();
                String address=addresstext.getText();
                String password=passwordtext.getText();
                if(lg.addCustomer(name,mobile,email,address,password) == 1){
                    customerDetails=lg.validate(mobile,password);
                    if(customerDetails != null){
                        welcome.setText(("Welcome "+customerDetails.name));
                    }
                    root.getChildren().clear();
                    root.getChildren().addAll(
                            header(),
                            productList.productGrid(),
                            footer()
                    );
                }
                else{
                    error.setText("Some Field is Empty/Error");
                }
            }
        });

        GridPane signUpPane=new GridPane();
        signUpPane.setHgap(20);
        signUpPane.setVgap(10);
        signUpPane.setTranslateX(70);
        signUpPane.setTranslateY(80);
        signUpPane.add(backToLogin,0,8);
        signUpPane.add(welcome,0,0);
        signUpPane.add(namelabel,0,1);
        signUpPane.add(nametext,1,1);
        signUpPane.add(mobilelabel,0,2);
        signUpPane.add(mobiletext,1,2);
        signUpPane.add(emaillabel,0,3);
        signUpPane.add(emailtext,1,3);
        signUpPane.add(addreslabel,0,4);
        signUpPane.add(addresstext,1,4);
        signUpPane.add(passwordlabel,0,5);
        signUpPane.add(passwordtext,1,5);
        signUpPane.add(signUpButton,0,6);
        signUpPane.add(error,0,7);
        return signUpPane;
    }

    private GridPane loginPage(){
        //Elements

        Label userlabel = new Label("User");
        Label passlabel=new Label("Password");
        TextField user=new TextField("Email/Mobile");
        PasswordField password=new PasswordField();
        Button loginBtn=new Button("Login");
        Label message=new Label("Login-Message.");
        Label option=new Label("Or");
        Button signUp=new Button("Sign Up");

        //Button action
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String User=user.getText();
                String pass=password.getText();
                customerDetails=lg.validate(User,pass);
                if(customerDetails != null){
                    message.setText("Login-Successful!");
                    welcome.setText(("Welcome "+customerDetails.name));
                    root.getChildren().clear();
                    root.getChildren().addAll(
                            footer(),
                            header(),
                            productList.productGrid()
                    );
                }
                else{
                    message.setText("Login-Failed!");
                }
            }
        });

        signUp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();
                root.getChildren().add(signUpPage());
            }
        });

        GridPane loginPane=new GridPane();
        loginPane.setHgap(10);
        loginPane.setVgap(20);
        loginPane.setTranslateX(100);
        loginPane.setTranslateY(100);
        loginPane.add(welcome,0,0);
        loginPane.add(userlabel,0,1);
        loginPane.add(passlabel,0,2);
        loginPane.add(user,1,1);
        loginPane.add(password,1,2);
        loginPane.add(loginBtn,0,3);
        loginPane.add(message,1,3);
        loginPane.add(option,0,4);
        loginPane.add(signUp,0,5);
        return loginPane;
    }

    private Pane createContent(){
        root=new Pane();
        root.setPrefSize(width,height);

        root.getChildren().addAll(
                loginPage()
        );
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(createContent());
        scene.setFill(Color.BLUE);
        stage.setTitle("Ichiba(市場)");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
