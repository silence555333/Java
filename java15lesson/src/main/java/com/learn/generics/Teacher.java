package com.learn.generics;

public class Teacher {
    int ID;
    String Name;

    public Teacher() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {

        return ID +
                "," + Name ;
    }
}
