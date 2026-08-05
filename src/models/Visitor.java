package models;

import java.time.LocalDate;

public class Visitor {

    private int id;
    private static int maxId = 0;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate lastVisitDate;
    private String phoneNumber;

    public Visitor (String firstName, String lastName, int age, LocalDate lastVisitDate, String phoneNumber) {
        this.id = ++maxId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.lastVisitDate = lastVisitDate;
        this.phoneNumber = phoneNumber;
    }
    public Visitor (String firstName, String lastName, int age, String phoneNumber) {
        this.id = ++maxId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
    public Visitor (String firstName, int age, String phoneNumber) {
        this.id = ++maxId;
        this.firstName = firstName;
        this.lastName = "";
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Visitor ID: " + this.id + "\n" +
                "Name: " + this.firstName + " " + this.lastName + "\n" +
                "Age: " + this.age + "\n" +
                "Phone Number: " + this.phoneNumber + "\n" +
                // the ternary operator is a shorthand way to evaluate a condition and produce a different result if it's true or false
                // here I have used it to check if a last visit date exists, and if it does, I print it as a string
                // if it doesn't exist, I print unknown
                "Last Visit Date: " + (this.lastVisitDate != null ? this.lastVisitDate.toString() : "Unknown") + "\n"
                ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor other = (Visitor) o;
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

    public void setLastVisitDate(LocalDate lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public LocalDate getLastVisitDate() {
        return this.lastVisitDate;
    }
}
