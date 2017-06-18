package begnardi.luca.thesis_test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by begno on 04/08/15.
 */
public abstract class Utils {

    public static double average(ArrayList<Double> values) {
        double average = 0;
        for(int i = 0; i < values.size(); i++)
            average += values.get(i);
        return average / values.size();
    }

    public static double secondFromNano(double nano) {
        return nano / 1000000000;
    }

    /**
     * @param value double value to round
     * @param places decimal places wanted
     * @return a double value in the format 0.00
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
