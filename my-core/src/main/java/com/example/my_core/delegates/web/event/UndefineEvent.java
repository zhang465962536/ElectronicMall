package com.example.my_core.delegates.web.event;

import android.content.Context;

import com.example.my_core.delegates.LatteDelegate;
import com.example.my_core.util.log.LatteLogger;

public class UndefineEvent extends Event {


    @Override
    public String execute(String params) {
        LatteLogger.e("UndefineEvent",params);
        return null;
    }
}
