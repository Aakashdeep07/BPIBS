package com.aakash.bpibs.ModelClass;

public class StudentModel {
    public String mobileNumber;
    public String studentName;
    public String fatherName;
    public String rollNumber;
    public String classIn;
    public String semIn;
    public String gender;
    public String birthDate;
    public String profilePicture;
    public Boolean verified;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getClassIn() {
        return classIn;
    }

    public void setClassIn(String classIn) {
        this.classIn = classIn;
    }

    public String getSemIn() {
        return semIn;
    }

    public void setSemIn(String semIn) {
        this.semIn = semIn;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public StudentModel() {
    }

    public StudentModel(String mobileNumber, String studentName, String fatherName, String rollNumber, String classIn, String semIn, String gender, String birthDate, String profilePicture, Boolean verified) {
        this.mobileNumber = mobileNumber;
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.rollNumber = rollNumber;
        this.classIn = classIn;
        this.semIn = semIn;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;
        this.verified = verified;
    }
}
