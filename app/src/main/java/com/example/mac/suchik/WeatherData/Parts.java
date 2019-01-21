package com.example.mac.suchik.WeatherData;

public class Parts {
    Night night;
    Morning morning;
    Day day;
    Evening evening;
    Day_short day_short;
    Night_short night_short;


    // Getter Methods

    public Night getNight() {
        return night;
    }

    public Morning getMorning() {
        return morning;
    }

    public Day getDay() {
        return day;
    }

    public Evening getEvening() {
        return evening;
    }

    public Day_short getDay_short() {
        return day_short;
    }

    public Night_short getNight_short() {
        return night_short;
    }

    // Setter Methods

    public void setNight(Night nightObject) {
        this.night = nightObject;
    }

    public void setMorning(Morning morningObject) {
        this.morning = morningObject;
    }

    public void setDay(Day dayObject) {
        this.day = dayObject;
    }

    public void setEvening(Evening eveningObject) {
        this.evening = eveningObject;
    }

    public void setDay_short(Day_short day_shortObject) {
        this.day_short = day_shortObject;
    }

    public void setNight_short(Night_short night_shortObject) {
        this.night_short = night_shortObject;
    }
}
