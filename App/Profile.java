package App;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Profile {
    private String firstName;
    private String middleName;
    private String lastName;
    private int age;
    private int length;
    private double weight;
    private double bmi;

    // Constructor person met overloading
    public Profile() {
    }

    public Profile(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        age = 0;
        length = 0;
        weight = 0.0;
    }

    public Profile(String firstName, String middleName, String lastName, int age, int length, double weight) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
        this.length = length;
        this.weight = weight;
    }

    // Set first name
    public void setFirstName(String newFirstName) {
        this.firstName = newFirstName;
    }

    // Get first name
    public String getFirstName() {
        return this.firstName;
    }

    // Set middle name
    public void setMiddleName(String newMiddleName) {
        this.middleName = newMiddleName;
    }

    // Get middle name
    public String getMiddleName() {
        return this.middleName;
    }

    // Set last name
    public void setLastName(String newLastName) {
        this.lastName = newLastName;
    }

    //Get last name
    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        String fullName;

        if (middleName.equals("")) {
            fullName = firstName + " " + lastName;
        } else {
            fullName = firstName + " " + middleName + " " + lastName;
        }
        return fullName;
    }
    // Set age
    public void setAge(int newAge) {
        this.age = newAge;
    }

    // Get age
    public int getAge() {
        return this.age;
    }

    //Set length
    public void setLength(int length) {
        this.length = length;
    }

    //Get length
    public int getLength() {
        return length;
    }

    //Set weight
    public void setWeight(double weight) {
        this.weight = weight;
    }

    //Get weight
    public double getWeight() {
        return weight;
    }

    //Get BMI
    public double getBodyMassIndex() {
        double lengthDouble = length;
        double lengthDoubleMeter = lengthDouble / 100;
        this.bmi = weight / (lengthDoubleMeter * lengthDoubleMeter);
        return bmi;
    }

    public void setListOfPrescriptions(ArrayList<Prescription> listOfPrescriptions) {
        this.listOfPrescriptions = listOfPrescriptions;
    }

    ArrayList<Prescription> listOfPrescriptions = new ArrayList<>();

    public void setListOfWeightPoints(ArrayList<WeightPoint> listOfWeightPoints) {
        this.listOfWeightPoints = listOfWeightPoints;
    }

    ArrayList<WeightPoint> listOfWeightPoints = new ArrayList<>();

}
