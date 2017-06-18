package begnardi.luca.thesis_test;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by begno on 03/08/15.
 */

public class Sample {

    private int charNum;
    private int delNum;
    private int correctNum;
    private int completNum;
    private double totalTime;
    private double delPerSec;
    private double charPerSec;
    private double correctPerSec;
    private double completPerSec;
    private boolean drunk;
    private String date;

    //private String text;

    public Sample() {
        date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public void setCharNum(int charNum) {
        this.charNum = charNum;
    }

    public int getCharNum() {
        return charNum;
    }

    public void setDelNum(int delNum) {
        this.delNum = delNum;
    }

    public int getDelNum() {
        return delNum;
    }

    public void setCorrectNum(int correctNum) {
        this.correctNum = correctNum;
    }

    public int getCorrectNum() {
        return correctNum;
    }

    public void setCompletNum(int completNum) {
        this.completNum = completNum;
    }

    public int getCompletNum() {
        return completNum;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setCharPerSec(double charPerSec) {
        this.charPerSec = charPerSec;
    }

    public void calcCharPerSec() {
        charPerSec = charNum / totalTime;
    }

    public double getCharPerSec() {
        return charPerSec;
    }

    public void setDelPerSec(double delPerSec) {
        this.delPerSec = delPerSec;
    }

    public void calcBackspPerSec() {
        delPerSec = delNum / totalTime;
    }

    public double getDelPerSec() {
        return delPerSec;
    }

    public void setCompletPerSec(double completPerSec) {
        this.completPerSec = completPerSec;
    }

    public void calcCompletPerSec() {
        completPerSec = completNum / totalTime;
    }

    public double getCompletPerSec() {
        return completPerSec;
    }

    public void setCorrectPerSec(double correctPerSec) {
        this.correctPerSec = correctPerSec;
    }

    public void calcCorrectPerSec() {
        correctPerSec = correctNum / totalTime;
    }

    public double getCorrectPerSec() {
        return correctPerSec;
    }

    public void setDrunk(boolean drunk) {
        this.drunk = drunk;
    }

    public boolean isDrunk() {
        return drunk;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getDateForFile() {
        return date.replaceAll(" ", "_");
    }

    //used to save samples in the storage file
    public String toCSV() {
        return delNum +","+
               correctNum +","+
               completNum +","+
               totalTime +","+
               charPerSec +","+
                delPerSec +","+
               correctPerSec +","+
               completPerSec +","+
               drunk +","+
               date;
    }

    //used to retrieve samples from the storage file, to show them in the main page
    public Sample fromCSV(String csv) {
        Sample s = new Sample();
        String csvArray[] = csv.split(",");
        s.delNum = Integer.parseInt(csvArray[0]);
        s.correctNum = Integer.parseInt(csvArray[1]);
        s.completNum = Integer.parseInt(csvArray[2]);
        s.totalTime = Double.parseDouble(csvArray[3]);
        s.charPerSec = Double.parseDouble(csvArray[4]);
        s.delPerSec = Double.parseDouble(csvArray[5]);
        s.correctPerSec = Double.parseDouble(csvArray[6]);
        s.completPerSec = Double.parseDouble(csvArray[7]);
        s.drunk = Boolean.parseBoolean(csvArray[8]);
        s.date = csvArray[9];
        return s;
    }

    //used in a previous client-server version
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        try {
            json.put("number_delete", delNum);
            json.put("number_correction", correctNum);
            json.put("number_completion", completNum);
            json.put("character_per_second", charPerSec);
            json.put("delete_per_second", delPerSec);
            json.put("correction_per_second", correctPerSec);
            json.put("completion_per_second", completPerSec);
            json.put("drunk", drunk);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    //needed by the classifier
    public Mat toMat() {

        ArrayList<Float> values = new ArrayList<Float>();

        values.add((float) delNum);
        values.add((float) correctNum);
        values.add((float) completNum);
        values.add((float) charPerSec);
        values.add((float) delPerSec);
        values.add((float) correctPerSec);
        values.add((float) completPerSec);
        if(drunk)
            values.add((float) 1.0);
        else
            values.add((float) 0.0);

        MatOfFloat m = new MatOfFloat();
        m.fromList(values);
        return m;
    }
}