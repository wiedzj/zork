package App;

public class WeightPoint {

    private int day;
    private int month;
    private int year;
    private String time;
    private double weight;

    public WeightPoint(double weight){
        this.weight = weight;
    }


    public WeightPoint(int day, int month, int year, String time, double weight){
        this.day = day;
        this.month = month;
        this.year = year;
        this.time = time;
        this.weight = weight;
    }

    public int getDay(){
        return day;
    }

    public int getMonth() { return month; }

    public int getYear() { return year; }

    public String getTime(){
        return time;
    }

    public void setTime(String newTime){
        time = newTime;
    }

    public double getWeight(){
        return weight;
    }

    private String date;

    public String getDate(boolean abbreviation){

        String yearString;

        if(abbreviation == false) {
            date = day + "-" + month + "-" + year;
        } else {
            if (year < 2000) {
                yearString = Integer.toString(year - 1900);
                yearString = "'" + yearString;
                if((year - 1) < 10){
                    yearString = "'0" + (year - 1900);
                }
            } else if (year == 2000) {
                yearString = "'00";
            } else {
                yearString = Integer.toString(year - 2000);
                yearString = "'" + yearString;
                if((year - 2000) < 10){
                    yearString = "'0" + (year - 2000);
                }
            }
            date = day + "-" + month + "-" + yearString;
        }
        return date;
}


}
