package com.example.mac.suchik.WeatherData;

import java.util.List;

public class WeatherData {
        private float now;
        private String now_dt;
        Info info;
        Fact fact;
        List<Forecasts> forecasts;


        // Getter Methods

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public float getNow() {
            return now;
        }

        public String getNow_dt() {
            return now_dt;
        }

        // Setter Methods

        public void setNow(float now) {
            this.now = now;
        }

        public void setNow_dt(String now_dt) {
            this.now_dt = now_dt;
        }


    public void setForecasts(List<Forecasts> forecasts) {
        this.forecasts = forecasts;
    }

    public List<Forecasts> getForecasts() {
        return forecasts;
    }
}

