package com.example.mac.suchik.WeatherData;

import java.util.List;

public class WeatherData {
        private float now;
        private String now_dt;
        Info InfoObject;
        Fact FactObject;
        List<Forecasts> forecasts;


        // Getter Methods

        public float getNow() {
            return now;
        }

        public String getNow_dt() {
            return now_dt;
        }

        public Info getInfo() {
            return InfoObject;
        }

        public Fact getFact() {
            return FactObject;
        }

        // Setter Methods

        public void setNow(float now) {
            this.now = now;
        }

        public void setNow_dt(String now_dt) {
            this.now_dt = now_dt;
        }

        public void setInfo(Info infoObject) {
            this.InfoObject = infoObject;
        }

        public void setFact(Fact factObject) {
            this.FactObject = factObject;
        }

    public void setInfoObject(Info infoObject) {
        InfoObject = infoObject;
    }

    public void setFactObject(Fact factObject) {
        FactObject = factObject;
    }

    public void setForecasts(List<Forecasts> forecasts) {
        this.forecasts = forecasts;
    }

    public Info getInfoObject() {
        return InfoObject;
    }

    public Fact getFactObject() {
        return FactObject;
    }

    public List<Forecasts> getForecasts() {
        return forecasts;
    }
}

