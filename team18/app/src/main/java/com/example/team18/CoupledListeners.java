package com.example.team18;

import android.view.View;

import java.util.ArrayList;

public class CoupledListeners implements View.OnClickListener {
    private ArrayList<View.OnClickListener> attached = new ArrayList<>();

    public void addListener(View.OnClickListener l) {
        attached.add(l);
        System.out.print(attached);
    }

    @Override
    public void onClick(View view) {
        for (View.OnClickListener listener: attached) {
            listener.onClick(view);
        }
    }
}