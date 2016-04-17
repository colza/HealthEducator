package kunyu.healtheducator.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.squareup.picasso.Picasso;

import kunyu.healtheducator.R;
import kunyu.healtheducator.fragment.FragmentDetail;
import kunyu.healtheducator.fragment.FragmentList;
import kunyu.healtheducator.fragment.FragmentListener;

public class ActivityMain extends AppCompatActivity implements FragmentListener.OnFragmentInteractionListener, FragmentList.OnClickCellItemListener{

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

    @Override
    public void onClickCellItem(long dataId) {
        FragmentDetail fragmentDetail = new FragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putLong("dataId", dataId);
        fragmentDetail.setArguments(bundle);

        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentDetail);
        fragmentTransaction.addToBackStack(FragmentDetail.class.getName());
        fragmentTransaction.commit();
    }
}
