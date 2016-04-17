package kunyu.healtheducator;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by QuentinTsai on 17/04/2016.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceInPx;
    public DividerItemDecoration(int spaceInPx) {
        this.spaceInPx = spaceInPx;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = spaceInPx;
        outRect.left = spaceInPx;
        outRect.right = spaceInPx;
        outRect.bottom = spaceInPx;
    }
}
