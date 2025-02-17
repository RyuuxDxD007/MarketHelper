package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class Relic {

    final private String BRONZE="bronze";
    final private String SILVER="silver";
    final private String GOLD="gold";
    final private int MAX_BRONZE=3;
    final private int MAX_SILVER=2;
    final private int MAX_GOLD=1;


    private ArrayList<PrimePart> primeParts;
    private int setPrice;
    private int seperatedPrice;

    public Relic(ArrayList<PrimePart> primeParts, int setPrice) {
        this.primeParts = primeParts;
        this.setPrice = setPrice;
        computeSeperatedSet();
    }
    public PrimePart getPrimePart(int index) {
        return primeParts.get(index);
    }
    public void setPrimePart(int index, PrimePart primePart) {
        primeParts.set(index, primePart);
    }
    public void computeSeperatedSet (){
        //reset si jamais lancer plusieurs fois
        seperatedPrice = 0;
        for(int i = 0; i < primeParts.size(); i++){
            seperatedPrice = seperatedPrice + primeParts.get(i).getPrice();
        }
    }
    public void setSetPrice(int setPrice) {
        this.setPrice = setPrice;
    }
    public int getSetPrice() {
        return setPrice;
    }
    public int getSeperatedPrice() {
        return seperatedPrice;
    }
    public void AddPrimePart(PrimePart primePart) {

        int bronze = findBronze();
        int silver = findSilver();
        int gold = findGold();


        if(primePart.getColor().equals(BRONZE)){
            if(bronze<MAX_BRONZE){
                primeParts.add(primePart);
            }
            else {
                throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.");
            }

        }
        else if(primePart.getColor().equals(SILVER)){
            if(silver<MAX_SILVER){
                primeParts.add(primePart);
            }
            else {
                throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.");
            }

        }
        else if(primePart.getColor().equals(GOLD)){
            if(gold<MAX_GOLD){
                primeParts.add(primePart);
            }
            else {
                throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.");
            }

        }
        computeSeperatedSet();
    }
    public int findBronze(){
        int bronze = 0;
        for(int i = 0; i < primeParts.size(); i++){
            if(primeParts.get(i).getColor().equals(BRONZE)){
                bronze++;
            }
        }
        return bronze;
    }
    public int findSilver(){
        int silver = 0;
        for(int i = 0; i < primeParts.size(); i++){
            if(primeParts.get(i).getColor().equals(SILVER)){
                silver++;
            }
        }
        return silver;
    }
    public int findGold(){
        int gold = 0;
        for(int i = 0; i < primeParts.size(); i++){
            if(primeParts.get(i).getColor().equals(GOLD)){
                gold++;
            }
        }
        return gold;
    }


}
