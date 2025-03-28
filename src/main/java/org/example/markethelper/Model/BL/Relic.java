package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class Relic {

    final private String BRONZE="bronze";
    final private String SILVER="silver";
    final private String GOLD="gold";
    final private int MAX_BRONZE=3;
    final private int MAX_SILVER=2;
    final private int MAX_GOLD=1;

    private int relicId;
    private String relicName;
    private ArrayList<PrimePart> primeParts;
    private int relicPrice;
    private int seperatedPrice;

    public Relic(int relicId, String relicName, ArrayList<PrimePart> primeParts, int relicPrice) {
        this.relicId=relicId;
        this.relicName=relicName;
        this.primeParts = primeParts;
        this.relicPrice = relicPrice;
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
    public int getRelicId() {return relicId;}
    public String getRelicName(){return relicName;}
    public int getRelicPrice() {
        return relicPrice;
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
    public String[] toStringArray() {
        String[] relic = new String[10];
        relic[0] = "Relic";
        relic[1] = String.valueOf(relicId);
        relic[2] = relicName;
        relic[3] = String.valueOf(relicPrice);
        for (int i = 0; i < 6; i++) {
            PrimePart part = getPrimePart(i);
            relic[4 + i] = (part != null) ? String.valueOf(part.getId()) : "null";
        }
        return relic;
    }


}
