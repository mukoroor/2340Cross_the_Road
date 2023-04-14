package com.example.team18;

import android.view.View;
import android.widget.Button;

public class Clock {
    private static int time = 0;
    private final Button button;

    private final CoupledListeners listener;

    public Clock(Button button, CoupledListeners listener) {
        this.button = button;
        this.listener = listener;
        this.button.setOnClickListener(listener);
    }

    public void addScheduledEvents(View.OnClickListener l) {
        listener.addListener(l);
    }

    public void dispatch() {
        button.performClick();
        setTime(time + 1);
    }

    public static int getTime() {
        return time;
    }

    public void setTime(int newTime) {
        if (newTime == Integer.MAX_VALUE) {
            time = 0;
        } else {
            time = newTime;
        }
    }
}
