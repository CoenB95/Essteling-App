package nl.l15vdef.essteling.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;
import nl.l15vdef.essteling.ScoreLayoutManager;
import nl.l15vdef.essteling.bluetooth.BluetoothInRangeChanged;
import nl.l15vdef.essteling.bluetooth.BluetoothInRangeDetector;
import nl.l15vdef.essteling.bluetooth.BluetoothNotAvailableException;
import nl.l15vdef.essteling.bluetooth.LocationPermissionNotExceptedException;
import nl.l15vdef.essteling.data.Attraction;

public class HomepageActivity extends Fragment {

    private ProgressBar progressBar;
	private RecyclerView scoreRecyclerView;
	private ScoreAdapter scoreAdapter;
	private BluetoothInRangeDetector bird;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_homepage, container, false);

		Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
		toolbar.setTitle("Home");
	    scoreAdapter = new ScoreAdapter();
	    scoreAdapter.setAll(Arrays.asList(
			    new Score("LiPa450", 1676),
			    new Score("Co-en-co-xxx", 1942),
			    new Score("PietjePuk", 1438),
			    new Score("xxx_360hans_xxx", 1700),
	    		new Score("Jaap1995", 2000)
	    ));

		List<String> strings = new ArrayList<>();
		final List<String> attractions = new ArrayList<>();
		final ArrayAdapter<String> attractionslistAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,attractions);
		for (Attraction attraction : Attraction.getAttractions()) {
			strings.add(attraction.getName());
			strings.add("NLQUIST02");
		}

	    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		try {
			bird = new BluetoothInRangeDetector(new BluetoothInRangeChanged() {
                @Override
                public void bluetoothChecked(Map<String, Boolean> inRange) {
					attractions.clear();
					for (String s : inRange.keySet()) {
						if(inRange.get(s)) {
							attractions.add(s);
							Log.d(getTag() + "Bluetooth", s + " was found");
						}
					}
					if(attractions.size() <= 0){
						progressBar.setVisibility(View.VISIBLE);
					}else progressBar.setVisibility(View.INVISIBLE);
					attractionslistAdapter.notifyDataSetChanged();
                }
            }, strings, getActivity(), 10000);
		} catch (BluetoothNotAvailableException | LocationPermissionNotExceptedException e) {
			e.printStackTrace();
		}

		ListView attractionsList  = (ListView) view.findViewById(R.id.fragement_homescreen_attractionslist_id);
		attractionsList.setAdapter(attractionslistAdapter);

		progressBar.getIndeterminateDrawable().setColorFilter(
				Color.rgb(184,55,139), android.graphics.PorterDuff.Mode.SRC_IN);
		progressBar.setVisibility(View.VISIBLE);

		scoreRecyclerView = (RecyclerView) view.findViewById(R.id.scoreboardRecyclerView);
	    scoreRecyclerView.setLayoutManager(new ScoreLayoutManager(getContext())
			    .enableScrolling(false));
	    scoreRecyclerView.setAdapter(scoreAdapter);

		return view;
    }

	@Override
	public void onDetach() {
		super.onDetach();
		if (bird != null) bird.stop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (bird != null) bird.stop();
	}
}
