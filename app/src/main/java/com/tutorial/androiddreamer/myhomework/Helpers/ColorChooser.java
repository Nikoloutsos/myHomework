package com.tutorial.androiddreamer.myhomework.Helpers;

public class ColorChooser {
    /**
     *
     * @param number
     * @return Colors in as strings depending on number size.
     */
    public static String chooseColor(int number){
        if(number <= 2) return "#8EE5EE";
        if(number <= 4) return "#7AC5CD";
        if(number <= 6) return "#FF9000";
        if(number <= 8) return "#FF5000";
        return "#FF1000";
    }
}
