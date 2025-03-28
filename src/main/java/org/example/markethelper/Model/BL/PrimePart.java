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
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    @Override
    public String[] toStringArray() {
        return new String[] {"PrimePart",String.valueOf(getId()),getName(),String.valueOf(getPrice()),String.valueOf(getRarity()),getColor()};
    }

}
