package nl.l15vdef.essteling.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import nl.l15vdef.essteling.OnScoreClickListener;
import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;
import nl.l15vdef.essteling.ScoreLayoutManager;

public class HomepageActivity extends Fragment {

    private ProgressBar progressBar;
	private RecyclerView scoreRecyclerView;
	private ScoreAdapter scoreAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.activity_homepage, container, false);


	    scoreAdapter = new ScoreAdapter();
	    scoreAdapter.setAll(Arrays.asList(
			    new Score("LiPa450", 1676),
			    new Score("Co-en-co-xxx", 1942),
			    new Score("PietjePuk", 1438),
			    new Score("xxx_360hans_xxx", 1700),
	    		new Score("Jaap1995", 2000)
	    ));
		scoreAdapter.setOnScoreClickListener(new OnScoreClickListener() {
			@Override
			public void onScoreClicked(Score score) {
				Snackbar.make(view, "Score of " + score.getName() + " clicked",
						Snackbar.LENGTH_SHORT).show();
			}
		});

	    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.INVISIBLE);

	    scoreRecyclerView = (RecyclerView) view.findViewById(R.id.scoreboardRecyclerView);
	    scoreRecyclerView.setLayoutManager(new ScoreLayoutManager(getContext())
			    .enableScrolling(false));
	    scoreRecyclerView.setAdapter(scoreAdapter);

		return view;
    }
}
