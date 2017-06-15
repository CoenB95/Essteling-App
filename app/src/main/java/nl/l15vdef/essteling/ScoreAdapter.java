package nl.l15vdef.essteling;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author CoenB95
 */

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreHolder> {

    private OnScoreClickListener onItemClickListener;
    private List<Score> scores;

    public ScoreAdapter() {
        scores = new ArrayList<>();
    }

    @Override
    public ScoreHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScoreHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_scoreboard, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoreHolder holder, int position) {
        holder.setScore(position + 1, scores.get(position));
    }

    @Override
    public int getItemCount() {
        return scores.size();
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setAll(Collection<Score> values) {
        scores.clear();
        scores.addAll(values);
        Collections.sort(scores);
        notifyDataSetChanged();
    }

    public void setOnScoreClickListener(OnScoreClickListener listener) {
        onItemClickListener = listener;
    }

    public class ScoreHolder extends RecyclerView.ViewHolder {

        private Score score;
        private TextView indexTextView;
        private TextView nameTextView;
        private TextView scoreTextView;

        public ScoreHolder(View itemView) {
            super(itemView);
            ConstraintLayout layout = (ConstraintLayout) itemView.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) onItemClickListener.onScoreClicked(score);
                }
            });
            indexTextView = (TextView) itemView.findViewById(R.id.scoreIndexTextView);
            indexTextView.setTextSize(20f);
            nameTextView = (TextView) itemView.findViewById(R.id.scoreNameTextView);
            nameTextView.setTextSize(20f);
            scoreTextView = (TextView) itemView.findViewById(R.id.scorePointsTextView);
            scoreTextView.setTextSize(20f);
        }

        public void setScore(int index, Score value) {
            this.score = value;
            indexTextView.setText(String.valueOf(index));
            nameTextView.setText(score.getName());
            scoreTextView.setText(String.valueOf(score.getScore()));
        }


    }
}
