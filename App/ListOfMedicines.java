package App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ListOfMedicines {
    private FileWriter listOfMedicinesFileWriter;

    public void openFile(){
        try {
            listOfMedicinesFileWriter = new FileWriter("src/Data/medicinesList.txt");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    File file = new File("src/Data/medicinesList.txt");

    public void close(){
        try {
            listOfMedicinesFileWriter.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void addMedicines(int id, String name, String dose, String type, String description){
        try {
            description = description.replaceAll("\n", " ");
            String line = id + " " + name + " "  + dose + " " + type + " " +  " \n" + description;
            listOfMedicinesFileWriter.write(line);
            listOfMedicinesFileWriter.write("\n");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }



    ArrayList<Medicine> listOfMedicines = new ArrayList<>();

    public void readFile(){
        try {
            Scanner reader = new Scanner(file);
            String medicineName;
            String medicineDescription;
            String medicineDose;
            String medicineType;
            int medicineID;
            while(reader.hasNextLine()) {
                if(reader.hasNextInt()){
                    medicineID = reader.nextInt();
                } else {
                    medicineID = 0;
                }
                if(reader.hasNext()) {
                    medicineName = reader.next();
                } else {
                    medicineName = "";
                }
                if(reader.hasNext()){
                    medicineDose = reader.next();
                } else {
                    medicineDose = "";
                }
                if(reader.hasNext()){
                    medicineType = reader.next();
                } else {
                    medicineType = "";
                }
                if(reader.hasNextLine()) {
                    reader.nextLine();
                    medicineDescription = reader.nextLine();
                } else {
                    medicineDescription = "";
                }


                listOfMedicines.add(new Medicine(medicineName, medicineDescription, medicineDose, medicineType));
            }
        } catch (FileNotFoundException e){
            System.out.println("Error");
        }
    }
    public ArrayList<Medicine> getListOfMedicines(){
        return listOfMedicines;
    }
}


