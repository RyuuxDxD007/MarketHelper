package org.example.markethelper.Model.BL;

import java.util.ArrayList;

public class Relic {

    final private String BRONZE="Bronze";
    final private String SILVER="Silver";
    final private String GOLD="Gold";
    final private int MAX_BRONZE=3;
    final private int MAX_SILVER=2;
    final private int MAX_GOLD=1;
    final private int QUANITY_PARTS=6;

    private int relicId;
    private String relicName;
    private ArrayList<PrimePart> primeParts = new ArrayList<>();
    private int relicPrice;
    private int seperatedPrice;

    public Relic(int relicId, String relicName, ArrayList<PrimePart> primeParts, int relicPrice) {
        this.relicId=relicId;
        this.relicName=relicName;
        AddPrimeParts(primeParts);
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
    public void AddPrimeParts(ArrayList<PrimePart> parts) {

        int bronze = findBronze(parts);
        int silver = findSilver(parts);
        int gold = findGold(parts);

        //to ensure it's not adding more parts than it should
        bronze = bronze + findBronze(primeParts);
        silver = silver + findSilver(primeParts);
        gold = gold + findGold(primeParts);

        for(PrimePart primePart : parts) {

            if (primePart.getColor().equals(BRONZE)) {
                if (bronze <= MAX_BRONZE) {
                    primeParts.add(primePart);
                } else {
                    throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.(Bronze)");
                }

            } else if (primePart.getColor().equals(SILVER)) {
                if (silver <= MAX_SILVER) {
                    primeParts.add(primePart);
                } else {
                    throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.(Silver)");
                }

            } else if (primePart.getColor().equals(GOLD)) {
                if (gold <= MAX_GOLD) {
                    primeParts.add(primePart);
                } else {
                    throw new IllegalArgumentException("Cannot add more PrimeParts of this rarity.(Gold)");
                }

            }
        }
        //if the number of parts is not the one wanted delete the added parts.
        if(primeParts.size()<QUANITY_PARTS){
            primeParts=null;
        }


    }
    public int findBronze(ArrayList<PrimePart> parts){
        int bronze = 0;
        for(int i = 0; i < parts.size(); i++){
            if(parts.get(i).getColor().equals(BRONZE)){
                bronze++;
            }
        }
        return bronze;
    }
    public int findSilver(ArrayList<PrimePart> parts){
        int silver = 0;
        for(int i = 0; i < parts.size(); i++){
            if(parts.get(i).getColor().equals(SILVER)){
                silver++;
            }
        }
        return silver;
    }
    public int findGold(ArrayList<PrimePart> parts){
        int gold = 0;
        for(int i = 0; i < parts.size(); i++){
            if(parts.get(i).getColor().equals(GOLD)){
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
