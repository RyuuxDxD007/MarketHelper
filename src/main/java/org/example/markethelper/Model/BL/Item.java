package org.example.markethelper.Model.BL;

public class Item {

    private int id;
    private String name;
    private int price;

    public Item (int id,String name, int price){
        this.id=id;
        this.name = name;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }


    public String[] toStringArray() {
        return new String[] {"Item",String.valueOf(id),name,String.valueOf(price)};
    }
}
