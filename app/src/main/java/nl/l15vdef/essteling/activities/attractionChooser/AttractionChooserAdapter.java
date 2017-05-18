package nl.l15vdef.essteling.activities.attractionChooser;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities.data.Attraction;

/**
 * Created by Maarten on 18/05/2017.
 */

public class AttractionChooserAdapter extends ArrayAdapter {
    public AttractionChooserAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_about_list_of_attractions_custom_row,parent,false);

        //background color
        //if(position % 2 == 1){
        //    convertView.setBackgroundColor(convertView.getResources().getColor(R.color.colorAccent));
        //}else convertView.setBackgroundColor(convertView.getResources().getColor(android.R.color.background_light));


        ImageView attraction = (ImageView) convertView.findViewById(R.id.activit_about_list_of_attractions_custom_row_attraction_image_id);
        attraction.setImageDrawable(convertView.getResources().getDrawable(
                ((Attraction) getItem(position)).getImage()
        ));

        TextView title = (TextView) convertView.findViewById(R.id.activit_about_list_of_attractions_custom_row_title_attraction);
        title.setText( ((Attraction) getItem(position)).getName());


        //dummy
        TextView amountOfplayers = (TextView) convertView.findViewById(R.id.activit_about_list_of_attractions_custom_row_amount_online_players_id);
        amountOfplayers.setText("70 players (Dummhy)");

        return convertView;
    }
}
