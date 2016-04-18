package kunyu.healtheducator.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.orm.query.Select;

import java.util.List;
import java.util.Random;

import kunyu.healtheducator.R;
import kunyu.healtheducator.fragment.FragmentDetail;
import kunyu.healtheducator.fragment.FragmentList;
import kunyu.healtheducator.model.ModelCellEducation;

public class ActivityMain extends AppCompatActivity implements FragmentList.OnClickCellItemListener, FragmentList.OnClickFloatingButton, FragmentManager.OnBackStackChangedListener{
    private final int ARTICLE_SIZE = 30;
    private List<ModelCellEducation> mModelCellEducationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if( ModelCellEducation.count() ==  0 ) {
            ModelCellEducation.spawnData(getResources().getStringArray(R.array.webAddresses),ARTICLE_SIZE);
        }
        mModelCellEducationList = Select.from(ModelCellEducation.class).list();

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.phone_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            FragmentList fragmentList = new FragmentList();
            fragmentList.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction().add(R.id.phone_container, fragmentList).commit();
        }
    }

    @Override
    public void onClickCellItem(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_detail);

        if( fragment != null && fragment instanceof FragmentDetail){
            FragmentDetail fragmentDetail = (FragmentDetail) fragment;
            fragmentDetail.setPosition(position);
            fragmentDetail.updateContent();
        }else{
            FragmentDetail fragmentDetail = new FragmentDetail();
            Bundle bundle = new Bundle();
            bundle.putInt(FragmentDetail.POSITION_IN_LIST, position);
            fragmentDetail.setArguments(bundle);

            fragmentManager.addOnBackStackChangedListener(this);
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.phone_container, fragmentDetail);
            fragmentTransaction.addToBackStack(FragmentDetail.class.getName());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public List<ModelCellEducation> getModelCellEducationList() {
        return mModelCellEducationList;
    }

    public void setPositionAtListRead(int positionAtListRead){
        ModelCellEducation modelCellEducation = mModelCellEducationList.get(positionAtListRead);
        modelCellEducation.setRead(true);
        modelCellEducation.save();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        if( fragment != null && fragment instanceof FragmentList){
            FragmentList fragmentList = (FragmentList) fragment;
            fragmentList.refreshView();

        }
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
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.phone_container);
        if( fragment instanceof FragmentList){
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        else if ( fragment instanceof FragmentDetail){
            FragmentDetail fragmentDetail = (FragmentDetail) fragment ;
            ModelCellEducation modelCellEducation = mModelCellEducationList.get(fragmentDetail.getPosition());
            getSupportActionBar().setTitle(modelCellEducation.getTextTitle());
        }
    }

    @Override
    public void onClickListFloatingButton() {
        View view = findViewById(R.id.phone_container);
        if( view == null)
            view = findViewById(R.id.tablet_container);

        String[] greetings = getResources().getStringArray(R.array.greetings);
        Snackbar.make(view, greetings[new Random().nextInt(greetings.length)], Snackbar.LENGTH_LONG).show();
    }
}
