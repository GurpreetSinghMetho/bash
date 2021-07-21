package com.orem.bashhub.data;

public class ComplementPOJO {

    private String name, count;
    private int icon;

    public ComplementPOJO(String name, String count, int icon) {
        this.name = name;
        this.count = count;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getCount() {
        return count;
    }

    public int getIcon() {
        return icon;
    }
}
