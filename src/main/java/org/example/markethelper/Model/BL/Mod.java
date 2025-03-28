package org.example.markethelper.Model.BL;

public class Mod extends Item{

    private String polarity;

    public Mod (int id,String name, int price, String polarity){
        super(id,name,price);
        this.polarity = polarity;
    }
    public String getPolarity(){
        return polarity;
    }
    public void setPolarity(String polarity){
        this.polarity = polarity;
    }

    @Override
    public String[] toStringArray() {
        return new String[] {"Mod",String.valueOf(getId()),getName(),String.valueOf(getPrice()),getPolarity()};
    }

}
