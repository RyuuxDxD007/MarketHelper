package org.example.markethelper.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IdDispenser {
    private Set<Integer> usedIDs;
    private int lastGeneratedID;

    public IdDispenser(ArrayList<Integer> ids) {
        this.usedIDs = new HashSet<>(ids);
        this.lastGeneratedID = 0;

        if (!usedIDs.isEmpty()) {
            this.lastGeneratedID = usedIDs.stream().max(Integer::compare).get();
        }
    }

    public int getNextID() {
        for (int i = 1; i <= lastGeneratedID; i++) {
            if (!usedIDs.contains(i)) {
                usedIDs.add(i);
                return i;
            }
        }

        lastGeneratedID++;
        usedIDs.add(lastGeneratedID);
        return lastGeneratedID;
    }

    public void deleteID(int id) {
        usedIDs.remove(id);
    }

    public Set<Integer> getUsedIDs() {
        return Set.copyOf(usedIDs);
    }
}
