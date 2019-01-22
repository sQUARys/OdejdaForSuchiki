package com.example.mac.suchik.WeatherData;


public class Forecasts {
        private String date;
        private float date_ts;
        private float week;
        private String sunrise;
        private String sunset;
        private String rise_begin;
        private String set_end;
        private float moon_code;
        private String moon_text;
        Parts parts;


        // Getter Methods

        public String getDate() {
            return date;
        }

        public float getDate_ts() {
            return date_ts;
        }

        public float getWeek() {
            return week;
        }

        public String getSunrise() {
            return sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public String getRise_begin() {
            return rise_begin;
        }

        public String getSet_end() {
            return set_end;
        }

        public float getMoon_code() {
            return moon_code;
        }

        public String getMoon_text() {
            return moon_text;
        }

        public Parts getParts() {
            return parts;
        }

        // Setter Methods

        public void setDate(String date) {
            this.date = date;
        }

        public void setDate_ts(float date_ts) {
            this.date_ts = date_ts;
        }

        public void setWeek(float week) {
            this.week = week;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public void setRise_begin(String rise_begin) {
            this.rise_begin = rise_begin;
        }

        public void setSet_end(String set_end) {
            this.set_end = set_end;
        }

        public void setMoon_code(float moon_code) {
            this.moon_code = moon_code;
        }

        public void setMoon_text(String moon_text) {
            this.moon_text = moon_text;
        }

        public void setParts(Parts parts) {
            this.parts = parts;
        }
    }

