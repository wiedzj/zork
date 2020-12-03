package App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import static java.lang.StrictMath.abs;
import static java.lang.Thread.sleep;

public class ZorgApp {

    // Icons
    private Icon medicineIcon = new ImageIcon("src/App/images/pil.png");
    private Icon deleteIcon = new ImageIcon("src/App/images/prullenbak.png");
    private Icon languageIcon = new ImageIcon("src/App/Images/taal.png");
    private Icon weightPointIcon = new ImageIcon("src/App/Images/gewicht.png");

    // Swing components for frame
    private JFrame mainFrame = new JFrame("zork");
    private JDialog changeLanguageFrame = new JDialog(mainFrame);
    private JLabel firstNameLabel = new JLabel();
    private JTextField firstNameTextField = new JTextField();

    private JLabel lastNameLabel = new JLabel();
    private JTextField lastNameTextField = new JTextField();

    private JLabel ageLabel = new JLabel();
    private JFormattedTextField ageTextField = new JFormattedTextField();

    private JLabel lengthLabel = new JLabel();
    private JFormattedTextField lengthTextField = new JFormattedTextField();

    private JLabel weightLabel = new JLabel();
    private JFormattedTextField weightTextField = new JFormattedTextField();

    private JLabel bmiLabel = new JLabel();
    private JFormattedTextField bmiTextField = new JFormattedTextField();

    // Buttons
    private JButton editButton = new JButton();
    private JButton previousPatientButton = new JButton("<");
    private JButton nextPatientButton = new JButton(">");
    private JButton newPatientButton = new JButton("+");
    private JButton medicineButton = new JButton(medicineIcon);

    // Colors
    private Color frameBackground = new Color(0x8555C6);
    private Color medicineFrameBackground = new Color(0xFA2E82);
    private Color textFieldColor = new Color(0x00DB9E);
    private Color textFieldEditableColor = new Color(0x3E92EA);
    private Color buttonColor = new Color(0xFF8A4C);
    private Color buttonDisabledColor = new Color(0xFFEB60);
    private Color timerColor = new Color(0);
    private Color timeTextColor = new Color(0xFF0800);

    public ZorgApp() {
    }

    // Language dependent labels
    private String firstNameLabelText;
    private String lastNameLabelText;
    private String ageLabelText;
    private String lengthLabelText;
    private String weightLabelText;
    private String bmiLabelText;
    private String bmiUnknown;
    private String editButtonSaveText;
    private String editButtonEditText;
    private String medicineFrameTitle;
    private String prescriptMedicineToPatientFrameTitle;
    private String patientNameLabelText;
    private String medicineNameLabelText;
    private String prescriptionFirstIntakeLabelText;
    private String prescriptionTimesLabelText;
    private String prescriptionIntervalLabelText;
    private String weightChartTitle;
    private String weightPointFrameTitle;
    private String dateLabelText;
    private String timeLabelText;
    private String noDifferenceInWeight;
    private String gainedWeight;
    private String lostWeight;
    private String newWeightPointFrameTitle;
    private String hour;
    private String prescriptionDescription1;
    private String prescriptionDescription2;
    private String prescriptionDescription3;
    private String prescriptionDescription4;
    private String prescriptionDescription5;

    // talen
    private Locale english = new Locale("en");
    private Locale dutch = new Locale("nl");
    private Locale german = new Locale("de");

    private ResourceBundle chosenLanguage = ResourceBundle.getBundle("App/bundle", dutch);

    // Change language dependent strings to the chosen language
    public void setLanguage(){
        firstNameLabelText = chosenLanguage.getString("firstName");
        lastNameLabelText  = chosenLanguage.getString("lastName");
        ageLabelText = chosenLanguage.getString("age");
        lengthLabelText = chosenLanguage.getString("length");
        weightLabelText = (chosenLanguage.getString("weight"));
        bmiLabelText = chosenLanguage.getString("BMI");
        bmiUnknown = chosenLanguage.getString("bmiUnknown");
        editButtonSaveText = chosenLanguage.getString("save");
        editButtonEditText = chosenLanguage.getString("edit");

        medicineFrameTitle = chosenLanguage.getString("medicationOverview");

        prescriptMedicineToPatientFrameTitle = (chosenLanguage.getString("newPrescription"));

        patientNameLabelText = (chosenLanguage.getString("patient"));
        medicineNameLabelText = (chosenLanguage.getString("medicine"));
        prescriptionFirstIntakeLabelText = (chosenLanguage.getString("firstTime"));
        prescriptionTimesLabelText = (chosenLanguage.getString("times"));
        prescriptionIntervalLabelText = (chosenLanguage.getString("interval"));

        prescriptionDescription1 = chosenLanguage.getString("prescriptionDescription1");
        prescriptionDescription2 = chosenLanguage.getString("prescriptionDescription2");
        prescriptionDescription3 = chosenLanguage.getString("prescriptionDescription3");
        prescriptionDescription4 = chosenLanguage.getString("prescriptionDescription4");
        prescriptionDescription5 = chosenLanguage.getString("firstTime");
        hour = chosenLanguage.getString("hour");

        weightChartTitle = chosenLanguage.getString("weightChart");
        weightPointFrameTitle = chosenLanguage.getString("weightPoints");
        newWeightPointFrameTitle = chosenLanguage.getString("newWeightPoint");


        dateLabelText = chosenLanguage.getString("date");
        timeLabelText = chosenLanguage.getString("time");
        noDifferenceInWeight = chosenLanguage.getString("noDifferenceInWeight");
        gainedWeight = chosenLanguage.getString("gainedWeight");
        lostWeight = chosenLanguage.getString("lostWeight");
    }

    ListOfPatients listPatients = new ListOfPatients();

    private ArrayList<Profile> listOfPatients = new ArrayList<>(); // used list of patients
    private int patientNumber = 0; // selected patient number

    public void updateListPatients(){
        listPatients.openFile();
        for(int i = 0; i < listOfPatients.size(); i++){
            String firstName = listOfPatients.get(i).getFirstName();
            String middleName = listOfPatients.get(i).getMiddleName();
            String lastName = listOfPatients.get(i).getLastName();
            int age = listOfPatients.get(i).getAge();
            int length = listOfPatients.get(i).getLength();
            double weight = listOfPatients.get(i).getWeight();
            listPatients.addRecords(firstName, middleName, lastName, age, length, weight);
        }
        listPatients.close();
    }

    public void updateListMedicines(){
        listMedicines.openFile();
        for(int i = 0; i < listOfAllMedicines.size(); i++){
            int id = listOfAllMedicines.get(i).getMedicineID();
            String name = listOfAllMedicines.get(i).getMedicineName();
            String dose = listOfAllMedicines.get(i).getMedicineDose();
            String type = listOfAllMedicines.get(i).getMedicineType();
            String description = listOfAllMedicines.get(i).getMedicineDescription();
            listMedicines.addMedicines(id, name, dose, type, description);
        }
        listMedicines.close();
    }

    PatientFile patientFile = new PatientFile();

    public void updatePatientFile(){
        for(int i = 0; i < listOfPatients.size(); i++) {
            patientFile.openFile(listOfPatients.get(i).getFullName());
            for(int a = 0; a < listOfPatients.get(i).listOfPrescriptions.size(); a++) {
                int id = listOfPatients.get(i).listOfPrescriptions.get(a).getMedicine().getMedicineID();
                int atHour = listOfPatients.get(i).listOfPrescriptions.get(a).getAtHour();
                int atMinute = listOfPatients.get(i).listOfPrescriptions.get(a).getAtMinute();
                int interval = listOfPatients.get(i).listOfPrescriptions.get(a).getInterval();
                int times = listOfPatients.get(i).listOfPrescriptions.get(a).getTimes();
                patientFile.addPrescriptions(id, atHour, atMinute, interval, times);
            }
            patientFile.addSeparationLine();
            for(int a = 0; a < listOfPatients.get(i).listOfWeightPoints.size(); a++) {
                int day = listOfPatients.get(i).listOfWeightPoints.get(a).getDay();
                int month = listOfPatients.get(i).listOfWeightPoints.get(a).getMonth();
                int year = listOfPatients.get(i).listOfWeightPoints.get(a).getYear();
                String time = listOfPatients.get(i).listOfWeightPoints.get(a).getTime();
                Double weight = listOfPatients.get(i).listOfWeightPoints.get(a).getWeight();
                patientFile.addWeightPoints(day, month, year, time, weight);
            }
            patientFile.close();


        }

    }

