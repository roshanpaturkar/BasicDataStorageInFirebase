package roshan.paturkar.com.smokerminimodel;

import static android.os.Build.VERSION_CODES.P;

public class Support {

    public static int txtToInt(String num){
        return Integer.parseInt(num);
    }

    public static String checkBp(String sy, String di){
        int s = txtToInt(sy);
        int d = txtToInt(di);

        if (s <= 90 && d <= 60){
            return  "BP is LOW!";
        } else if ((s >= 90 && s <= 120) && (d >= 60 && d <= 80)) {
            return "BP is NORMAL!";
        } else if(s > 120 && d > 80){
            return "BP is HIGH!";
        } else {
            return "BP is Abnormal!";
        }
    }

    public static String checkGlF(String fa){
        int f = txtToInt(fa);

        if (f < 70){
            return  "FASTING sugar level is LOW!";
        } else if ((f >= 70 && f <= 110)) {
            return "FASTING sugar level is NORMAL!";
        } else {
            return "FASTING sugar level is HIGH!";
        }
    }

    public static String checkGlP(String po){
        int pp = txtToInt(po);

        if (pp < 70){
            return  "POSTMEAL sugar level is LOW!";
        } else if ((pp >= 70 && pp <= 140)) {
            return "POSTMEAL sugar level is NORMAL!";
        } else {
            return "POSTMEAL sugar level is HIGH!";
        }
    }

    public static String checkHemoglobine(String he) {
        int h = txtToInt(he);

        if (h < 13.5) {
            return "Hemoglobin is LOW";
        } else if (h >= 13.5 && h <= 17.5) {
            return "Hemoglobin is NORMAL";
        } else {
            return "Hemoglobin is HIGH!";
        }
    }

    public static String checkCalcium(String cl) {
        int c = txtToInt(cl);

        if (c < 8.5) {
            return "Calcium is LOW";
        } else if (c >= 8.5 && c <= 10.5) {
            return "Calcium is NORMAL";
        } else {
            return "Calcium is HIGH!";
        }
    }
}
