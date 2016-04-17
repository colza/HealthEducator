package kunyu.healtheducator.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import kunyu.healtheducator.ObservableWebView;
import kunyu.healtheducator.R;
import kunyu.healtheducator.activity.ActivityMain;

public class FragmentDetail extends Fragment {
    private ProgressBar mProgressBar;
    private ObservableWebView mWebView;
    private FloatingActionButton mFloatingActionButton;
    private FragmentListener.OnFragmentInteractionListener mListener;
    private OnClickFloatingButtonListener mFloatingButtonListener;
    public static final String POSITION_IN_LIST = "POSITION_IN_LIST";

    private int position;

    public interface OnClickFloatingButtonListener{
        public void onClickWebViewFloatingButton(long dataId);
    }

    public FragmentDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(POSITION_IN_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setMax(100);

        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingButton);

        mWebView = (ObservableWebView) view.findViewById(R.id.webContent);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(View.GONE);
                mProgressBar.setProgress(100);
                view.clearCache(true);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                mProgressBar.setProgress(newProgress);
            }
        });

        mWebView.setOnScrollChangedCallback(new ObservableWebView.OnScrollChangedCallback() {
            @Override
            public void onScroll(int scrollXPosition, int scrollYPosition) {
                if( mFloatingActionButton.getVisibility() == View.VISIBLE && scrollYPosition > mWebView.getCurrentScrollY()){
                    mFloatingActionButton.hide();
                } else if (mFloatingActionButton.getVisibility() != View.VISIBLE && scrollYPosition < mWebView.getCurrentScrollY() ) {
                    mFloatingActionButton.show();
                }

                mWebView.setCurrentScrollY(scrollYPosition);
            }
        });

        mWebView.loadUrl("https://en.wikipedia.org/");

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LOG","count= " + getActivity().getSupportFragmentManager().getBackStackEntryCount());
                if( getActivity() instanceof ActivityMain){
                    ActivityMain activityMain = (ActivityMain) getActivity();
                    activityMain.setPositionAtListRead(position);
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener.OnFragmentInteractionListener) {
            mListener = (FragmentListener.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentListener");
        }

        if (context instanceof OnClickFloatingButtonListener) {
            mFloatingButtonListener = (OnClickFloatingButtonListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public int getPosition() {
        return position;
    }
}
