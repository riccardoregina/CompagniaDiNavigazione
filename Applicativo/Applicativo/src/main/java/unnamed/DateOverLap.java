package unnamed;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class DateOverLap {
    private static ArrayList<LocalDate> getDateOverlap(ArrayList<LocalDate> inizioPer, ArrayList<LocalDate> finePer) {
        ArrayList<LocalDate> dateOverlap = new ArrayList<LocalDate>();
        HashSet<LocalDate> dateViste = new HashSet<LocalDate>();

        for (int i = 0; i < inizioPer.size(); i++) {
            LocalDate firstDate = inizioPer.get(i);
            LocalDate lastDate = finePer.get(i);

            for (LocalDate currentDate = firstDate; !currentDate.isAfter(lastDate); currentDate = currentDate.plusDays(1)) {
                if (!dateViste.contains(currentDate)) {
                    dateViste.add(currentDate);
                } else {
                    dateOverlap.add(currentDate);
                }
            }
        }

        return dateOverlap;
    }

    private static int countDay(ArrayList<String> giorni, int day) {
        int cont = 0;
        for (int i = 0; i < giorni.size(); i++) {
            if (day != 7) {
                if (giorni.get(i).charAt(day) == '1') {
                    cont++;
                }
            }
        }
        return (cont > 1) ? 1 : 0;
    }

    public static int getNumberOfOverlap(ArrayList<LocalDate> inizioPer, ArrayList<LocalDate> finePer, ArrayList<String> giorni) {
        ArrayList<LocalDate> dateOverlap = getDateOverlap(inizioPer, finePer);
        int overlap = 0;
        for (LocalDate currentDate : dateOverlap) {
            int day = currentDate.getDayOfWeek().getValue();
            overlap += countDay(giorni, day);
        }

        return overlap;
    }
}
