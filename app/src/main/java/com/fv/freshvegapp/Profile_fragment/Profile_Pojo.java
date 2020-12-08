package com.fv.freshvegapp.Profile_fragment;

public class Profile_Pojo {

    private String Name;
    private String Email;
    private String Age;
    private String Gender;
    private String Number;
    private String Passcode;


    public Profile_Pojo() {
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {

                  Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getPasscode() {
        return Passcode;
    }

    public void setPasscode(String passcode) {
        Passcode = passcode;
    }
}
