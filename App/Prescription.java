package App;

public class Prescription {

    private Medicine medicine;
    private int atHour;
    private int atMinute;
    private int interval;
    private int times;
    private String timeTillAlarm;
    private boolean isPrescriptionTaken = true;

    public int getMedicineID() {
        return medicineID;
    }

    int medicineID;

    public Prescription(Medicine medicine, int atHour, int atMinute, int interval, int times){
        this.medicine = medicine;
        this.atHour = atHour;
        this.atMinute = atMinute;
        this.interval = interval;
        this.times = times;
    }

    public Prescription(int medicineID, int atHour, int atMinute, int interval, int times){
        this.medicineID = medicineID;


    }


    public Medicine getMedicine(){
        return medicine;
    }

    public void setAtHour(int newHour){
        atHour = newHour;
    }
    public int getAtHour() { return atHour;}
    public int getAtMinute() { return atMinute;}
    public int getInterval() { return interval;}

    public int getTimes(){
        return times;
    }

    public String getTimeTillAlarm(){
        return timeTillAlarm;
    }

    public void setTimeTillAlarm(int hours, int minutes){
        if (minutes < 0){
            minutes = minutes + 60;
            hours = hours - 1;
        }
        if (hours < 0){
            hours = hours + 24;
        }
        if (hours == 0 && minutes == 00){
            hours = 24;
        }
        String hoursString = "" + hours;
        String minutesString = "" + minutes;
        if(hours < 10){
            hoursString = "0" + hours;
        }

        if(minutes < 10){
            minutesString = "0" + minutes;
        }

        timeTillAlarm = hoursString + "h" + minutesString + "m";
    }

    String atTime;

    public String getAtTime(){
        String hoursString = "" + atHour;
        String minutesString = "" + atMinute;

        if(atHour < 10){
            hoursString = "0" + atHour;
        }
        if(atMinute < 10){
            minutesString = "0" + atMinute;
        }
        return atTime = hoursString + ":" + minutesString;

    }

    public boolean isPrescriptionTaken(){
        return isPrescriptionTaken;
    }

    public void setPrescriptionTaken(boolean prescriptionTaken){
        this.isPrescriptionTaken = prescriptionTaken;
    }

    private boolean alarmWentOff = false;

    public boolean isAlarmWentOff() {
        return alarmWentOff;
    }

    public void setAlarmWentOff(boolean alarmWentOff) {
        this.alarmWentOff = alarmWentOff;
    }
}






