package kunyu.healtheducator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by QuentinTsai on 17/04/2016.
 */
public class ObservableWebView extends WebView
{
    private OnScrollChangedCallback mOnScrollChangedCallback;
    private int currentScrollX = 0;
    private int currentScrollY = 0;

    public ObservableWebView(final Context context)
    {
        super(context);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ObservableWebView(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt)
    {
        super.onScrollChanged(l, t, oldl, oldt);
        if(mOnScrollChangedCallback != null) mOnScrollChangedCallback.onScroll(l, t);
    }

    public OnScrollChangedCallback getOnScrollChangedCallback()
    {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback)
    {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }

    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback
    {
        public void onScroll(int l, int t);
    }

    public int getCurrentScrollX() {
        return currentScrollX;
    }

    public void setCurrentScrollX(int currentScrollX) {
        this.currentScrollX = currentScrollX;
    }

    public int getCurrentScrollY() {
        return currentScrollY;
    }

    public void setCurrentScrollY(int currentScrollY) {
        this.currentScrollY = currentScrollY;
    }
}
