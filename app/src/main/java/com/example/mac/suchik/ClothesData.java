package com.example.mac.suchik;

public class ClothesData {
    private int category;
    private String name;
    private int minTemp;
    private int maxTemp;
    private int rain;
    private int wind;
    private int cloud;
    private String color;

    // Getter Methods
    public int getCategory(){return category;}

    public String getName(){return name;}

    public int getMinTemp(){return minTemp;}

    public int getMaxTemp(){return maxTemp;}

    public int getRain(){return rain;}

    public int getWind(){return wind;}

    public int getCloud(){return cloud;}

    public String getColor(){return color;}


    // Setter Methods

    public void setCategory(int category) { this.category = category; }

    public void setName(String name) { this.name = name; }

    public void setMinTemp(int minTemp) { this.minTemp = minTemp; }

    public void setMaxTemp(int maxTemp) { this.maxTemp = maxTemp; }

    public void setRain(int rain) { this.rain = rain; }

    public void setWind(int wind) { this.wind = wind; }

    public void setCloud(int cloud) { this.cloud = cloud; }

    public void setColor(String color) { this.color = color; }
}
