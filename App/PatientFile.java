package App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class PatientFile {
    private FileWriter fileWriter;
    private File file;

    public void openFile(String fileName){
        try {
            file = new File("src/Data/patients/"+ fileName +".txt");
            fileWriter = new FileWriter(file);
        } catch (Exception e) {
            System.out.println("Error3");
        }
    }



    public void close(){
        try {
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }


    public void addPrescriptions(int id, int atHour, int atMinute, int interval, int times){
        try {
            String line = id + " " + atHour + " " + atMinute + " " + interval + " " + times + "\n";
            fileWriter.write(line);
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public void addSeparationLine(){
        try {
            fileWriter.write("XXXXX\n");
        } catch (Exception e){
            System.out.println("Error");
        }
    }

    public void addWeightPoints(int day, int month, int year, String time, double weight){
        try {
            String line = day + " " + month + " " + year + " " + time + " " + weight + "\n";
            fileWriter.write(line);
        } catch (Exception e) {
            System.out.println("Error");
        }
    }



    ArrayList<Prescription> listOfPrescriptions = new ArrayList<>();
    ArrayList<WeightPoint> listOfWeightPoints = new ArrayList<>();

    public void readFile(ArrayList<Medicine> listOfMedicine, String fileName){
        file = new File("src/Data/patients/"+ fileName +".txt");
        listOfPrescriptions.clear();
        try {
            Scanner reader = new Scanner(file).useLocale(Locale.US);
            Medicine medicine;
            int id;
            int atHour;
            int atMinute;
            int interval;
            int times;
            while (true){

                if(reader.hasNextInt()){
                    id = reader.nextInt();
                    medicine = listOfMedicine.get(id);
                } else {
                    break;
                }
                if(reader.hasNextInt()){
                    atHour = reader.nextInt();
                } else {
                    atHour = 0;
                }
                if(reader.hasNextInt()){
                    atMinute = reader.nextInt();
                } else {
                    atMinute = 0;
                }
                if(reader.hasNextInt()){
                    interval = reader.nextInt();
                } else {
                    interval = 24;
                }
                if(reader.hasNextInt()){
                    times = reader.nextInt();
                } else {
                    times = 24/interval;
                }

                listOfPrescriptions.add(new Prescription(medicine, atHour, atMinute, interval, times));
                if(reader.nextLine().contains("XXXXX")) break;

                }
            if(reader.hasNextLine())reader.nextLine();
            int day;
            int month;
            int year;
            String time;
            double weight;
            while (reader.hasNextLine()) {
                if(reader.hasNextInt()) day = reader.nextInt();
                else break;
                if(reader.hasNextInt()) month = reader.nextInt();
                else month = 1;
                if(reader.hasNextInt()) year= reader.nextInt();
                else year = 1900;
                if(reader.hasNext()) time = reader.next();
                else time = "??:??";
                if(reader.hasNextDouble()) weight = reader.nextDouble();
                else weight = 1000.3;
                listOfWeightPoints.add(new WeightPoint(day, month, year, time, weight));

            }

        } catch (FileNotFoundException e){
            System.out.println("Error");
        }
    }
    public ArrayList<Prescription> getListOfPrescriptions(){
        return listOfPrescriptions;
    }

    public ArrayList<WeightPoint> getListOfWeightPoints(){
        return listOfWeightPoints;
    }
}

