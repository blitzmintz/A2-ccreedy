package park;

public class Visitor {

    private int id;
    private static int maxId = 0;
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;


    public Visitor (String firstName, String lastName, int age, String phoneNumber) {
        this.id = ++maxId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    /**
     * This constructor allows the id to be set directly rather than auto-incremented. Intended for use when restoring data from file.
     * @param id the ID of the backed up visitor
     * @param firstName the first name of the visitor
     * @param lastName the last name of the visitor
     * @param age the age of the visitor
     * @param phoneNumber the phone number of the visitor
     */
    public Visitor (int id, String firstName, String lastName, int age, String phoneNumber) {
        this.id = id;
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
                "Phone Number: " + this.phoneNumber + "\n"
                ;
    }

    /**
     * This method compares visitors by phone number. If it is different, the objects are not considered equal.
     * This is for the purposes of the assignment, previous iteration had comparison by ID which is preferable but given there is no UI functionality to search/select, this makes more sense.
     *
     * @param o   the reference object with which to compare.
     * @return true/false for object equivalency
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visitor other = (Visitor) o;
        //Of course, in real life, people can provide the same phone number - families especially
        return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName) && this.phoneNumber.equals(other.phoneNumber);
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


}
