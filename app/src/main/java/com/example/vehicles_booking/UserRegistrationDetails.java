package com.example.vehicles_booking;

import java.io.Serializable;

public class UserRegistrationDetails implements Serializable {

    private String pname, pMobileNumber, pAge, pgender, pAddress, pBloodGroup, pPassword, pWeight, pHight, pBmr, pBmi, pBP, pEmargencyNumber1, pEmargencyNumber2, plastDateOfDonation;


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getpMobileNumber() {
        return pMobileNumber;
    }

    public void setpMobileNumber(String pMobileNumber) {
        this.pMobileNumber = pMobileNumber;
    }

    public String getpAge() {
        return pAge;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public String getPgender() {
        return pgender;
    }

    public void setPgender(String pgender) {
        this.pgender = pgender;
    }

    public String getpAddress() {
        return pAddress;
    }

    public void setpAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getpBloodGroup() {
        return pBloodGroup;
    }

    public void setpBloodGroup(String pBloodGroup) {
        this.pBloodGroup = pBloodGroup;
    }

    public String getpPassword() {
        return pPassword;
    }

    public void setpPassword(String pPassword) {
        this.pPassword = pPassword;
    }

    public String getpWeight() {
        return pWeight;
    }

    public void setpWeight(String pWeight) {
        this.pWeight = pWeight;
    }

    public String getpHight() {
        return pHight;
    }

    public void setpHight(String pHight) {
        this.pHight = pHight;
    }

    public String getpBmr() {
        return pBmr;
    }

    public void setpBmr(String pBmr) {
        this.pBmr = pBmr;
    }

    public String getpBmi() {
        return pBmi;
    }

    public void setpBmi(String pBmi) {
        this.pBmi = pBmi;
    }

    public String getpBP() {
        return pBP;
    }

    public void setpBP(String pBP) {
        this.pBP = pBP;
    }

    public String getpEmargencyNumber1() {
        return pEmargencyNumber1;
    }

    public void setpEmargencyNumber1(String pEmargencyNumber1) {
        this.pEmargencyNumber1 = pEmargencyNumber1;
    }

    public String getpEmargencyNumber2() {
        return pEmargencyNumber2;
    }

    public void setpEmargencyNumber2(String pEmargencyNumber2) {
        this.pEmargencyNumber2 = pEmargencyNumber2;
    }

    public String getPlastDateOfDonation() {
        return plastDateOfDonation;
    }

    public void setPlastDateOfDonation(String plastDateOfDonation) {
        this.plastDateOfDonation = plastDateOfDonation;
    }
}