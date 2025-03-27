package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class PrimeSet {

    private int setId;
    private String setName;
    private ArrayList<PrimePart> primeParts;
    private int setPrice;
    private double seperatedPrice;

    public PrimeSet(int setId, String setName, ArrayList<PrimePart> primeParts, int setPrice) {
        this.setId = setId;
        this.setName = setName;
        this.primeParts = primeParts;
        this.setPrice = setPrice;
        computeSeperatedSet();
    }
    private void computeSeperatedSet() {
        seperatedPrice = 0;
        for(int i = 0; i < primeParts.size(); i++){
            seperatedPrice = seperatedPrice + primeParts.get(i).getPrice();
        }
    }
    public int getSetId() {return setId;}

    public String getSetName() {return setName;}

    public PrimePart getPrimePart(int index) {
            if (index >= 0 && index < primeParts.size()) {
                return primeParts.get(index);
            } else {
                return null;
            }
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
    public int getPrimePartsSize(){
        return primeParts.size();
    }
}
