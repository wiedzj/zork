package App;

public class Medicine {

    private String medicineName;
    private String medicineDescription;
    private String medicineDose;
    private String medicineType;
    private int medicineID;


    // Medicijn constructors
    public Medicine(String medicineName){
        this.medicineName = medicineName;
    }

    public Medicine(int medicineID, String medicineName, String medicineDescription, String medicineDose, String medicineType) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
        this.medicineDose = medicineDose;
        this.medicineType = medicineType;
    }

    public Medicine(String medicineName, String medicineDescription, String medicineDose, String medicineType) {
        this.medicineName = medicineName;
        this.medicineDescription = medicineDescription;
        this.medicineDose = medicineDose;
        this.medicineType = medicineType;
    }

    // Name
    public void setMedicineName(String newMedicineName) {
        medicineName = newMedicineName;
    }
    public String getMedicineName() {
        return medicineName;
    }

    // Description
    public void setMedicineDescription(String newMedicineDescription) {
        medicineDescription = newMedicineDescription;
    }
    public String getMedicineDescription(){
        return medicineDescription;
    }

    // Dose
    public void setMedicineDose(String newMedicineDose){ medicineDose = newMedicineDose; }
    public String getMedicineDose(){ return medicineDose; }

    // Type
    public void setMedicineType(String newMedicineType){ medicineType = newMedicineType; }
    public String getMedicineType(){ return medicineType; }

    public int getMedicineID(){
        return medicineID;
    }

    public void setMedicineID(int newMedicineID){
        medicineID = newMedicineID;
    }
}


