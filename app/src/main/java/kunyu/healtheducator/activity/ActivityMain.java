package kunyu.healtheducator.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import kunyu.healtheducator.R;
import kunyu.healtheducator.fragment.FragmentList;
import kunyu.healtheducator.fragment.FragmentListener;

public class ActivityMain extends AppCompatActivity implements FragmentListener.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            FragmentList fragmentList = new FragmentList();
            fragmentList.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fragmentList).commit();
        }
    }

    @Override
    public void launchAction(String action) {

    }
}
