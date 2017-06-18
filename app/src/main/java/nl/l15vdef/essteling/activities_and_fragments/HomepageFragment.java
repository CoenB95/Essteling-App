package nl.l15vdef.essteling.activities_and_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import nl.l15vdef.essteling.data.ScoreData;
import nl.l15vdef.essteling.game.GameScreenActivity;

public class HomepageFragment extends Fragment {

    private ProgressBar progressBar;
	private RecyclerView scoreRecyclerView;
	private ScoreAdapter scoreAdapter;
    private TextView scoreAttractionName;
	private BluetoothInRangeDetector bird;

    private ScoreData scoreData;
    private long prev;
    private int index;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_homepage, container, false);
		getActivity().setTitle(getResources().getString(R.string.home));

        scoreAttractionName = (TextView) view.findViewById(R.id.menu_ondertitel);
        scoreAttractionName.setText(getString(R.string.loading));

        scoreAdapter = new ScoreAdapter();

        scoreData = new ScoreData();

		List<String> strings = new ArrayList<>();
		final List<String> attractions = new ArrayList<>();
		final ArrayAdapter<String> attractionslistAdapter = new ArrayAdapter<>(getActivity(),
				R.layout.activity_nearby_attractions_row, R.id.nearbyNameField, attractions);
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
                        if (inRange.get(s)) {
                            attractions.add(s);
                            Log.d(getTag() + "Bluetooth", s + " was found");
                        }
                    }
                    if (attractions.size() <= 0) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else progressBar.setVisibility(View.INVISIBLE);
                    attractionslistAdapter.notifyDataSetChanged();
                }

                @Override
                public void bluetoothDeviceFoundandWasCheckedFor(String deviceName) {
                    if (!attractions.contains(deviceName)) {
                        attractions.add(deviceName);
                        Log.d(getTag() + "Bluetooth", deviceName + " was found");
                        progressBar.setVisibility(View.INVISIBLE);
                        attractionslistAdapter.notifyDataSetChanged();
                    }
                }
            }, strings, getActivity(), 10000);
        } catch (BluetoothNotAvailableException | LocationPermissionNotExceptedException e) {
            e.printStackTrace();
        }

        ListView attractionsList = (ListView) view.findViewById(R.id.fragement_homescreen_attractionslist_id);
        attractionsList.setAdapter(attractionslistAdapter);

        attractionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), GameScreenActivity.class);
                startActivity(i);
            }
        });

        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.colorAccent2), android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.VISIBLE);

        scoreRecyclerView = (RecyclerView) view.findViewById(R.id.scoreboardRecyclerView);
        scoreRecyclerView.setLayoutManager(new ScoreLayoutManager(getContext())
                .enableScrolling(false));
        scoreRecyclerView.setAdapter(scoreAdapter);
        scoreRecyclerView.setItemAnimator(new DefaultItemAnimator());


        final Handler h = new Handler();
        index = 0;
        prev = 0;
        h.post(new Runnable() {
            @Override
            public void run() {
                if(scoreData.isDone()){
                    if(prev == 0){
                        scoreAdapter.setAll(scoreData.getAnaconda());
                        scoreAttractionName.setText("Anaconda- Best of all Time");
                        index = 0;
                        prev = System.currentTimeMillis();

                    }else {
                        long diffTime = System.currentTimeMillis() - prev;
                        if (diffTime >= 10000) {
                            prev = System.currentTimeMillis();
                            scoreAdapter.setAll(scoreData.get(index));
                            switch (index){
                                case 0: scoreAttractionName.setText("Anaconda- Best of all Time"); break;
                                case 1: scoreAttractionName.setText("Sjoris en de Draak - Best of all Time"); break;
                                case 2: scoreAttractionName.setText("Vliegende Duitser - Best off all Time"); break;
                                case 3: scoreAttractionName.setText("Vogel jazz - Best off all Time"); break;
                            }
                            index++;
                            if (index > 3) {
                                index = 0;
                            }
                        }
                    }
                }
                h.postDelayed(this,200);
            }
        });


        return view;
    }




    @Override
    public void onStop() {
        super.onStop();
        Log.d("stop", "stop");
        if (bird != null) bird.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bird != null)
            bird.resume();
    }

    public BluetoothInRangeDetector getBird() {
        return bird;
    }
}
