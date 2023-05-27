package com.example.bglresidentprofiling;

public class HelpClass {
    String firstname, lastname, middle, age, sex, birthday, marital, number, address, occupation, religion, houseNo, householdMem;
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getMiddle() {
        return middle;
    }
    public void setMiddle(String middle) {
        this.middle = middle;
    }
    public String getAge() {
        return age;
    }
    public void setAge(String age) {this.age = age; }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getMarital() {
        return marital;
    }
    public void setMarital(String marital) {
        this.marital = marital;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public String getReligion() {
        return religion;
    }
    public void setReligion(String religion) {
        this.religion = religion;
    }
    public String getHouseNo() {
        return houseNo;
    }
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }
    public String getHouseholdMem() {
        return householdMem;
    }
    public void setHouseholdMem(String password) {
        this.householdMem = householdMem;
    }
    public HelpClass(String firstname, String lastname, String middle, String age, String sex, String birthday, String marital, String number, String address, String occupation, String religion, String houseNo,String householdMem ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middle = middle;
        this.age = age;
        this.sex = sex;
        this.birthday = birthday;
        this.marital = marital;
        this.number = number;
        this.address = address;
        this.occupation = occupation;
        this.religion = religion;
        this.houseNo = houseNo;
        this.householdMem = householdMem;
    }
    public HelpClass() {
    }
}

