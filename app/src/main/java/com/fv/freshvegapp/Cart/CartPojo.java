package com.fv.freshvegapp.Cart;

public class CartPojo {

    String CategoryName,ProductName,product_img,Quantity,Subprice,Price,Count;

    public String getSubprice() {
        return Subprice;
    }

    public void setSubprice(String subprice) {
        Subprice = subprice;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProduct_img() {
        return product_img;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
