package nl.l15vdef.essteling.activities_and_fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Handler;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;
import nl.l15vdef.essteling.ScoreLayoutManager;
import nl.l15vdef.essteling.data.ScoreReceiver;

public class AttractionDetailFragment extends Fragment {
    private ImageView imageView;
    private ScoreReceiver SR;
    private ArrayList<Score> scoreList = new ArrayList<>();
    private ArrayList<Score> dayScore, weekScore, monthScore, allTimeScore;
    private int pos;
    @DrawableRes
    int imageRes = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_attraction_detail, container, false);

        // get the reference of TabLayout
        final TabLayout tabLayout = (TabLayout) view.findViewById(R.id.TabLayout_id);

        // Create new Tabs
        TabLayout.Tab firstTab = tabLayout.newTab();
        TabLayout.Tab secondTab = tabLayout.newTab();
        TabLayout.Tab thirdTab = tabLayout.newTab();
        TabLayout.Tab fourthTab = tabLayout.newTab();

        // set Text for the Tabs
        firstTab.setText("Daily");
        secondTab.setText("Weekly");
        thirdTab.setText("Monthly");
        fourthTab.setText("All Time");

        // add tabs to the TabLayout
        tabLayout.addTab(firstTab);
        tabLayout.addTab(secondTab);
        tabLayout.addTab(thirdTab);
        tabLayout.addTab(fourthTab);

        // create custom adapter
        final ScoreAdapter scoreAdapater = new ScoreAdapter();

        // create the arraylists with data
        dayScore = new ArrayList<>();
        weekScore = new ArrayList<>();
        monthScore = new ArrayList<>();
        allTimeScore = new ArrayList<>();

        // fill arraylists with scores from database
        getScore("Anaconda", 0);
        getScore("Anaconda", 1);
        getScore("Anaconda", 2);
        getScore("Anaconda", 3);

        // create recycleview
        final RecyclerView scoreRecyclerView = createRecyclerView(R.id.scoreboardDayRecyclerView,
                view, scoreAdapater);

        // Tab selected listener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        pos = 0;
                        break;
                    case 1:
                        pos = 1;
                        break;
                    case 2:
                        pos = 2;
                        break;
                    case 3:
                        pos = 3;
                        break;

                }
                scoreRecyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        // Set icon bottom of the page
        imageView = (ImageView) view.findViewById(R.id.mapImageView);
        if (imageRes >= 0)
            imageView.setImageResource(imageRes);

        // Handler for setting adapter data
        final android.os.Handler h = new android.os.Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                switch (pos) {
                    case 0:
                        scoreAdapater.setAll(dayScore);
                        break;
                    case 1:
                        scoreAdapater.setAll(weekScore);
                        break;
                    case 2:
                        scoreAdapater.setAll(monthScore);
                        break;
                    case 3:
                        scoreAdapater.setAll(allTimeScore);
                        break;
                }
                h.postDelayed(this, 200);

            }
        };
        h.post(r);
        return view;
    }

    private RecyclerView createRecyclerView(@IdRes int viewId, View view, ScoreAdapter adapter) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(viewId);
        recyclerView.setLayoutManager(new ScoreLayoutManager(getContext()).enableScrolling(true));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    public void setImageRes(@DrawableRes int img) {
        imageRes = img;
        if (imageView != null)
            imageView.setImageResource(img);
    }

    // Naam = naam attractie in database, int frequentie = dag/week/maand/alltime voor 0-3;
    public ArrayList<Score> getScore(String naam, final int frequentie) {
        SR = new ScoreReceiver(naam);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> tempScoreList = new ArrayList<>();
                try {
                    switch (frequentie) {
                        case 0:
                            dayScore.clear();
                            tempScoreList.clear();
                            tempScoreList.addAll(SR.geefHuidigeDatumScores());
                            dayScore.addAll(scoreParsing(tempScoreList));
                            System.out.println("lengte" + dayScore.size());
                            break;
                        case 1:
                            weekScore.clear();
                            tempScoreList.clear();
                            tempScoreList.addAll(SR.geefWeekScores());
                            weekScore.addAll(scoreParsing(tempScoreList));
                            break;
                        case 2:
                            monthScore.clear();
                            tempScoreList.clear();
                            tempScoreList.addAll(SR.geefMaandScores());
                            monthScore.addAll(scoreParsing(tempScoreList));
                            break;
                        case 3:
                            allTimeScore.clear();
                            tempScoreList.clear();
                            tempScoreList.addAll(SR.geefAllTimeScores());
                            allTimeScore.addAll(scoreParsing(tempScoreList));
                            break;
                    }

                } catch (ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        return scoreList;
    }

    // Data uit database parsen naar scores
    public ArrayList<Score> scoreParsing(ArrayList<String> tempList) {
        ArrayList<Score> tempScoreList = new ArrayList<>();
        for (String s : tempList) {
            String[] splits = s.split("~");
            Score tempScore = new Score(splits[1], Integer.valueOf(splits[2]));
            tempScoreList.add(tempScore);
        }
        return tempScoreList;
    }
}
