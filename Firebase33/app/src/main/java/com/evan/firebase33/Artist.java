package com.evan.firebase33;

public class Artist {
    String w;
    String firstName;
    String lastName;

    public Artist(){}
    public Artist(String w, String name, String lastName){

        this.w=w;
        this.firstName = name;
        this.lastName= lastName;
    }


    public String getW() {
        return w;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
