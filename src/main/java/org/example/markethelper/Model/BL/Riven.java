package org.example.markethelper.Model.BL;

public class Riven extends Mod{

    private int rerolls;

    public Riven (int id,String name, int price, String polarity, int rerolls) {
        super(id,name,price,polarity);
        this.rerolls = rerolls;
    }
    public int getRerolls() {
        return rerolls;
    }
    public void setRerolls(int rerolls) {
        this.rerolls = rerolls;
    }
}