    ListOfMedicines listMedicines = new ListOfMedicines();

    ArrayList<PatientFile> patientFiles = new ArrayList<>();

    public void setListsAndReadStuff(){
        listPatients.readFile();
        listOfPatients = listPatients.getListOfPatients();
        if (listOfPatients.size() == 0){
            listOfPatients.add(new Profile(chosenLanguage.getString("new"), Integer.toString(1),
                    chosenLanguage.getString("patient"), 0, 0, 0));
        }
        listMedicines.readFile();
        listOfAllMedicines = listMedicines.getListOfMedicines();
        for (int i = 0; i < listOfAllMedicines.size(); i++) {
            listOfAllMedicines.get(i).setMedicineID(i);
        }
        for(int i = 0; i < listOfPatients.size(); i++) {
            patientFiles.add(new PatientFile());
            patientFiles.get(i).readFile(listOfAllMedicines, listOfPatients.get(i).getFullName());
            listOfPatients.get(i).setListOfPrescriptions(patientFiles.get(i).getListOfPrescriptions());
            listOfPatients.get(i).setListOfWeightPoints(patientFiles.get(i).getListOfWeightPoints());
        }

        setAlarms();
    }


    public void firstName() {
        // Voornaam
        firstNameLabel.setBackground(frameBackground);
        firstNameLabel.setText(firstNameLabelText);
        firstNameLabel.setBounds(10, 10, 100, 20);
        firstNameTextField.setText(listOfPatients.get(patientNumber).getFirstName());
        firstNameTextField.setBackground(textFieldColor);
        firstNameTextField.setLocation(105, 10);
        firstNameTextField.setBorder(null);
        firstNameTextField.setSize(95, 20);
        firstNameTextField.setEditable(false);
    }
    public void checkForMiddleName() {
        if (listOfPatients.get(patientNumber).getMiddleName().equals("")) {
            lastNameTextField.setText(listOfPatients.get(patientNumber).getLastName());
        } else {
            lastNameTextField.setText(listOfPatients.get(patientNumber).getLastName() + ", " + listOfPatients.get(patientNumber).getMiddleName());
        }
    }
    public void lastName() {
        // Achternaam
        lastNameLabel.setText(lastNameLabelText);
        lastNameLabel.setBounds(10, 30, 100, 20);
        lastNameLabel.setBackground(frameBackground);
        checkForMiddleName();
        lastNameTextField.setBackground(textFieldColor);
        lastNameTextField.setBorder(null);
        lastNameTextField.setLocation(105, 30);
        lastNameTextField.setSize(95, 20);
        lastNameTextField.setEditable(false);
    }
    public void age() {
        ageLabel.setText(ageLabelText);
        ageLabel.setBackground(frameBackground);
        ageLabel.setBounds(10, 50, 100, 20);
        ageTextField.setValue(listOfPatients.get(patientNumber).getAge());
        ageTextField.setCaretPosition(ageTextField.getText().length());
        ageTextField.setBorder(null);
        ageTextField.setBackground(textFieldColor);
        ageTextField.setLocation(105, 50);
        ageTextField.setSize(95, 20);
        ageTextField.setEditable(false);
    }
    public void length() {
        lengthLabel.setText(lengthLabelText);
        lengthLabel.setBackground(frameBackground);
        lengthLabel.setBounds(10, 70, 100, 20);
        lengthTextField.setValue(listOfPatients.get(patientNumber).getLength());
        lengthTextField.setBorder(null);
        lengthTextField.setBackground(textFieldColor);
        lengthTextField.setLocation(105, 70);
        lengthTextField.setSize(95, 20);
        lengthTextField.setEditable(false);
    }
    private DecimalFormat numberFormat = new DecimalFormat(".0");
    public void weight() {
        weightLabel.setText(weightLabelText);
        weightLabel.setBackground(frameBackground);
        weightLabel.setBounds(10, 90, 100, 20);
        weightTextField.setValue(numberFormat.format(listOfPatients.get(patientNumber).getWeight()).replace(",", "."));
        weightTextField.setBorder(null);
        weightTextField.setBackground(textFieldColor);
        weightTextField.setLocation(105, 90);
        weightTextField.setSize(95, 20);
        weightTextField.setEditable(false);
        if (weightTextField.getValue().equals(".0")) {
            weightTextField.setValue("0");
        }
    }
    public void bmi() {
        bmiLabel.setText(bmiLabelText);
        bmiLabel.setBackground(frameBackground);
        bmiLabel.setBounds(10, 110, 100, 20);
        bmiTextField.setValue(numberFormat.format(listOfPatients.get(patientNumber).getBodyMassIndex()).replace(",", "."));
        bmiTextField.setBorder(null);
        bmiTextField.setBackground(textFieldColor);
        bmiTextField.setLocation(105, 110);
        bmiTextField.setSize(95, 20);
        bmiTextField.setEditable(false);

        if (bmiTextField.getValue().equals("NaN") || bmiTextField.getValue().equals("∞") || bmiTextField.getValue().equals(".0")) {
            bmiTextField.setValue(bmiUnknown);
        }
    }

    public void data() {
        firstName();
        lastName();
        age();
        length();
        weight();
        bmi();
    }

