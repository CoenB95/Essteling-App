package nl.l15vdef.essteling;

import android.support.annotation.NonNull;

/**
 * @author CoenB95
 */

public class Score implements Comparable<Score> {
	private String name;
	private int score;

	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(@NonNull Score o) {
		if (this.score < o.score) return 1;
		else if (this.score > o.score) return -1;
		else return this.name.compareTo(o.name);
	}
}
