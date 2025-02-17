package org.example.markethelper.Model.BL;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class User {

    private String id;
    private String hash;

    public User(String id, String hash) {
        this.id = id;
        this.hash = hash;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getHash() {
        return hash;
    }

    public void computeHash(String toHash) {
        Argon2 argon2 = Argon2Factory.create();
        try{
            this.hash = argon2.hash(5,129024,4,toHash.toCharArray());
        } finally {
            argon2.wipeArray(toHash.toCharArray());
        }
    }
    public boolean hashValid(String toVerify) {
        Argon2 argon2 = Argon2Factory.create();
        try{
            return argon2.verify(hash, toVerify.toCharArray());
        }
        finally {
            argon2.wipeArray(toVerify.toCharArray());
        }
    }

}
