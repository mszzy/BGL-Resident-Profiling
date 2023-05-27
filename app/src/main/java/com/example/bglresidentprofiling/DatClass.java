package com.example.bglresidentprofiling;

public class DatClass {
    private String dataImage;
    private String firstname;
    private String lastname;
    private String middle;
    private String age;
    private String sex;
    private String birthday;
    private String marital;
    private String number;
    private String address;
    private String occupation;
    private String religion;
    private String houseNo;
    private String levelOfEduc;
    private String parentSolo;
    private String seniorCitiz;
    private String regisVoter;
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getMiddle() {
        return middle;
    }
    public String getAge() {
        return age;
    }
    public String getSex() {
        return sex;
    }
    public String getBirthday() {
        return birthday;
    }
    public String getMarital() {
        return marital;
    }
    public String getNumber() {
        return number;
    }
    public String getAddress() {
        return address;
    }
    public String getOccupation() {
        return occupation;
    }
    public String getReligion() {
        return religion;
    }
    public String getHouseNo() {
        return houseNo;
    }
    public String getLevelOfEduc() {
        return levelOfEduc;
    }
    public String getParentSolo() {
        return parentSolo;
    }
    public String getSeniorCitiz() {
        return seniorCitiz;
    }
    public String getRegisVoter() {
        return regisVoter;
    }
    public String getDataImage() {
        return dataImage;
    }
    public DatClass(String firstname, String lastname, String middle, String age, String sex, String birthday, String marital, String number, String address, String occupation, String religion, String houseNo,String levelOfEduc, String parentSolo, String  seniorCitiz, String regisVoter, String dataImage) {
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
        this.levelOfEduc = levelOfEduc;
        this.parentSolo = parentSolo;
        this.seniorCitiz = seniorCitiz;
        this.regisVoter = regisVoter;
        this.dataImage = dataImage;
    }
    public DatClass(){
    }
}
