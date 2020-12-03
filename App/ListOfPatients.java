package App;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class ListOfPatients{
    private FileWriter listOfPatientsFileWriter;

    public void openFile(){
        try {
            listOfPatientsFileWriter = new FileWriter("src/Data/patientsList" +
                    ".txt");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    File listOfPatientsFile = new File("src/Data/patientsList.txt");

    public void close(){
        try {
            listOfPatientsFileWriter.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public void addRecords(String firstName, String middleName, String lastName, int age, int length, double weight){
        try {
            if(firstName.equals(""))firstName="-";
            if(lastName.equals(""))lastName= "-";
            if(middleName.equals(""))middleName = "NULL";
            String line = firstName + " " + middleName + " " + lastName + " " + age + " " + length + " " + weight;

            listOfPatientsFileWriter.write(line);
            listOfPatientsFileWriter.write("\n");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    ArrayList<Profile> listOfPatients = new ArrayList<>();

    public void readFile(){
        try {
            Scanner reader = new Scanner(listOfPatientsFile).useLocale(Locale.US);
            String firstName;
            String middleName;
            String lastName;
            int age;
            int length;
            double weight;
            while(reader.hasNextLine()) {
                if(reader.hasNext()) {
                    firstName = reader.next();
                } else {
                    firstName = "";
                }
                if(reader.hasNext()) {
                    middleName = reader.next();
                    if(middleName.equals( "NULL")) middleName = "";
                } else {
                    middleName = "";
                }
                if(reader.hasNext()) lastName = reader.next();
                else {
                    lastName = "";
                }
                if(reader.hasNextInt()) age = reader.nextInt();
                else {
                    age = 0;
                }
                if(reader.hasNextInt()) length = reader.nextInt();
                else {
                    length = 0;
                }
                if(reader.hasNextDouble()){
                    weight = reader.nextDouble();
                } else {
                    weight = 0;
                }
                listOfPatients.add(new Profile(firstName, middleName, lastName, age, length, weight));
                if(reader.hasNextLine()) {
                    reader.nextLine();
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Error");
        }
    }
    public ArrayList<Profile> getListOfPatients(){
        return listOfPatients;
    }
}
