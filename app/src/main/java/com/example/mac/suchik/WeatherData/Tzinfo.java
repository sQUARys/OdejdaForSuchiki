package com.example.mac.suchik.WeatherData;

public class Tzinfo {
        private String name;
        private String abbr;
        private float offset;
        private boolean dst;


        // Getter Methods

        public String getName() {
            return name;
        }

        public String getAbbr() {
            return abbr;
        }

        public float getOffset() {
            return offset;
        }

        public boolean getDst() {
            return dst;
        }

        // Setter Methods

        public void setName(String name) {
            this.name = name;
        }

        public void setAbbr(String abbr) {
            this.abbr = abbr;
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        public void setDst(boolean dst) {
            this.dst = dst;
        }

}
