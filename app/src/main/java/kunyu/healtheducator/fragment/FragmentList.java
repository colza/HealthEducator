package kunyu.healtheducator.fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Select;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.svenjacobs.loremipsum.LoremIpsum;
import kunyu.healtheducator.DividerItemDecoration;
import kunyu.healtheducator.R;
import kunyu.healtheducator.activity.ActivityMain;
import kunyu.healtheducator.model.ModelCellEducation;
import kunyu.healtheducator.utils.ImageViewHandler;

public class FragmentList extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapaterEducation mAdapaterEducation;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private OnClickCellItemListener mOnClickCellItemListener;
    private FragmentListener.OnFragmentInteractionListener mListener;

    public FragmentList() {
        // Required empty public constructor
    }

    public interface OnClickCellItemListener{
        public void onClickCellItem(int adapterPosition);
    }

    public void setDataIdAsRead(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvEducationTopic);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getResources().getDimensionPixelOffset(R.dimen.space_between_item)));

        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        if( getActivity() instanceof ActivityMain){
            ActivityMain activityMain = (ActivityMain) getActivity();
            mAdapaterEducation = new AdapaterEducation(activityMain.getModelCellEducationList());
            mRecyclerView.setAdapter(mAdapaterEducation);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClickCellItemListener) {
            mOnClickCellItemListener = (OnClickCellItemListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentListener");
        }

        if (context instanceof FragmentListener.OnFragmentInteractionListener) {
            mListener = (FragmentListener.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickCellItemListener = null;
        mListener = null;
    }

    private class AdapaterEducation extends RecyclerView.Adapter<AdapaterEducation.MyViewHolder>{
        private List<ModelCellEducation> list;

        public AdapaterEducation(List<ModelCellEducation> list) {
            this.list = list;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            public View itemView;
            public ImageView imageView;
            public TextView title;
            public TextView content;

            public MyViewHolder(View itemView) {
                super(itemView);

                this.itemView = itemView;
                imageView = (ImageView) itemView.findViewById(R.id.cell_image);
                title = (TextView)  itemView.findViewById(R.id.cell_title);
                content = (TextView)  itemView.findViewById(R.id.cell_content);
                itemView.setOnClickListener(this);
            }

            public void setData(ModelCellEducation modelCellEducation){
                Picasso.with(imageView.getContext()).load(modelCellEducation.getImageUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(imageView);
                title.setText(modelCellEducation.getTextTitle());
                content.setText(modelCellEducation.getTextContent());
                content.setMaxLines(modelCellEducation.maxLine);

                if( modelCellEducation.getRead() == true ) {
                    content.setVisibility(View.GONE);
                    ImageViewHandler.greyLock(imageView);
                }else{
                    content.setVisibility(View.VISIBLE);
                    ImageViewHandler.greyUnlock(imageView);
                }
            }

            @Override
            public void onClick(View view) {
                mOnClickCellItemListener.onClickCellItem(getAdapterPosition());
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_education, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.setData(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
