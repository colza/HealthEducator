package kunyu.healtheducator.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.query.Select;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.svenjacobs.loremipsum.LoremIpsum;
import kunyu.healtheducator.R;
import kunyu.healtheducator.model.ModelCellEducation;

public class FragmentList extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdapaterEducation mAdapaterEducation;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentListener.OnFragmentInteractionListener mListener;

    public FragmentList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentList newInstance(String param1, String param2) {
        FragmentList fragment = new FragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

        if( ModelCellEducation.count() ==  0 ) {
            ModelCellEducation.spawnData(5);
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvEducationTopic);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapaterEducation = new AdapaterEducation();
        mRecyclerView.setAdapter(mAdapaterEducation);

        List<ModelCellEducation> list = Select.from(ModelCellEducation.class).list();
        mAdapaterEducation.attachList(list);

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class AdapaterEducation extends RecyclerView.Adapter<AdapaterEducation.MyViewHolder>{
        private List<ModelCellEducation> list = new ArrayList<>();

        public void attachList(List<ModelCellEducation> list){
            this.list.addAll(0,list);
            notifyDataSetChanged();
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
//                Picasso.with(itemView.getContext()).load(modelCellEducation.getImageUrl()).into(imageView);
//                title.setText(modelCellEducation.getTextTitle());
//                content.setText(modelCellEducation.getTextContent());
//                if( modelCellEducation.getRead() == true ) {
//                    //set grey out
//                }
            }

            @Override
            public void onClick(View view) {

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
