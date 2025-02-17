package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class PrimeSet {

    private int setId;
    private ArrayList<PrimePart> primeParts;
    private int setPrice;
    private double seperatedPrice;

    public PrimeSet(int setId, ArrayList<PrimePart> primeParts, int setPrice) {
        this.setId = setId;
        this.primeParts = primeParts;
        this.setPrice = setPrice;
        computeSeperatedSet();
    }
    private void computeSeperatedSet() {

        for(int i = 0; i < primeParts.size(); i++){
            seperatedPrice = seperatedPrice + primeParts.get(i).getPrice();
        }
    }
    public int getSetId() {return setId;}
    public PrimePart getPrimePart(int index) {
        return primeParts.get(index);
    }
    public void setPrimePart(int index, PrimePart primePart) {
        primeParts.set(index, primePart);
    }
    public int getSetPrice() {
        return setPrice;
    }
    public void setSetPrice(int setPrice) {
        this.setPrice = setPrice;
    }
    public double getSeperatedPrice() {
        return seperatedPrice;
    }
    public void addPart(PrimePart primePart) {
    primeParts.add(primePart);
    computeSeperatedSet();
    }
}
