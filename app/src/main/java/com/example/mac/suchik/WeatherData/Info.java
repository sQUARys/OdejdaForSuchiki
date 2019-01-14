package com.example.mac.suchik.WeatherData;

public class Info {
        private boolean f;
        private boolean n;
        private boolean nr;
        private boolean ns;
        private boolean nsr;
        private boolean p;
        private float lat;
        private float lon;
        Tzinfo TzinfoObject;
        private float def_pressure_mm;
        private float def_pressure_pa;
        private boolean _h;
        private String url;


        // Getter Methods

        public boolean getF() {
            return f;
        }

        public boolean getN() {
            return n;
        }

        public boolean getNr() {
            return nr;
        }

        public boolean getNs() {
            return ns;
        }

        public boolean getNsr() {
            return nsr;
        }

        public boolean getP() {
            return p;
        }

        public float getLat() {
            return lat;
        }

        public float getLon() {
            return lon;
        }

        public Tzinfo getTzinfo() {
            return TzinfoObject;
        }

        public float getDef_pressure_mm() {
            return def_pressure_mm;
        }

        public float getDef_pressure_pa() {
            return def_pressure_pa;
        }

        public boolean get_h() {
            return _h;
        }

        public String getUrl() {
            return url;
        }

        // Setter Methods

        public void setF(boolean f) {
            this.f = f;
        }

        public void setN(boolean n) {
            this.n = n;
        }

        public void setNr(boolean nr) {
            this.nr = nr;
        }

        public void setNs(boolean ns) {
            this.ns = ns;
        }

        public void setNsr(boolean nsr) {
            this.nsr = nsr;
        }

        public void setP(boolean p) {
            this.p = p;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setTzinfo(Tzinfo tzinfoObject) {
            this.TzinfoObject = tzinfoObject;
        }

        public void setDef_pressure_mm(float def_pressure_mm) {
            this.def_pressure_mm = def_pressure_mm;
        }

        public void setDef_pressure_pa(float def_pressure_pa) {
            this.def_pressure_pa = def_pressure_pa;
        }

        public void set_h(boolean _h) {
            this._h = _h;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
