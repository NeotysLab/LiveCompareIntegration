package com.neotys.tricentis.workloadParser.jsonDatamodel;

import java.util.Random;

public class NavigationProperty {
    String key;
    String text;
    String color;
    Random obj = new Random();


    public NavigationProperty(String key, String text, String color) {
        this.key = key;
        this.text = text;
        this.color = color;
    }

    public NavigationProperty(String name)
    {
        this.key=name;
        String[] nameslited=name.split(":");
        if(nameslited.length>1)
        {
            this.text="Tcode "+nameslited[0] + " screen "+nameslited[1];
        }
        else
            this.text=name;

       this.color=String.valueOf(obj.nextInt(0xffffff + 1));
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
