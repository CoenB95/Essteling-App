package nl.l15vdef.essteling.activities.helpMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.l15vdef.essteling.R;
import nl.l15vdef.essteling.activities.data.FAQ;

/**
 * Created by Arjan on 18-5-2017.
 */

public class HelpMenuAdapter extends ArrayAdapter {
    public HelpMenuAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_help_menu_row,parent,false);


        TextView question = (TextView) convertView.findViewById(R.id.helpMenu_Row_question);
        TextView answer = (TextView) convertView.findViewById(R.id.helpMenu_Row_answer);

        question.setText( ((FAQ) getItem(position)).getQuestion());
        answer.setText( ((FAQ) getItem(position)).getAnswer());

        return convertView;
    }
}
