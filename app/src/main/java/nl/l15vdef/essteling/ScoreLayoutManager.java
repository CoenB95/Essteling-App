package nl.l15vdef.essteling;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * @author CoenB95
 */

public class ScoreLayoutManager extends LinearLayoutManager {

	private boolean scrollingEnabled = true;

	public ScoreLayoutManager(Context context) {
		super(context);
	}

	public ScoreLayoutManager enableScrolling(boolean value) {
		scrollingEnabled = value;
		return this;
	}

	@Override
	public boolean canScrollVertically() {
		return scrollingEnabled && super.canScrollVertically();
	}

}
