package nl.l15vdef.essteling.data;

import android.os.Handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.activities_and_fragments.AttractionDetailFragment;

/**
 * Created by Maarten on 16/06/2017.
 */

public class ScoreData {
    private List<Score> anaconda;
    private List<Score> sjoris_en_de_draak;
    private List<Score> vliegende_duitser;
    private List<Score> vogel_jazz;

    private ScoreReceiver anacondaSr;
    private ScoreReceiver sjoris_en_de_draakSr;
    private ScoreReceiver vliegende_duitserSr;
    private ScoreReceiver vogel_jazzSr;

    private boolean isDone;


    public ScoreData() {
        anaconda = new ArrayList<>();
        sjoris_en_de_draak = new ArrayList<>();
        vliegende_duitser = new ArrayList<>();
        vogel_jazz = new ArrayList<>();

        anacondaSr = new ScoreReceiver("Anaconda");
        sjoris_en_de_draakSr = new ScoreReceiver("SjorisendeDraak");
        vliegende_duitserSr = new ScoreReceiver("VliegendeDuitser");
        vogel_jazzSr = new ScoreReceiver("Vogeljazz");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    anaconda = AttractionDetailFragment.scoreParsing(anacondaSr.geefAllTimeScores());
                    sjoris_en_de_draak = AttractionDetailFragment.scoreParsing(sjoris_en_de_draakSr.geefAllTimeScores());
                    vliegende_duitser = AttractionDetailFragment.scoreParsing(vliegende_duitserSr.geefAllTimeScores());
                    vogel_jazz = AttractionDetailFragment.scoreParsing(vogel_jazzSr.geefAllTimeScores());
                    isDone = true;
                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        t.start();
    }

    public List<Score> getAnaconda() {
        return anaconda;
    }

    public List<Score> getSjoris_en_de_draak() {
        return sjoris_en_de_draak;
    }

    public List<Score> getVliegende_duitser() {
        return vliegende_duitser;
    }

    public List<Score> getVogel_jazz() {
        return vogel_jazz;
    }

    public boolean isDone() {
        return isDone;
    }

    public Collection<Score> get(int index) {
        switch (index){
            case 0: return anaconda;
            case 1: return sjoris_en_de_draak;
            case 2: return vliegende_duitser;
        }
        return vogel_jazz;
    }
}
