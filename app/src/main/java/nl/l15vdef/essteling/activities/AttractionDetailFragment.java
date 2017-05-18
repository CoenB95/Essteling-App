package nl.l15vdef.essteling.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;

public class AttractionDetailFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_attraction_detail, container, false);

		ScoreAdapter scoreDayAdapter = createDummyAdapter();
		ScoreAdapter scoreWeekAdapter = createDummyAdapter();
		ScoreAdapter scoreYearAdapter = createDummyAdapter();
		ScoreAdapter scoreAllAdapter = createDummyAdapter();

		RecyclerView scoreDayRecyclerView = createRecyclerView(view, scoreDayAdapter);
		RecyclerView scoreWeekRecyclerView = createRecyclerView(view, scoreWeekAdapter);
		RecyclerView scoreYearRecyclerView = createRecyclerView(view, scoreYearAdapter);
		RecyclerView scoreAllRecyclerView = createRecyclerView(view, scoreAllAdapter);

		return view;
    }

    private RecyclerView createRecyclerView(View view, ScoreAdapter adapter) {
	    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.scoreboardRecyclerView);
	    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
}
