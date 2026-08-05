package models;

import java.time.LocalDate;

public class Employee {

    private int id;
    private static int maxId = 0;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;
    private LocalDate startOfEmployment;
    private String jobTitle;

    public Employee (String firstName, String lastName, int age, String phoneNumber, LocalDate startOfEmployment, String jobTitle) {
        this.id = ++maxId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.startOfEmployment = startOfEmployment;
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "Employee ID: " + this.id + "\n" +
        "Name: " + this.firstName + " " + this.lastName + "\n" +
        "Age: " + this.age + "\n" +
        "Phone Number: " + this.phoneNumber + "\n" +
        "Employment Start Date: " + this.startOfEmployment.toString() + "\n" +
        "Job Title: " + this.jobTitle + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee other = (Employee) o;
        return this.id == other.id;
    }

    public int getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setStartOfEmployment(LocalDate startOfEmployment) {
        this.startOfEmployment = startOfEmployment;
    }

    public LocalDate getStartOfEmployment() {
        return this.startOfEmployment;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
