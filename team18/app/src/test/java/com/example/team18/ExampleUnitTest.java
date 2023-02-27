package com.example.team18;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void difficulty_selection() {
        String dif = LoginActivity.selectedDiff;
        int position = LoginActivity.position;
        System.out.println("position" + position);
        System.out.println(dif);
        if (dif != null) {
            if (position == 0) {
                assertEquals("EASY", dif);
            } else if (position == 1) {
                assertEquals("MEDIUM", dif);
            } else {
                assertEquals("HARD", dif);
            }
        } else {
            System.out.println("no action so far");
        }
    }


}