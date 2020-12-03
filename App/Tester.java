package App;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Tester {
    @Test
    public void testCase1() {
        Profile patient = new Profile("Jan", "", "Patiënt",
                100, 170, 80);
        assertEquals(80 / (1.7 * 1.7), patient.getBodyMassIndex(), 0);
    }

    @Test
    public void testCase2() {
        Profile patient = new Profile("Willie", "van den", "Kasten");
        assertEquals("Willie van den Kasten", patient.getFullName());
    }

    Medicine medicine = new Medicine("Paracetamol", "Een pilletje",
            "10mg", "Pijnstiller");
    Prescription prescription = new Prescription(medicine, 17, 03, 24, 1);

    @Test
    public void testCase3(){
        int atHour = prescription.getAtHour();
        int atMinute = prescription.getAtMinute();
        prescription.setTimeTillAlarm(atHour - 19, atMinute - 8); //
        assertEquals("21h55m", prescription.getTimeTillAlarm());
    }

    @Test
    public void testCase4(){
        int atHour = prescription.getAtHour();
        int atMinute = prescription.getAtMinute();
        prescription.setTimeTillAlarm(atHour - 12, atMinute - 55);
        assertEquals("04h08m", prescription.getTimeTillAlarm());
    }
}