    public void editButton() {
        editButton.setText(editButtonEditText);
        editButton.setBounds(10, 142, 85, 40);
        editButton.setBackground(buttonColor);
        editButton.setBorder(null);
        ageTextField.setCaretPosition(ageTextField.getText().length());
        editButton.addActionListener(new ActionListener() {
            // Edit button
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editButton.getText().equals(editButtonEditText)) {
                    firstNameTextField.setEditable(true);
                    lastNameTextField.setEditable(true);
                    ageTextField.setEditable(true);


                    lengthTextField.setEditable(true);
                    weightTextField.setEditable(true);
                    editButton.setText(editButtonSaveText);
                    firstNameTextField.setBackground(textFieldEditableColor);
                    lastNameTextField.setBackground(textFieldEditableColor);
                    ageTextField.setBackground(textFieldEditableColor);
                    lengthTextField.setBackground(textFieldEditableColor);
                    weightTextField.setBackground(textFieldEditableColor);
                    mainFrame.setTitle("work");
                }
                // Save button
                else if (editButton.getText().equals(editButtonSaveText)) {
                    makeTextFieldsUneditable();

                    // Save data
                    listOfPatients.get(patientNumber).setFirstName(firstNameTextField.getText());

                    try {
                        String[] fullName = lastNameTextField.getText().split(", ");
                        listOfPatients.get(patientNumber).setLastName(fullName[0]);
                        listOfPatients.get(patientNumber).setMiddleName(fullName[1]);
                    } catch (Exception noMiddleName) {
                        listOfPatients.get(patientNumber).setLastName(lastNameTextField.getText());
                        listOfPatients.get(patientNumber).setMiddleName("");
                    }

                    listOfPatients.get(patientNumber).setAge(Integer.parseInt(ageTextField.getText()));

                    listOfPatients.get(patientNumber).setLength(Integer.parseInt(lengthTextField.getText()));

                    try {
                        listOfPatients.get(patientNumber).setWeight(Double.parseDouble(weightTextField.getText()));
                    } catch (Exception a) {
                        weight();
                    }

                    data();
                    refreshActiveFramesWhenPatientChanges();
                    updateListPatients();

                }
            }
        });
    }
    public void checkPatientNumber() {
        if (patientNumber > 0) {
            previousPatientButton.setEnabled(true);
            previousPatientButton.setBackground(buttonColor);

        } else {
            previousPatientButton.setEnabled(false);
            previousPatientButton.setBackground(buttonDisabledColor);
        }
        if (patientNumber < (listOfPatients.size() - 1)) {
            nextPatientButton.setEnabled(true);
            nextPatientButton.setBackground(buttonColor);
        } else {
            nextPatientButton.setEnabled(false);
            nextPatientButton.setBackground(buttonDisabledColor);
        }
        if (listOfPatients.size() == 1) {
            previousPatientButton.setEnabled(false);
            previousPatientButton.setBackground(buttonDisabledColor);
            nextPatientButton.setEnabled(false);
            nextPatientButton.setBackground(buttonDisabledColor);
        }
    }
    public void makeTextFieldsUneditable() {
        mainFrame.setTitle("zork");
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        ageTextField.setEditable(false);
        lengthTextField.setEditable(false);
        weightTextField.setEditable(false);
        editButton.setText(editButtonEditText);
    }

    public void previousPatientButton() {
        previousPatientButton.setBounds(105, 142, 32, 20);
        previousPatientButton.setBorder(null);
        checkPatientNumber();
        previousPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                patientNumber = patientNumber - 1;
                data();
                checkPatientNumber();
                makeTextFieldsUneditable();
                refreshActiveFramesWhenPatientChanges();
            }
        });
    }

    // Knop volgende patiënt
    public void nextPatientButton() {
        nextPatientButton.setBounds(137, 142, 32, 20);
        nextPatientButton.setBorder(null);
        checkPatientNumber();
        nextPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                patientNumber = patientNumber + 1;
                data();
                checkPatientNumber();
                makeTextFieldsUneditable();

                refreshActiveFramesWhenPatientChanges();
            }
        });
    }

    public void newPatientButton() {
        newPatientButton.setBounds(169, 142, 31, 20);
        newPatientButton.setBackground(buttonColor);
        newPatientButton.setBorder(null);
        newPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                patientNumber = listOfPatients.size();
                listOfPatients.add(new Profile(chosenLanguage.getString("new"), Integer.toString(patientNumber + 1),
                        chosenLanguage.getString("patient"), 0, 0, 0));
                nextPatientButton.setEnabled(false);
                nextPatientButton.setBackground(buttonDisabledColor);
                previousPatientButton.setEnabled(true);
                previousPatientButton.setBackground(buttonColor);
                makeTextFieldsUneditable();
                data();
                refreshActiveFramesWhenPatientChanges();
            }
        });
    }

    public void refreshActiveFramesWhenPatientChanges(){
        if (prescriptionFrame.isVisible()){
            showPrescriptionFrame();
        }
        if (lineChart.isVisible()){
            showLineChart();
        }
        if(weightPointFrame.isVisible()){
            showWeightPointsFrame();
        }
        if(prescriptMedicineToPatientFrame.isVisible()){
            showPrescriptMedicineToPatientFrame();
        }
        weightPointDetailFrame.dispose();
    }

    public void refreshActiveFramesWhenLanguageChanges(){
        data();
        makeTextFieldsUneditable();
        if(medicineFrame.isVisible()){
            showMedicineFrame();
        }
        if(medicineDetailsFrame.isVisible()){
            showMedicineDetailsFrame();
        }
        if(newWeightPointFrame.isVisible()){
            showNewWeightPointFrame();
        }
        if (prescriptionFrame.isVisible()){
            showPrescriptionFrame();
        }
        if (lineChart.isVisible()){
            showLineChart();
        }
        if(weightPointFrame.isVisible()){
            showWeightPointsFrame();
        }
        if(prescriptMedicineToPatientFrame.isVisible()){
            showPrescriptMedicineToPatientFrame();
        }
        if(newMedicineFrame.isVisible()){
            showNewMedicineFrame();
        }
    }

    private int frameSize = 225;

    JButton changeLanguageButton = new JButton(languageIcon);

    public void startZorgApp(){
        setLanguage();
        setListsAndReadStuff();
        showMainFrame();
        activateButtons();
    }
    public void showMainFrame() {
        setLanguage();
        // Frame Instellingen
        mainFrame.setLayout(null);
        mainFrame.getContentPane().setBackground(frameBackground);
        mainFrame.setLocation(450, 200);
        mainFrame.setSize(frameSize, frameSize);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Voeg componenten toe aan frame
        mainFrame.add(editButton);
        editButton();
        mainFrame.add(nextPatientButton);
        nextPatientButton();
        mainFrame.add(previousPatientButton);
        previousPatientButton();
        mainFrame.add(newPatientButton);
        newPatientButton();
        mainFrame.add(medicineButton);
        medicineButton();
        mainFrame.add(weightPointsButton);
        weightPointsButton();
        selectLanguageButton();
        mainFrame.add(firstNameTextField);
        mainFrame.add(firstNameLabel);
        mainFrame.add(lastNameTextField);
        mainFrame.add(lastNameLabel);
        mainFrame.add(ageTextField);
        mainFrame.add(ageLabel);
        mainFrame.add(lengthLabel);
        mainFrame.add(lengthTextField);
        mainFrame.add(weightLabel);
        mainFrame.add(weightTextField);
        mainFrame.add(bmiLabel);
        mainFrame.add(bmiTextField);
        checkPatientNumber();
        data();
        mainFrame.setVisible(true);
    }

    public void activateButtons(){
        activateDeleteWeightPointButton();
        activateSaveNewWeightPoint();
        activateDeletePrescriptionButton();
        activatePrescriptMedicineToPatientButton();
        activateAddWeightPointButton();
        activateSaveNewPrescriptionButton();
        activateAddMedicineButton();
        activateSaveMedicineChange();
        activateDeleteMedicineButton();
        activateLanguageButtons();
        activateSaveNewMedicineButton();
    }

    Icon englishFlag = new ImageIcon("src/App/images/flags/english.png");
    Icon dutchFlag = new ImageIcon("src/App/images/flags/dutch.png");
    Icon germanFlag = new ImageIcon("src/App/images/flags/german.png");

    JTextField welcome = new JTextField();
    JButton englishButton = new JButton("English language", englishFlag);
    JButton dutchButton = new JButton("Nederlandse taal", dutchFlag);
    JButton germanButton = new JButton("Deutsche Sprache", germanFlag);

    public void selectLanguageButton(){
        changeLanguageButton.setBounds(169, 162, 31, 20);
        changeLanguageButton.setBorder(null);
        changeLanguageButton.setBackground(buttonColor);
        changeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showChangeLanguageFrame();
                setLanguage();
                refreshActiveFramesWhenLanguageChanges();
            }
        });
        mainFrame.add(changeLanguageButton);

    }

    public void showChangeLanguageFrame(){
        changeLanguageFrame.setSize(frameSize, frameSize - 30);
        changeLanguageFrame.setLocation(mainFrame.getX(), mainFrame.getY() + frameSize - 8);
        changeLanguageFrame.getContentPane().setBackground(frameBackground);
        changeLanguageFrame.setResizable(false);
        changeLanguageFrame.setLayout(null);
        changeLanguageFrame.setVisible(true);


        welcome.setBounds(10, 130, 190, 20);
        welcome.setHorizontalAlignment(JTextField.CENTER);
        welcome.setEditable(false);
        welcome.setBorder(null);
        welcome.setBackground(frameBackground);
        changeLanguageFrame.add(welcome);


        englishButton.setIconTextGap(10);
        englishButton.setBackground(buttonColor);
        englishButton.setHorizontalAlignment(JButton.CENTER);
        englishButton.setBorder(null);
        englishButton.setBounds(10, 10, 190, 30);

        changeLanguageFrame.add(englishButton);


        dutchButton.setBorder(null);
        dutchButton.setIconTextGap(10);
        dutchButton.setHorizontalAlignment(0);
        dutchButton.setBackground(buttonColor);
        dutchButton.setBounds(10, 50, 190, 30);

        changeLanguageFrame.add(dutchButton);

        germanButton.setBackground(buttonColor);
        germanButton.setIconTextGap(10);
        germanButton.setHorizontalAlignment(0);
        germanButton.setBorder(null);
        germanButton.setBounds(10, 90, 190, 30);

        changeLanguageFrame.add(germanButton);
    }

    public void activateLanguageButtons(){
        englishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenLanguage = ResourceBundle.getBundle("App/bundle", english);
                setLanguage();
                refreshActiveFramesWhenLanguageChanges();
                welcome.setText(chosenLanguage.getString("welcome"));
            }
        });
        dutchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenLanguage = ResourceBundle.getBundle("App/bundle", dutch);
                setLanguage();
                refreshActiveFramesWhenLanguageChanges();
                welcome.setText(chosenLanguage.getString("welcome"));
            }
        });
        germanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chosenLanguage = ResourceBundle.getBundle("App/bundle", german);
                setLanguage();
                refreshActiveFramesWhenLanguageChanges();
                welcome.setText(chosenLanguage.getString("welcome"));
            }
        });
    }

    // Swing components for medicine frame
    private JDialog prescriptionFrame = new JDialog(mainFrame);
    private JDialog medicineFrame = new JDialog(prescriptionFrame);
    private JDialog medicineDetailsFrame = new JDialog(medicineFrame);

    private int medicineNumber; // selected medicine
    ArrayList<Medicine> listOfAllMedicines = new ArrayList<>();

    // Medicine frame
    public void medicineButton() {
        medicineButton.setBounds(105, 162, 32, 20);
        medicineButton.setBackground(buttonColor);
        medicineButton.setBorder(null);
        medicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrescriptionFrame();
                showMedicineFrame();

            }
        });
    }
    public void showMedicineFrame() {
        medicineFrame.getContentPane().removeAll();
        medicineFrame.setLayout(null);
        medicineFrame.getContentPane().setBackground(frameBackground);
        medicineFrame.setSize(frameSize, frameSize);
        medicineFrame.setResizable(false);
        medicineFrame.setLocation(mainFrame.getX() - 420, mainFrame.getY());
        medicineFrame.setLayout(null);
        medicineFrame.setTitle(medicineFrameTitle);

        ArrayList<JButton> medicineNameButtonList = new ArrayList<>();

        newMedicineButton.setText("+");
        newMedicineButton.setBounds(170, 165, 30, 15);
        newMedicineButton.setBackground(buttonColor);
        newMedicineButton.setBorder(null);
        medicineFrame.add(newMedicineButton);
        medicineFrame.setVisible(true);
        int deletedMedicinesInList = 0;
        int numberInVisualList;
        // Create buttons of medicines
        for(int i = 0; i < listOfAllMedicines.size(); i++){

            // Deze medicijnen zijn verwijderd uit de lijst
            if(listOfAllMedicines.get(i).getMedicineName().contains(deleteMedicine)){
                deletedMedicinesInList = deletedMedicinesInList + 1;

            }
            numberInVisualList = i - deletedMedicinesInList;

            if(!listOfAllMedicines.get(i).getMedicineName().contains(deleteMedicine)) {
                medicineNameButtonList.add(new JButton(listOfAllMedicines.get(i).getMedicineName()));
                medicineNameButtonList.get(numberInVisualList).setSize(90, 19);
                medicineNameButtonList.get(numberInVisualList).setBackground(buttonColor);
                medicineNameButtonList.get(numberInVisualList).setBorder(null);
                listOfAllMedicines.get(i).setMedicineID(i);
                // Koppel knop aan het juiste medicinijn
                medicineNameButtonList.get(numberInVisualList).setName(Integer.toString(i));
                if (numberInVisualList < 8) {
                    medicineNameButtonList.get(numberInVisualList).setLocation(10, 10 + (19 * numberInVisualList));
                } else {
                    medicineNameButtonList.get(numberInVisualList).setLocation(110, 10 + (19 * (numberInVisualList - 8)));
                }

                medicineNameButtonList.get(numberInVisualList).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        medicineNumber = Integer.parseInt(((JButton) e.getSource()).getName());
                        showMedicineDetailsFrame();
                        if (prescriptMedicineToPatientFrame.isVisible()) {
                            showPrescriptMedicineToPatientFrame();
                        }
                    }
                });
                medicineFrame.add(medicineNameButtonList.get(numberInVisualList));
            }
        }
    }

    private JButton newMedicineButton = new JButton();
    private JDialog newMedicineFrame = new JDialog();
    private JLabel newMedicineLabel = new JLabel();
    private JTextField newMedicineText = new JTextField();
    private JButton saveNewMedicineButton = new JButton();

    public void showNewMedicineFrame(){
        newMedicineFrame.setBounds(medicineFrame.getX(), medicineFrame.getY() - 122, frameSize , 130);
        newMedicineFrame.getContentPane().setBackground(frameBackground);
        newMedicineFrame.setLayout(null);
        newMedicineFrame.setResizable(false);

        newMedicineLabel.setBounds(10, 5, 190, 20);
        newMedicineLabel.setHorizontalAlignment(JTextField.CENTER);
        newMedicineLabel.setText(chosenLanguage.getString("newMedicine"));
        newMedicineFrame.add(newMedicineLabel);

        newMedicineText.setBounds(10, 30, 190, 20);
        newMedicineText.setBackground(textFieldEditableColor);
        newMedicineText.setBorder(null);
        newMedicineText.setHorizontalAlignment(JTextField.CENTER);
        newMedicineText.setText("");
        newMedicineFrame.add(newMedicineText);

        saveNewMedicineButton.setBounds(10, 60, 190, 20);
        saveNewMedicineButton.setText(chosenLanguage.getString("save"));
        saveNewMedicineButton.setBackground(buttonColor);
        saveNewMedicineButton.setBorder(null);
        newMedicineFrame.add(saveNewMedicineButton);


        newMedicineFrame.setVisible(true);
    }

    public void activateSaveNewMedicineButton(){
        saveNewMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicineName = newMedicineText.getText();
                String dose = chosenLanguage.getString("medicineDose");
                String type = chosenLanguage.getString("medicineType");
                String description = chosenLanguage.getString("medicineDescription");
                listOfAllMedicines.add(new Medicine(medicineName, description, dose, type));
                medicineNumber = listOfAllMedicines.size() - 1;
                listOfAllMedicines.get(medicineNumber).setMedicineID(medicineNumber);
                newMedicineFrame.dispose();
                showMedicineFrame();
                updateListMedicines();
            }
        });
    }

    public void activateAddMedicineButton(){
        newMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewMedicineFrame();
            }
        });
    }

    JButton saveMedicineChangesButton = new JButton();
    public void activateSaveMedicineChange(){
        saveMedicineChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newType = medicineTypeText.getText();
                listOfAllMedicines.get(medicineNumber).setMedicineType(newType);
                String newDose = medicineDoseText.getText();
                listOfAllMedicines.get(medicineNumber).setMedicineDose(newDose);
                String newDescription = medicineDescriptionText.getText();
                listOfAllMedicines.get(medicineNumber).setMedicineDescription(newDescription);
                if(prescriptionDetailsFrame.isVisible()) {
                    showFramePrescriptionDetails();
                }
                updateListMedicines();


            }
        });
    }

    // Medicine details frame
    private JTextArea medicineDescriptionText = new JTextArea();
    private JTextField medicineDoseText = new JTextField();
    private JTextField medicineTypeText = new JTextField();
    private JScrollPane medicineScroll;
    private JButton prescriptMedicineToPatientButton = new JButton("+");

    public void showMedicineDetailsFrame() {
        medicineDetailsFrame.getContentPane().removeAll();
        medicineDetailsFrame.setLayout(null);
        medicineDetailsFrame.setTitle(listOfAllMedicines.get(medicineNumber).getMedicineName().toLowerCase());
        medicineDetailsFrame.getContentPane().setBackground(frameBackground);
        medicineDetailsFrame.setSize(frameSize, frameSize - 30);
        medicineDetailsFrame.setResizable(false);
        medicineDetailsFrame.setLocation(medicineFrame.getX(), medicineFrame.getY() + frameSize - 8);
        medicineDetailsFrame.setLayout(null);

        prescriptMedicineToPatientButton.setBounds(170, 137, 30, 15);
        prescriptMedicineToPatientButton.setBackground(buttonColor);
        prescriptMedicineToPatientButton.setBorder(null);
        medicineDetailsFrame.add(prescriptMedicineToPatientButton);

        deleteMedicineButton.setBounds(130, 137, 30, 15);
        deleteMedicineButton.setBorder(null);
        deleteMedicineButton.setBackground(buttonColor);
        medicineDetailsFrame.add(deleteMedicineButton);

        saveMedicineChangesButton.setBounds(10, 137, 90, 15);
        saveMedicineChangesButton.setBackground(buttonColor);
        saveMedicineChangesButton.setBorder(null);
        saveMedicineChangesButton.setText(editButtonSaveText);
        medicineDetailsFrame.add(saveMedicineChangesButton);

        medicineDescriptionText.setBounds(10, 40, 190, 90);
        medicineDescriptionText.setBackground(textFieldEditableColor);
        medicineDescriptionText.setBorder(null);
        medicineDescriptionText.setText(listOfAllMedicines.get(medicineNumber).getMedicineDescription());
        medicineDescriptionText.setLineWrap(true);
        medicineDescriptionText.setWrapStyleWord(true);
        medicineDescriptionText.setCaretPosition(0);
        // Scroller
        medicineScroll = new JScrollPane(medicineDescriptionText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        medicineScroll.setBounds(10, 40, 190, 90);
        medicineScroll.setBorder(null);
        medicineScroll.getVerticalScrollBar().setBackground(medicineFrameBackground);
        medicineDetailsFrame.add(medicineScroll);

        medicineDoseText.setBounds(10, 10, 50, 20);
        medicineDoseText.setBorder(null);
        medicineDoseText.setBackground(textFieldEditableColor);
        medicineDoseText.setText(listOfAllMedicines.get(medicineNumber).getMedicineDose());
        medicineDetailsFrame.add(medicineDoseText);

        medicineTypeText.setBounds(70, 10, 130, 20);
        medicineTypeText.setBorder(null);
        medicineTypeText.setBackground(textFieldEditableColor);
        medicineTypeText.setText(listOfAllMedicines.get(medicineNumber).getMedicineType());
        medicineDetailsFrame.add(medicineTypeText);
        medicineDetailsFrame.setVisible(true);
    }

    //This String is added to a deleted medicine
    String deleteMedicine = "(DELETED)";
    JButton deleteMedicineButton = new JButton(deleteIcon);

    public void activateDeleteMedicineButton(){
        deleteMedicineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String medicineBeforeDeleting = listOfAllMedicines.get(medicineNumber).getMedicineName();
                listOfAllMedicines.get(medicineNumber).setMedicineName(medicineBeforeDeleting + deleteMedicine);
                medicineDetailsFrame.dispose();
                showMedicineFrame();
                updateListMedicines();
            }
        });
    }

    private JDialog prescriptMedicineToPatientFrame = new JDialog(prescriptionFrame);
    private JLabel patientNameLabel = new JLabel();
    private JTextField patientNameText = new JTextField();
    private JLabel medicineNameLabel = new JLabel();
    private JTextField medicineNameText = new JTextField();
    private JLabel prescriptionTimesLabel = new JLabel();
    private JTextField prescriptionTimesText = new JTextField();
    private JLabel prescriptionIntervalLabel = new JLabel();
    private JTextField prescriptionIntervalText = new JTextField();
    private JLabel prescriptionFirstIntakeLabel = new JLabel();
    private JTextField prescriptionFirstIntakeText = new JTextField();
    private JButton saveNewPrescription = new JButton();

    public void showPrescriptMedicineToPatientFrame(){
        prescriptMedicineToPatientFrame.setTitle(prescriptMedicineToPatientFrameTitle);
        prescriptMedicineToPatientFrame.setSize(frameSize, frameSize - 30);
        prescriptMedicineToPatientFrame.setResizable(false);
        prescriptMedicineToPatientFrame.setLayout(null);
        prescriptMedicineToPatientFrame.getContentPane().setBackground(frameBackground);
        prescriptMedicineToPatientFrame.setLocation(prescriptionFrame.getX(), prescriptionFrame.getY() + frameSize - 8);

        // patient name
        patientNameLabel.setBounds(10,10,100,20);
        patientNameLabel.setText(patientNameLabelText);
        patientNameText.setBounds(110, 10, 90, 20);
        patientNameText.setBorder(null);
        patientNameText.setText(listOfPatients.get(patientNumber).getFullName());
        patientNameText.setCaretPosition(0);
        patientNameText.setEditable(false);
        patientNameText.setBackground(textFieldColor);

        // Medicine
        medicineNameLabel.setBounds(10, 30, 100,20);
        medicineNameLabel.setText(medicineNameLabelText);
        medicineNameText.setBorder(null);
        medicineNameText.setBounds(110, 30, 90 ,20);
        medicineNameText.setText(listOfAllMedicines.get(medicineNumber).getMedicineName());
        medicineNameText.setEditable(false);
        medicineNameText.setBackground(textFieldColor);

        // First time intake
        prescriptionFirstIntakeLabel.setBounds(10, 50, 100, 20);
        prescriptionFirstIntakeLabel.setText(prescriptionFirstIntakeLabelText);
        prescriptionFirstIntakeText.setBounds(110, 50, 90, 20);
        prescriptionFirstIntakeText.setBackground(textFieldEditableColor);
        prescriptionFirstIntakeText.setBorder(null);
        prescriptionFirstIntakeText.setText("");

        prescriptMedicineToPatientFrame.add(prescriptionFirstIntakeLabel);
        prescriptMedicineToPatientFrame.add(prescriptionFirstIntakeText);

        prescriptionTimesLabel.setBounds(10, 70, 100, 20);
        prescriptionTimesLabel.setText(prescriptionTimesLabelText);
        prescriptionTimesText.setBounds(110, 70, 90, 20);
        prescriptionTimesText.setBackground(textFieldEditableColor);
        prescriptionTimesText.setBorder(null);
        prescriptionTimesText.setText("");

        prescriptMedicineToPatientFrame.add(prescriptionTimesLabel);
        prescriptMedicineToPatientFrame.add(prescriptionTimesText);

        prescriptionIntervalLabel.setBounds(10, 90, 100, 20);
        prescriptionIntervalLabel.setText(prescriptionIntervalLabelText);
        prescriptionIntervalText.setBounds(110, 90, 90, 20);
        prescriptionIntervalText.setBackground(textFieldEditableColor);
        prescriptionIntervalText.setBorder(null);
        prescriptionIntervalText.setText("");

        prescriptMedicineToPatientFrame.add(prescriptionIntervalLabel);
        prescriptMedicineToPatientFrame.add(prescriptionIntervalText);

        saveNewPrescription.setBounds(110, 125, 90, 20);
        saveNewPrescription.setText(editButtonSaveText);
        saveNewPrescription.setBackground(buttonColor);
        saveNewPrescription.setBorder(null);


        prescriptMedicineToPatientFrame.add(patientNameLabel);
        prescriptMedicineToPatientFrame.add(patientNameText);
        prescriptMedicineToPatientFrame.add(medicineNameLabel);
        prescriptMedicineToPatientFrame.add(medicineNameText);
        prescriptMedicineToPatientFrame.add(saveNewPrescription);
        prescriptMedicineToPatientFrame.setVisible(true);
    }

    public void activateSaveNewPrescriptionButton(){
        saveNewPrescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medicine medicine = listOfAllMedicines.get(medicineNumber);
                try {
                    String[] hourAndMinutes = prescriptionFirstIntakeText.getText().split(":");
                    int intakeHour = Integer.parseInt(hourAndMinutes[0]);
                    int intakeMinute = Integer.parseInt(hourAndMinutes[1]);
                    int times = Integer.parseInt(prescriptionTimesText.getText());
                    int interval = Integer.parseInt(prescriptionIntervalText.getText());
                    listOfPatients.get(patientNumber).listOfPrescriptions.add(new Prescription(medicine, intakeHour, intakeMinute, interval, times));
                    prescriptionNumber = listOfPatients.get(patientNumber).listOfPrescriptions.size() - 1;
                    showFramePrescriptionDetails();
                    showPrescriptionFrame();
                    prescriptMedicineToPatientFrame.dispose();
                } catch (Exception notAValidPrescription) {
                    prescriptMedicineToPatientFrame.setTitle("voer geldige waardes in");
                }
                updatePatientFile();
            }
        }
        );
    }

    public void activatePrescriptMedicineToPatientButton(){
        prescriptMedicineToPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrescriptMedicineToPatientFrame();
            }
        });
    }

    int prescriptionNumber; // Selected prescription

    int startThread = 0; // So that thread starts only once
    ArrayList<JTextField> prescriptionTimeTillIntake = new ArrayList<>();

    // Prescription frame
    public void  showPrescriptionFrame() {
        startThread++;
        prescriptionFrame.getContentPane().removeAll();
        prescriptionFrame.setLayout(null);
        prescriptionFrame.getContentPane().setBackground(frameBackground);
        prescriptionFrame.setSize(frameSize, frameSize);
        prescriptionFrame.setResizable(false);
        prescriptionFrame.setLocation(mainFrame.getX() - 210, mainFrame.getY());
        prescriptionFrame.setLayout(null);
        prescriptionFrame.setTitle(listOfPatients.get(patientNumber).getFullName());

        ArrayList<JButton> prescriptionButtonList = new ArrayList<>();

        if(listOfPatients.get(patientNumber).listOfPrescriptions.size() > 0) {
            // Create a button and text field with timer for each prescription
            for (int i = 0; i < listOfPatients.get(patientNumber).listOfPrescriptions.size() && i < 9; i++) {
                prescriptionButtonList.add(new JButton(listOfPatients.get(patientNumber).listOfPrescriptions.
                        get(i).getMedicine().getMedicineName()));
                prescriptionButtonList.get(i).setSize(90, 19);
                prescriptionButtonList.get(i).setBackground(buttonColor);
                prescriptionButtonList.get(i).setBorder(null);
                prescriptionButtonList.get(i).setLocation(10, 10 + (19 * i));
                prescriptionButtonList.get(i).setName(Integer.toString(i));
                prescriptionButtonList.get(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        prescriptionNumber = Integer.parseInt(((JButton) e.getSource()).getName());
                        showFramePrescriptionDetails();
                        prescriptionDetailsFrame.setVisible(true);
                    }
                });
                prescriptionTimeTillIntake.add(new JTextField());
                prescriptionTimeTillIntake.get(i).setSize(90, 19);
                prescriptionTimeTillIntake.get(i).setBackground(buttonColor);
                prescriptionTimeTillIntake.get(i).setBorder(null);
                prescriptionTimeTillIntake.get(i).setLocation(110, 10 + (19 * i));
                prescriptionTimeTillIntake.get(i).setBackground(timerColor);
                prescriptionTimeTillIntake.get(i).setForeground(timeTextColor);

                prescriptionFrame.add(prescriptionButtonList.get(i));
                prescriptionFrame.add(prescriptionTimeTillIntake.get(i));
            }
        }
        if(startThread == 1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if(listOfPatients.get(patientNumber).listOfPrescriptions.size() != 0) {
                            for (int i = 0; i < listOfPatients.get(patientNumber).listOfPrescriptions.size() && i < 8; i++) {
                                String timeTillAlarm = listOfPatients.get(patientNumber).listOfPrescriptions.get(i).getTimeTillAlarm();

                                if (!listOfPatients.get(patientNumber).listOfPrescriptions.get(i).isPrescriptionTaken()) {
                                    prescriptionTimeTillIntake.get(i).setText(chosenLanguage.getString("takeMedicine"));
                                } else {
                                    prescriptionTimeTillIntake.get(i).setText(chosenLanguage.getString("timeTillAlarm") + " " + timeTillAlarm);

                                }
                                prescriptionTimeTillIntake.get(i).setHorizontalAlignment(JTextField.CENTER);

                            }
                        }
                        try {
                            sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
            }).start();
        }
                    prescriptionFrame.setVisible(true);
    }

    private JDialog prescriptionDetailsFrame = new JDialog(prescriptionFrame);
    private JTextArea prescriptionDescriptionText = new JTextArea();
    private JTextField prescriptionDoseText = new JTextField();
    private JTextField prescriptionTypeText = new JTextField();
    private JButton deletePrescriptionButton = new JButton(deleteIcon);

    public void activateDeletePrescriptionButton(){
        deletePrescriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listOfPatients.get(patientNumber).listOfPrescriptions.remove(prescriptionNumber);
                prescriptionDetailsFrame.dispose();
                showPrescriptionFrame();
                updatePatientFile();
            }
        });
    }

    // Prescription details frame
    public void showFramePrescriptionDetails() {
        prescriptionDetailsFrame.getContentPane().removeAll();
        prescriptionDetailsFrame.setLayout(null);
        prescriptionDetailsFrame.setTitle(listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber).
                getMedicine().getMedicineName().toLowerCase() + " (" + listOfPatients.get(patientNumber).getFullName() + ")");
        prescriptionDetailsFrame.getContentPane().setBackground(frameBackground);
        prescriptionDetailsFrame.setSize(frameSize, frameSize - 30);
        prescriptionDetailsFrame.setResizable(false);
        prescriptionDetailsFrame.setLocation(prescriptionFrame.getX(), prescriptionFrame.getY() + frameSize - 8);
        prescriptionDetailsFrame.setLayout(null);

        deletePrescriptionButton.setBounds(170, 137, 30, 15);
        deletePrescriptionButton.setBackground(buttonColor);
        deletePrescriptionButton.setBorder(null);
        prescriptionDetailsFrame.add(deletePrescriptionButton);

        int times = listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber).getTimes();
        int interval = listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber).getInterval();
        String atTime = listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber).getAtTime();

        prescriptionDescriptionText.setBounds(10, 40, 190, 90);
        prescriptionDescriptionText.setBackground(textFieldColor);
        prescriptionDescriptionText.setEditable(false);
        prescriptionDescriptionText.setBorder(null);

        if (times > 1) {
            prescriptionDescriptionText.setText(prescriptionDescription1 + " " + times + " " + prescriptionDescription2
                    + " " + prescriptionDescription3 + " " + interval + " " + prescriptionDescription4 + ". " +
                    prescriptionDescription5  + " " + atTime + " " + hour +  ". ");
        } else {
            prescriptionDescriptionText.setText(prescriptionDescription1 + " " + times + " " + prescriptionDescription2
                    + ". " + prescriptionDescription5  + " " + atTime + " " + hour + ". ");
        }

        prescriptionDescriptionText.append(listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber)
                .getMedicine().getMedicineDescription());
        prescriptionDescriptionText.setLineWrap(true);
        prescriptionDescriptionText.setWrapStyleWord(true);
        prescriptionDescriptionText.setCaretPosition(0);
        JScrollPane prescriptionScroll = new JScrollPane(prescriptionDescriptionText, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        prescriptionScroll.setBounds(10, 40, 190, 90);
        prescriptionScroll.setBorder(null);
        prescriptionScroll.getVerticalScrollBar().setBackground(medicineFrameBackground);
        prescriptionDetailsFrame.add(prescriptionScroll);

        prescriptionDoseText.setBounds(10, 10, 50, 20);
        prescriptionDoseText.setBorder(null);
        prescriptionDoseText.setBackground(textFieldColor);
        prescriptionDoseText.setEditable(false);
        prescriptionDoseText.setText(listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber)
                .getMedicine().getMedicineDose());
        if(prescriptionDoseText.getText().equals("")){
            prescriptionDoseText.setText("dosis");
        }
        prescriptionDetailsFrame.add(prescriptionDoseText);

        prescriptionTypeText.setBounds(70, 10, 130, 20);
        prescriptionTypeText.setBorder(null);
        prescriptionTypeText.setBackground(textFieldColor);
        prescriptionTypeText.setEditable(false);
        prescriptionTypeText.setText(listOfPatients.get(patientNumber).listOfPrescriptions.get(prescriptionNumber)
                .getMedicine().getMedicineType());
        prescriptionDetailsFrame.add(prescriptionTypeText);
        prescriptionDetailsFrame.setVisible(true);
    }

    JButton weightPointsButton = new JButton(weightPointIcon);

    // Weight points
    public void weightPointsButton(){
        weightPointsButton.setBounds(137, 162, 32, 20);
        weightPointsButton.setBackground(buttonColor);
        weightPointsButton.setBorder(null);
        weightPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLineChart();
                showWeightPointsFrame();
            }
        });
    }

    Color gridLine = new Color(0xCE00835F, true);
    ChartPanel cp;
    JDialog lineChart = new JDialog(mainFrame);

    // Linechart
    public void showLineChart(){
        lineChart.getContentPane().removeAll();
        TimeSeries patient = new TimeSeries("");

        if (listOfPatients.get(patientNumber).listOfWeightPoints.size() > 1 ){
            for (int i = 0; i < listOfPatients.get(patientNumber).listOfWeightPoints.size(); i++) {
                double weight = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getWeight();
                int day = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getDay();
                int month = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getMonth();
                int year = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getYear();
                String[] time = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getTime().split(":");
                int minute = Integer.parseInt(time[0]);
                int hour = Integer.parseInt(time[1]);
                Day date = new Day(day, month, year);
                patient.addOrUpdate(date, weight);
            }

            TimeSeriesCollection dataset = new TimeSeriesCollection();
            dataset.addSeries(patient);

            JFreeChart xyLineChart = ChartFactory.createTimeSeriesChart(
                    "",
                    "", // X-Axis Label
                    "", // Y-Axis Label
                    dataset, false, false, false);

            XYPlot plot = xyLineChart.getXYPlot();
            int seriesCount = plot.getSeriesCount();

            for (int i = 0; i < seriesCount; i++) {
                plot.getRenderer().setSeriesStroke(i, new BasicStroke(3));
                plot.getRenderer().setSeriesPaint(i, Color.RED);
            }
            plot.getRangeAxis().setUpperMargin(0);
            plot.getRangeAxis().setLowerMargin(0);
            plot.getDomainAxis().setUpperMargin(0);
            plot.getDomainAxis().setLowerMargin(0);

            plot.setDomainMinorGridlinesVisible(false);
            xyLineChart.setBackgroundPaint(frameBackground);
            xyLineChart.setBorderVisible(false);
            xyLineChart.setBorderStroke(new BasicStroke(0));
            plot.setBackgroundPaint(textFieldColor);
            plot.setDomainGridlinePaint(gridLine);
            plot.setRangeGridlinePaint(gridLine);
            plot.setShadowGenerator(null);

            cp = new ChartPanel(xyLineChart);
            cp.setDomainZoomable(false);
            cp.setRangeZoomable(false);
            cp.setPopupMenu(null);
            Dimension cpDimension = new Dimension();
            cpDimension.setSize(225, 210);
            cp.setMaximumSize(cpDimension);
            cp.setBackground(buttonColor);
            lineChart.add(cp);
        }
        lineChart.setBounds(mainFrame.getX() + frameSize - 15, mainFrame.getY(), frameSize*2,frameSize*2 - 38);
        lineChart.getContentPane().setBackground(frameBackground);
        lineChart.setTitle(weightChartTitle + " " + listOfPatients.get(patientNumber).getFullName());
        lineChart.setResizable(false);
        lineChart.setVisible(true);
    }

    JDialog weightPointFrame = new JDialog(lineChart);
    ArrayList<JButton> weightPointButtons = new ArrayList<>();

    int weightPointNumber;

    JButton addWeightPointButton = new JButton();

    public void activateAddWeightPointButton(){
        addWeightPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNewWeightPointFrame();
            }
        });
    }

    // Weight points frame
    public void showWeightPointsFrame(){
        weightPointFrame.getContentPane().removeAll();
        weightPointFrame.setResizable(false);
        weightPointFrame.setLayout(null);
        weightPointFrame.setSize(frameSize, frameSize);
        weightPointFrame.setTitle(weightPointFrameTitle);
        weightPointFrame.setLocation(lineChart.getX() + frameSize*2 - 16, lineChart.getY());
        weightPointFrame.getContentPane().setBackground(frameBackground);

        addWeightPointButton.setLocation(170, 170);
        addWeightPointButton.setSize(30, 10);
        addWeightPointButton.setBorder(null);
        addWeightPointButton.setBackground(buttonColor);
        addWeightPointButton.setText("+");

        weightPointFrame.add(addWeightPointButton);

        weightPointButtons.clear();

        for(int i = 0; i < listOfPatients.get(patientNumber).listOfWeightPoints.size() && i < 16; i++){
            weightPointButtons.add(new JButton());
            String gewichtMeetPunt = listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getTime() + " | " +
                    listOfPatients.get(patientNumber).listOfWeightPoints.get(i).getDate(true);
            weightPointButtons.get(i).setText(gewichtMeetPunt);
            weightPointButtons.get(i).setSize(90, 19);
            weightPointButtons.get(i).setBackground(buttonColor);
            weightPointButtons.get(i).setBorder(null);
            if(i <= 7)
            { weightPointButtons.get(i).setLocation(10, 10 + (19 * i));}
            else if (i > 7) { weightPointButtons.get(i).setLocation(110, 10 + (19 * (i - 8)));}
            weightPointButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    weightPointNumber = weightPointButtons.indexOf((e.getSource()));
                    showWeightPointDetailFrame();
                }
            });
            weightPointFrame.add(weightPointButtons.get(i));

        }
        weightPointFrame.setVisible(true);
    }

    JDialog weightPointDetailFrame = new JDialog(weightPointFrame);
    JButton deleteWeightPointButton = new JButton(deleteIcon);
    JLabel dateLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JTextField timeTextField = new JTextField();
    JFormattedTextField dateTextField = new JFormattedTextField();
    JLabel weightPointLabel = new JLabel();
    JFormattedTextField weightPointText = new JFormattedTextField();
    JLabel gainOrLoseWeightLabel = new JLabel();
    JFormattedTextField gainOrLoseWeightText = new JFormattedTextField();

    public void showWeightPointDetailFrame(){
        weightPointDetailFrame.getContentPane().removeAll();
        weightPointDetailFrame.setLayout(null);
        String weightPointDetail = listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber).getDate(false);
        weightPointDetailFrame.setTitle(weightPointDetail);
        weightPointDetailFrame.getContentPane().setBackground(frameBackground);
        weightPointDetailFrame.setSize(frameSize, frameSize - 30);
        weightPointDetailFrame.setResizable(false);
        weightPointDetailFrame.setLocation(weightPointFrame.getX(), weightPointFrame.getY() + frameSize - 8);
        weightPointDetailFrame.setLayout(null);
        weightPointLabel.setBounds(10, 10, 100, 20);
        weightPointLabel.setText(weightLabelText);

        dateLabel.setBounds(10, 70, 100, 20);
        dateLabel.setText(dateLabelText);
        weightPointDetailFrame.add(dateLabel);

        dateTextField.setBounds(110, 70, 90, 20);
        dateTextField.setBackground(textFieldColor);
        dateTextField.setEditable(false);
        dateTextField.setBorder(null);
        dateTextField.setText(listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber).getDate(false));
        weightPointDetailFrame.add(dateTextField);

        timeLabel.setBounds(10, 50, 100,20);
        timeLabel.setText(timeLabelText);
        weightPointDetailFrame.add(timeLabel);

        timeTextField.setBounds(110, 50, 90, 20);
        timeTextField.setText(listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber).getTime());
        timeTextField.setBorder(null);
        timeTextField.setEditable(false);
        timeTextField.setBackground(textFieldColor);
        weightPointDetailFrame.add(timeTextField);

        weightPointDetailFrame.add(weightPointLabel);
        weightPointDetailFrame.add(weightPointText);


       if(weightPointNumber > 0) {
           gainOrLoseWeightLabel.setBounds(10, 30, 100, 20);
           double gainOrLose = listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber).getWeight() - listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber - 1).getWeight();
           if (gainOrLose > 0) {
               gainOrLoseWeightLabel.setText(gainedWeight);
           } else if (gainOrLose < 0) {
               gainOrLoseWeightLabel.setText(lostWeight);
           } else {
               gainOrLoseWeightLabel.setText(noDifferenceInWeight);
           }
           gainOrLoseWeightText.setBounds(110, 30, 90, 20);
           gainOrLoseWeightText.setEditable(false);
           gainOrLoseWeightText.setBorder(null);
           gainOrLoseWeightText.setBackground(textFieldColor);
           if(gainOrLose != 0) {
               gainOrLoseWeightText.setValue(numberFormat.format(abs(gainOrLose)).replace(',', '.') + " kg");
           } else {
               gainOrLoseWeightText.setValue("0 kg");
           }
           weightPointDetailFrame.add(gainOrLoseWeightLabel);
           weightPointDetailFrame.add(gainOrLoseWeightText);
       } else {
           dateLabel.setLocation(10, 30);
           dateTextField.setLocation(110, 30);
           timeLabel.setLocation(10, 50);
           timeTextField.setLocation(110, 50);
       }


       weightPointText.setBounds(110, 10, 90, 20);
       weightTextField.setValue(numberFormat.format(listOfPatients.get(patientNumber).getWeight()).replace(",", "."));
       String weightMeetPunt = numberFormat.format(listOfPatients.get(patientNumber).listOfWeightPoints.get(weightPointNumber).getWeight()).replace(",", ".");
       weightPointText.setValue(weightMeetPunt);
       weightPointText.setEditable(false);
       weightPointText.setBorder(null);
       weightPointText.setBackground(textFieldColor);

       deleteWeightPointButton.setBounds(170, 130, 30, 20);
       deleteWeightPointButton.setBackground(buttonColor);
       deleteWeightPointButton.setBorder(null);
       weightPointDetailFrame.add(deleteWeightPointButton);

       weightPointDetailFrame.setVisible(true);
    }

    public void activateDeleteWeightPointButton(){
        deleteWeightPointButton.addActionListener(new ActionListener() {
            @Override
            final public void actionPerformed(ActionEvent e) {
                listOfPatients.get(patientNumber).listOfWeightPoints.remove(weightPointNumber);
                weightPointDetailFrame.dispose();
                showWeightPointsFrame();
                showLineChart();
                updatePatientFile();
            }
        });
    }

    JDialog newWeightPointFrame = new JDialog(weightPointFrame);
    JLabel newWeightLabel = new JLabel();
    JLabel newDateLabel = new JLabel();
    JLabel newTimeLabel = new JLabel();
    JTextField newWeight = new JTextField();
    JTextField newDate = new JTextField();
    JTextField newTime = new JTextField();
    JButton saveNewWeightPoint = new JButton();

    GregorianCalendar calendar = new GregorianCalendar();

    public void showNewWeightPointFrame(){
        newWeightPointFrame.setTitle(newWeightPointFrameTitle);
        newWeightPointFrame.setResizable(false);
        newWeightPointFrame.setLayout(null);
        newWeightPointFrame.setLocation(weightPointFrame.getX() + 210, weightPointFrame.getY());
        newWeightPointFrame.setSize(frameSize, frameSize);
        newWeightPointFrame.getContentPane().setBackground(frameBackground);

        newWeightLabel.setBounds(10,10,100, 20);
        newWeightLabel.setText(weightLabelText);
        newWeight.setText(Double.toString(listOfPatients.get(patientNumber).getWeight()));
        newWeight.setBounds(110, 10, 90, 20);
        newWeight.setBorder(null);
        newWeight.setBackground(textFieldEditableColor);
        newWeightPointFrame.add(newWeightLabel);



        newTimeLabel.setBounds(10, 30, 100, 20);
        newTimeLabel.setText(timeLabelText);
        newTime.setBounds(110, 30, 90, 20);
        if(calendar.get(Calendar.MINUTE) < 10){
            newTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":0" + calendar.get(Calendar.MINUTE));
        } else {
            newTime.setText(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        }
        newTime.setBorder(null);
        newTime.setBackground(textFieldEditableColor);
        newWeightPointFrame.add(newTimeLabel);

        newDateLabel.setBounds(10, 50, 90, 20);
        newDateLabel.setText(dateLabelText);
        newDate.setBounds(110, 50, 90, 20);
        newDate.setText(calendar.get(Calendar.DAY_OF_MONTH) + "-" +  (calendar.get(Calendar.MONTH) + 1) + "-" +  calendar.get(Calendar.YEAR));
        newDate.setBorder(null);
        newDate.setBackground(textFieldEditableColor);
        newWeightPointFrame.add(newDateLabel);

        saveNewWeightPoint.setBounds(110, 160, 90, 20);
        saveNewWeightPoint.setBackground(buttonColor);
        saveNewWeightPoint.setBorder(null);
        saveNewWeightPoint.setText(editButtonSaveText);

        newWeightPointFrame.add(saveNewWeightPoint);
        newWeightPointFrame.add(newWeight);
        newWeightPointFrame.add(newTime);
        newWeightPointFrame.add(newDate);
        newWeightPointFrame.setVisible(true);
    }

    public void activateSaveNewWeightPoint(){
        saveNewWeightPoint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] date = new String[3];
                try {
                    double newWeightInt = Double.parseDouble(newWeight.getText());
                    String newTimeString = newTime.getText();
                    date = newDate.getText().split("-", 3);
                    int newDay = Integer.parseInt(date[0]);
                    int newMonth = Integer.parseInt(date[1]);
                    int newYear = Integer.parseInt(date[2]);
                    listOfPatients.get(patientNumber).listOfWeightPoints.add(new WeightPoint(newDay, newMonth, newYear, newTimeString, newWeightInt));
                    showLineChart();
                    showWeightPointsFrame();
                    newWeightPointFrame.dispose();
                    updatePatientFile();
                } catch (Exception notAValidWeightPoint){
                    newWeightPointFrame.setTitle("voer geldige waardes in");
                }


            }
        });
    }

    public void addWeightPoints(){
        listOfPatients.get(0).listOfWeightPoints.add(new WeightPoint(1,1,2017, "9:30", 80));

    }

    private int hours;
    private int minutes;

    public void setAlarms(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Calendar c = new GregorianCalendar();
                    hours = c.get(Calendar.HOUR_OF_DAY);
                    minutes = c.get(Calendar.MINUTE);
                    for (int i = 0; i < listOfPatients.size(); i++) {
                        for (int a = 0; a < listOfPatients.get(i).listOfPrescriptions.size(); a++) {

                            int atHour = listOfPatients.get(i).listOfPrescriptions.get(a).getAtHour();
                            int atMinute = listOfPatients.get(i).listOfPrescriptions.get(a).getAtMinute();
                            int interval = listOfPatients.get(i).listOfPrescriptions.get(a).getInterval();
                            int times = listOfPatients.get(i).listOfPrescriptions.get(a).getTimes();

                            while (hours > atHour || (hours == atHour && minutes > atMinute)){
                                atHour = atHour + interval;
                            }

                            if (atHour == hours && atMinute == minutes && !listOfPatients.get(i).listOfPrescriptions.get(a).isAlarmWentOff()) {
                                listOfPatients.get(i).listOfPrescriptions.get(a).setPrescriptionTaken(false);
                                String medicineName = listOfPatients.get(i).listOfPrescriptions.get(a).getMedicine().getMedicineName();
                                String fullName = listOfPatients.get(i).getFullName();
                                String medicineDose = listOfPatients.get(i).listOfPrescriptions.get(a).getMedicine().getMedicineDose();
                                String popUpMessageText = fullName + " " + chosenLanguage.getString("popUpMessage1")
                                        + " " + medicineDose + " " + medicineName + " " + chosenLanguage.getString("popUpMessage2");
                                if(minutes < 10){
                                    showMedicinePopUp(hours + ":0" + minutes, popUpMessageText, i, a);
                                } else {
                                    showMedicinePopUp(hours + ":" + minutes, popUpMessageText, i, a);
                                }

                                atHour = atHour + interval;
                            }

                            if (atHour != hours && atMinute != minutes && listOfPatients.get(i).listOfPrescriptions.get(a).isAlarmWentOff()){
                                listOfPatients.get(i).listOfPrescriptions.get(a).setAlarmWentOff(false);
                            }


                            if(atHour > (listOfPatients.get(i).listOfPrescriptions.get(a).getAtHour() + interval * (times - 1))){
                                atHour = listOfPatients.get(i).listOfPrescriptions.get(a).getAtHour();
                            }

                            listOfPatients.get(i).listOfPrescriptions.get(a).setTimeTillAlarm(atHour - hours, atMinute - minutes);

                        }

                    }
                    try{
                        sleep(1000);
                    } catch (Exception e){

                    }
                }
            }
        }).start();
    }

    ArrayList<JDialog> popUps = new ArrayList<>();
    ArrayList<JButton> popUpButtons = new ArrayList<>();
    ArrayList<JTextArea> popUpMessages = new ArrayList<>();

    public void showMedicinePopUp(String title, String message, int alarmPatientNumber, int alarmPrescriptionNumber){

        int patient = alarmPatientNumber;
        int prescription = alarmPrescriptionNumber;
        if(!listOfPatients.get(patient).listOfPrescriptions.get(prescription).isAlarmWentOff() &&
                !listOfPatients.get(patient).listOfPrescriptions.get(prescription).isPrescriptionTaken()) { ;
            popUps.add(new JDialog());
            popUpButtons.add(new JButton("OK"));
            popUpMessages.add(new JTextArea());

            JDialog medicinePopUp = new JDialog();
            JTextArea popUpMessage = new JTextArea();
            JButton closePopUp = new JButton("OK");

            medicinePopUp.getContentPane().removeAll();
            medicinePopUp.setTitle(title);
            medicinePopUp.setBounds(0, 0, frameSize, 120);
            medicinePopUp.setAlwaysOnTop(true);
            medicinePopUp.setLayout(null);
            medicinePopUp.setResizable(false);
            medicinePopUp.getContentPane().setBackground(frameBackground);

            popUpMessage.setText(message);
            popUpMessage.setLocation(10, 10);
            popUpMessage.setSize(195, 40);
            popUpMessage.setEditable(false);
            popUpMessage.setLineWrap(true);
            popUpMessage.setWrapStyleWord(true);
            popUpMessage.setBorder(null);
            popUpMessage.setBackground(frameBackground);

            closePopUp.setBounds(10, 50, 195, 20);
            closePopUp.setBackground(buttonColor);
            closePopUp.setBorder(null);
            closePopUp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    medicinePopUp.dispose();
                    if (prescriptionFrame.isVisible()) {
                        showPrescriptionFrame();
                    }
                    listOfPatients.get(patient).listOfPrescriptions.get(prescription).setPrescriptionTaken(true);
                    popUps.remove(medicinePopUp);
                    popUpButtons.remove(closePopUp);
                    popUpMessages.remove(popUpMessage);
                }
            });

            medicinePopUp.add(closePopUp);
            medicinePopUp.add(popUpMessage);


            listOfPatients.get(patient).listOfPrescriptions.get(prescription).setAlarmWentOff(true);
            medicinePopUp.setVisible(true);


        }

    }

}