package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class PrimeSet {

    private int setId;
    private String setName;
    private ArrayList<PrimePart> primeParts;
    private int setPrice;
    private int seperatedPrice;

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

    public ArrayList<PrimePart> getPrimeParts() {return primeParts;}

    public PrimePart getPrimePart(int index) {
            if (index >= 0 && index < primeParts.size()) {
                return primeParts.get(index);
            } else {
                return null;
            }
    }
    public int getSetPrice() {
        return setPrice;
    }
    public int getSeperatedPrice() {
        return seperatedPrice;
    }
    public int getPrimePartsSize(){
        return primeParts.size();
    }
    public String[] toStringArray() {
        String[] set = new String[10];
        set[0] = "PrimeSet";
        set[1] = String.valueOf(getSetId());
        set[2] = getSetName();
        set[3] = String.valueOf(getSetPrice());
        for (int i = 0; i < 6; i++) {
            PrimePart part = getPrimePart(i);
            set[4 + i] = (part != null) ? String.valueOf(part.getId()) : "null";
        }
        return set;
    }
}
