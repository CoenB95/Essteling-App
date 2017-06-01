package nl.l15vdef.essteling.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.Score;
import nl.l15vdef.essteling.ScoreAdapter;
import nl.l15vdef.essteling.bluetooth.BluetoothInRangeChanged;
import nl.l15vdef.essteling.bluetooth.BluetoothInRangeDetector;
import nl.l15vdef.essteling.bluetooth.BluetoothNotAvailableException;
import nl.l15vdef.essteling.bluetooth.LocationPermissionNotExceptedException;
import nl.l15vdef.essteling.data.Attraction;

public class HomepageActivity extends Fragment {

    private ProgressBar progressBar;
	private RecyclerView scoreRecyclerView;
	private ScoreAdapter scoreAdapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_homepage, container, false);


	    scoreAdapter = new ScoreAdapter();
	    scoreAdapter.setAll(Arrays.asList(
			    new Score("LiPa450", 1676),
			    new Score("Co-en-co-xxx", 1942),
			    new Score("PietjePuk", 1438),
			    new Score("xxx_360hans_xxx", 1700),
	    		new Score("Jaap1995", 2000)
	    ));
		List<String> strings = new ArrayList<>();
		for (Attraction attraction : Attraction.getAttractions()) {
			strings.add(attraction.getName().replaceAll(" ",""));
			Log.d("test",attraction.getName().replaceAll(" ",""));
		}



	    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		BluetoothInRangeDetector bird;

		try {
			bird = new BluetoothInRangeDetector(new BluetoothInRangeChanged() {
                @Override
                public void bluetoothChecked(Map<String, Boolean> inRange) {
					for (String s : inRange.keySet()) {
						if(inRange.get(s))
							Log.d(getTag() + "Bluetooth",s + " was found");
					}
                }
            },strings,getActivity(),15000);
		} catch (BluetoothNotAvailableException e) {
			e.printStackTrace();
			//getActivity().finish();
		} catch (LocationPermissionNotExceptedException e) {
			e.printStackTrace();
		}



		progressBar.getIndeterminateDrawable().setColorFilter(
				Color.rgb(184,55,139), android.graphics.PorterDuff.Mode.SRC_IN);
		progressBar.setVisibility(View.VISIBLE);


	    scoreRecyclerView = (RecyclerView) view.findViewById(R.id.scoreboardRecyclerView);
	    scoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
	    scoreRecyclerView.setAdapter(scoreAdapter);

		return view;
    }
}
