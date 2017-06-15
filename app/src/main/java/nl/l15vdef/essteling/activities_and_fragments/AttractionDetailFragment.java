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
    @DrawableRes
    int imageRes = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_attraction_detail, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.TabLayout_id); // get the reference of TabLayout
        TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names "First Tab"
        TabLayout.Tab secondTab = tabLayout.newTab();
        TabLayout.Tab thirdTab = tabLayout.newTab();
        TabLayout.Tab fourthTab = tabLayout.newTab();
        firstTab.setText("Daily"); // set the Text for the first Tab
        secondTab.setText("Weekly");
        thirdTab.setText("Monthly");
        fourthTab.setText("All Time");
        tabLayout.addTab(firstTab); // add  the tab to the TabLayout
        tabLayout.addTab(secondTab);
        tabLayout.addTab(thirdTab);
        tabLayout.addTab(fourthTab);


       final ScoreAdapter scoreDayAdapter = new ScoreAdapter();
            SR = new ScoreReceiver("Araconda");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<String> tempScoreList = null;
                    try {
                        tempScoreList = SR.geefHuidigeDatumScores();
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    for (String s : tempScoreList) {
                        String[] splits = s.split("~");
                        Score tempScore = new Score(splits[1],Integer.valueOf(splits[2]));
                        scoreList.add(tempScore);
                        System.out.println(tempScore);
                    }
                }
            });
            t.start();



        ScoreAdapter scoreWeekAdapter = createDummyAdapter();
        ScoreAdapter scoreYearAdapter = createDummyAdapter();
        final ScoreAdapter scoreAllAdapter = createDummyAdapter();

        final RecyclerView scoreDayRecyclerView = createRecyclerView(R.id.scoreboardDayRecyclerView,
                view, scoreDayAdapter);
//        RecyclerView scoreWeekRecyclerView = createRecyclerView(R.id.scoreboardWeekRecyclerView,
//                view, scoreWeekAdapter);
//        RecyclerView scoreYearRecyclerView = createRecyclerView(R.id.scoreboardYearRecyclerView,
//                view, scoreYearAdapter);
//        RecyclerView scoreAllRecyclerView = createRecyclerView(R.id.scoreboardAllRecyclerView,
//                view, scoreAllAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        scoreDayRecyclerView.setBackgroundColor(Color.RED);
                        break;
                    case 1:
                        scoreDayRecyclerView.setBackgroundColor(Color.BLUE);
                        break;
                    case 2:
                        scoreDayRecyclerView.setBackgroundColor(Color.YELLOW);
                        break;
                    case 3:
                        scoreDayRecyclerView.setBackgroundColor(Color.GREEN);
                        break;

                }
                scoreDayRecyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }


        });

        imageView = (ImageView) view.findViewById(R.id.mapImageView);
        if (imageRes >= 0)
            imageView.setImageResource(imageRes);

        final android.os.Handler h = new android.os.Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                scoreDayAdapter.setAll(scoreList);
                h.postDelayed(this,1000);
                Log.d("doe"," ik dit");
            }
        };
        h.post(r);


        return view;
    }

    private RecyclerView createRecyclerView(@IdRes int viewId, View view, ScoreAdapter adapter) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(viewId);
        recyclerView.setLayoutManager(new ScoreLayoutManager(getContext()).enableScrolling(false));
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    private ScoreAdapter createDummyAdapter() {
        ScoreAdapter a = new ScoreAdapter();
        a.setAll(Arrays.asList(
                new Score("LiPa450", 1676),
                new Score("Co-en-co-xxx", 1942),
                new Score("PietjePuk", 1438),
                new Score("xxx_360hans_xxx", 1700),
                new Score("Jaap1995", 2000)
        ));
        return a;
    }

    public void setImageRes(@DrawableRes int img) {
        imageRes = img;
        if (imageView != null)
            imageView.setImageResource(img);
    }
}
