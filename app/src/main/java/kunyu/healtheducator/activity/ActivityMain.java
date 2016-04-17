package kunyu.healtheducator.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;


import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import java.util.List;

import kunyu.healtheducator.R;
import kunyu.healtheducator.fragment.FragmentDetail;
import kunyu.healtheducator.fragment.FragmentList;
import kunyu.healtheducator.fragment.FragmentListener;
import kunyu.healtheducator.model.ModelCellEducation;

public class ActivityMain extends AppCompatActivity implements FragmentListener.OnFragmentInteractionListener, FragmentList.OnClickCellItemListener, FragmentDetail.OnClickFloatingButtonListener, FragmentManager.OnBackStackChangedListener{
    private List<ModelCellEducation> mModelCellEducationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( ModelCellEducation.count() ==  0 ) {
            ModelCellEducation.spawnData(20);
        }

        mModelCellEducationList = Select.from(ModelCellEducation.class).list();

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
    public void onClickCellItem(int position) {
        FragmentDetail fragmentDetail = new FragmentDetail();
        Bundle bundle = new Bundle();
        bundle.putInt(FragmentDetail.POSITION_IN_LIST, position);
        fragmentDetail.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(this);
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentDetail);
        fragmentTransaction.addToBackStack(FragmentDetail.class.getName());
        fragmentTransaction.commit();

        Log.i("LOG","count= " + getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClickWebViewFloatingButton(long dataId) {

    }

    public List<ModelCellEducation> getModelCellEducationList() {
        return mModelCellEducationList;
    }

    public void setPositionAtListRead(int positionAtListRead){
        ModelCellEducation modelCellEducation = mModelCellEducationList.get(positionAtListRead);
        modelCellEducation.setRead(true);
        modelCellEducation.save();
    }

    private void shouldDisplayHomeUp(){
        //Enable Up button only if there are entries in the back stack
        boolean canback = getSupportFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if( fragment instanceof FragmentList){
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        else if ( fragment instanceof FragmentDetail){
            FragmentDetail fragmentDetail = (FragmentDetail) fragment ;
            ModelCellEducation modelCellEducation = mModelCellEducationList.get(fragmentDetail.getPosition());
            getSupportActionBar().setTitle(modelCellEducation.getTextTitle());
        }
    }
}
