package com.example.findrestaurant;

public class Menu {

    String item,price,image,foodId;

    public String getItem() {
        return item;
    }

    public Menu(String item, String price, String image) {
        this.item = item;
        this.price = price;
        this.image = image;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public Menu(String item, String price) {
        this.item = item;
        this.price = price;
    }

    public Menu() {
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
