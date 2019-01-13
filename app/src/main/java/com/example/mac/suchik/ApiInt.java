package com.example.mac.suchik;

import android.app.DownloadManager;

import java.io.IOException;
import java.util.Map;

interface WheatherTime{
    Map<String, String> parseWheather(String json);
}
