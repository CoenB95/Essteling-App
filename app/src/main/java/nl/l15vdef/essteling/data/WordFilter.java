package nl.l15vdef.essteling.data;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nl.l15vdef.essteling.R;

/**
 * Created by Maarten on 16/06/2017.
 */

public class WordFilter implements Runnable{
    private List<String> bannedwords;
    private Activity a;
    private boolean done;

    public WordFilter(Activity a) {
        this.a =a;
        this.done = false;
        bannedwords = new ArrayList<>();
        Handler h = new Handler();
        h.post(this);
    }

    @Override
    public void run() {
        InputStream in = a.getResources().openRawResource(R.raw.banned_words);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                bannedwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("WordFilter", "DONE loading words.");
        done  =true;
    }

    public boolean isDone() {
        return done;
    }

    public List<String> getBannedwords() {
        return bannedwords;
    }
}
