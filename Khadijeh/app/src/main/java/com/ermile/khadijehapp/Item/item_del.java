package com.ermile.khadijehapp.Item;

public class item_del {
    String text,day,plus,sex,id;

    public item_del(String text, String day, String plus, String sex, String id) {
        this.text = text;
        this.day = day;
        this.plus = plus;
        this.sex = sex;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
