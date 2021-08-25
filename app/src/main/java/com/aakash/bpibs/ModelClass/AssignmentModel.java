package com.aakash.bpibs.ModelClass;

public class AssignmentModel {
    public String classFor;
    public String semFor;
    public String subject;
    public String description;
    public String assignedDate;
    public String lastDate;

    public String getClassFor() {
        return classFor;
    }

    public void setClassFor(String classFor) {
        this.classFor = classFor;
    }

    public String getSemFor() {
        return semFor;
    }

    public void setSemFor(String semFor) {
        this.semFor = semFor;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public AssignmentModel() {
    }

    public AssignmentModel(String classFor, String semFor, String subject, String description, String assignedDate, String lastDate) {
        this.classFor = classFor;
        this.semFor = semFor;
        this.subject = subject;
        this.description = description;
        this.assignedDate = assignedDate;
        this.lastDate = lastDate;
    }
}

