package org.example.markethelper.Model.BL;

public class PrimePart extends Item{

    private int rarity;
    private String color;

    public PrimePart (int id, String name, int price, int rarity, String color) {
        super(id, name, price);
        this.rarity = rarity;
        this.color = color;
    }
    public int getRarity() {
        return rarity;
    }
    public String getColor() {
        return color;
    }
    public double ratio() {
        return (double)rarity / (double)getPrice();
    }
    @Override
    public String[] toStringArray() {
        return new String[] {"PrimePart",String.valueOf(getId()),getName(),String.valueOf(getPrice()),String.valueOf(getRarity()),getColor()};
    }
    @Override
    public String toString(){
        return getName();
    }

}
