package com.example.mac.suchik.WeatherData;

public class Parts {
    Night NightObject;
    Morning MorningObject;
    Day DayObject;
    Evening EveningObject;
    Day_short Day_shortObject;
    Night_short Night_shortObject;


    // Getter Methods

    public Night getNight() {
        return NightObject;
    }

    public Morning getMorning() {
        return MorningObject;
    }

    public Day getDay() {
        return DayObject;
    }

    public Evening getEvening() {
        return EveningObject;
    }

    public Day_short getDay_short() {
        return Day_shortObject;
    }

    public Night_short getNight_short() {
        return Night_shortObject;
    }

    // Setter Methods

    public void setNight(Night nightObject) {
        this.NightObject = nightObject;
    }

    public void setMorning(Morning morningObject) {
        this.MorningObject = morningObject;
    }

    public void setDay(Day dayObject) {
        this.DayObject = dayObject;
    }

    public void setEvening(Evening eveningObject) {
        this.EveningObject = eveningObject;
    }

    public void setDay_short(Day_short day_shortObject) {
        this.Day_shortObject = day_shortObject;
    }

    public void setNight_short(Night_short night_shortObject) {
        this.Night_shortObject = night_shortObject;
    }
}
