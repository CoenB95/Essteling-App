package nl.l15vdef.essteling.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;

public class HomepageActivity extends AppCompatActivity {

    private ProgressBar progressBar;
	private RecyclerView scoreRecyclerView;
	private ScoreAdapter scoreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

	    scoreAdapter = new ScoreAdapter();
	    scoreAdapter.setAll(Arrays.asList(
			    new Score("LiPa450", 1676),
			    new Score("Co-en-co-xxx", 1942),
			    new Score("PietjePuk", 1438),
			    new Score("xxx_360hans_xxx", 1700),
	    		new Score("Jaap1995", 2000)
	    ));

	    progressBar = (ProgressBar) findViewById(R.id.progressBar);
	    scoreRecyclerView = (RecyclerView) findViewById(R.id.scoreboardRecyclerView);
	    scoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	    scoreRecyclerView.setAdapter(scoreAdapter);

	    progressBar.setVisibility(View.INVISIBLE);
    }
}
