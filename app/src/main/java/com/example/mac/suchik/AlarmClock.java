package com.example.mac.suchik;

public class AlarmClock {
    private String time;
    private String id;

    public void setTime(String time) {
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {

        return time;
    }

    public String getId() {
        return id;
    }

    AlarmClock(String time, String id){
        this.time = time;
        this.id = id;
    }
}
