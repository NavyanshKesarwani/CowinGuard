package com.navyanshkesarwani.cowinguard;

public class Model {
    private String center_name;
    private String timing1;
    private String timing2;
    private String timing3;
    private String timing4;
    private int info_button;

    Model(String center_name, String timing1, String timing2, String timing3, String timing4, int info_button){
        this.center_name = center_name;
        this.timing1 = timing1;
        this.timing2 = timing2;
        this.timing3 = timing3;
        this.timing4 = timing4;
        this.info_button = info_button;
    }

    public String getCenter_name() {
        return center_name;
    }

    public String getTiming1() {
        return timing1;
    }

    public String getTiming2() {
        return timing2;
    }

    public String getTiming3() {
        return timing3;
    }

    public String getTiming4() {
        return timing4;
    }

    public int getInfo_button() {
        return info_button;
    }
}
