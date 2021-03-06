package com.example.android.alcchallenge.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.alcchallenge.Activities.EditMedActivity;
import com.example.android.alcchallenge.Activities.Main2Activity;
import com.example.android.alcchallenge.Activities.MedicationDetailActivity;
import com.example.android.alcchallenge.Contracts.MedicationsContract;
import com.example.android.alcchallenge.Interfaces.MedicationItemListener;
import com.example.android.alcchallenge.R;
import com.example.android.alcchallenge.Source.Medication;
import com.example.android.alcchallenge.Source.MedicationsFilterType;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MedFragment# factory method to
 * create an instance of this fragment.
 */
public class MedFragment extends Fragment implements MedicationsContract.IView, MedicationItemListener {

    private MedicationsContract.IPresenter mPresenter;

    private MedicationAdapter mMedicationAdapter;

    private View mNoMedicationsView;

    private ImageView mNoMedicationIcon;

    private TextView mNoMedicationAddView;

    private TextView mNoMedicationMainView;

    private RecyclerView mRecyclerView;
    Spinner spinner;


    private OnFragmentInteractionListener mListener;

    public MedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    /*public static MedFragment newInstance(String param1, String param2) {
        MedFragment fragment = new MedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMedicationAdapter = new MedicationAdapter(new ArrayList<Medication>(0), this);
        //mPresenter.setFiltering(MedicationsFilterType.ALL_MEDICATIONS);
        mPresenter.loadMedications();


    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_med, container, false);
        mRecyclerView =  rootView.findViewById(R.id.medications_recycler_view);


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMedicationAdapter);
        // Set up  no Medications view
        mNoMedicationsView = rootView.findViewById(R.id.noMedications);
        mNoMedicationIcon = (ImageView) rootView.findViewById(R.id.noMedicatonsIcon);
        mNoMedicationMainView = (TextView) rootView.findViewById(R.id.noMedicationsMain);
        mNoMedicationAddView = (TextView) rootView.findViewById(R.id.noMedicationsAdd);
        mNoMedicationAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddMedication();
            }
        });

        spinner = (Spinner)rootView.findViewById(R.id.spin);
        ArrayAdapter<CharSequence> spinadapt =  ArrayAdapter.createFromResource(getActivity(),R.array.months,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinadapt);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();

                switch (selected){
                    case "January":
                        mPresenter.setFiltering(MedicationsFilterType.JANUARY_MEDICATIONS);
                        break;
                    case "February":
                        mPresenter.setFiltering(MedicationsFilterType.FEBRUARY_MEDICATIONS);
                        break;
                    case "March":
                        mPresenter.setFiltering(MedicationsFilterType.MARCH_MEDICATIONS);
                        break;
                    case "April":
                        mPresenter.setFiltering(MedicationsFilterType.APRIL_MEDICATIONS);
                        break;
                    case "May":
                        mPresenter.setFiltering(MedicationsFilterType.MAY_MEDICATIONS);
                        break;
                    case "June":
                        mPresenter.setFiltering(MedicationsFilterType.JUNE_MEDICATIONS);
                        break;
                    case "July":
                        mPresenter.setFiltering(MedicationsFilterType.JULY_MEDICATIONS);
                        break;
                    case "August":
                        mPresenter.setFiltering(MedicationsFilterType.AUGUST_MEDICATIONS);
                        break;
                    case "September":
                        mPresenter.setFiltering(MedicationsFilterType.SEPTEMBER_MEDICATIONS);
                        break;
                    case "October":
                        mPresenter.setFiltering(MedicationsFilterType.OCTOBER_MEDICATIONS);
                        break;
                    case "November":
                        mPresenter.setFiltering(MedicationsFilterType.NOVEMBER_MEDICATIONS);
                        break;
                    case "December":
                        mPresenter.setFiltering(MedicationsFilterType.DECEMBER_MEDICATIONS);
                        break;
                    default:
                        mPresenter.setFiltering(MedicationsFilterType.ALL_MEDICATIONS);
                        break;
                }
                mPresenter.loadMedications();
                Toast.makeText(getActivity(), String.valueOf(parent.getItemAtPosition(position)),Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_medication);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewMedication();
            }
        });

      /*  final ScrollChildSwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        swipeRefreshLayout.setScrollUpChild(mRecyclerView);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadMedications();
            }
        });

        setHasOptionsMenu(true);*/

        return rootView;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                mPresenter.loadMedications();
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.medications_fragment_menu ,menu);

    }


    @Override
    public void setPresenter(MedicationsContract.IPresenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null){
            return;
        }
        final SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.refresh_layout);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });

    }

    @Override
    public void showMedications(List<Medication> medications) {
        mMedicationAdapter.replaceData(medications);
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoMedicationsView.setVisibility(View.GONE);
    }

    @Override
    public void showAddMedication() {
        startActivityForResult(new Intent(getContext(),EditMedActivity.class ),
                EditMedActivity.REQUEST_ADD_MEDICATION);


    }

    @Override
    public void showMedicationDetailsUi(String medicationId) {
        Intent intent = new Intent(getContext(), MedicationDetailActivity.class);
        intent.putExtra(MedicationDetailActivity.MEDICATION_EXTRA_ID, medicationId);
        startActivity(intent);
    }

    @Override
    public void showLoadingMedicationsError() {
        showMessage(getString(R.string.no_medications));
    }

    @Override
    public void showNoMedications() {
        showNoMedicationsViews(
                getString(R.string.no_medications),
                R.drawable.ic_folder
        );
    }

    private void showNoMedicationsViews(String mainText, int icon){
        mRecyclerView.setVisibility(View.GONE);
        mNoMedicationsView.setVisibility(View.VISIBLE);
        mNoMedicationMainView.setText(mainText);
        mNoMedicationIcon.setImageDrawable(getResources().getDrawable(icon));
        mNoMedicationAddView.setVisibility(View.VISIBLE);

    }



    @Override
    public void setJanFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "January"));
    }

    @Override
    public void setFebFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "February"));

    }

    @Override
    public void setMarFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "March"));

    }

    @Override
    public void setAprFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "April"));

    }

    @Override
    public void setMayFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "May"));

    }

    @Override
    public void setJunFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "June"));

    }

    @Override
    public void setJulFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "July"));

    }

    @Override
    public void setAugFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "August"));

    }

    @Override
    public void setSepFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "September"));

    }

    @Override
    public void setOctFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "October"));

    }

    @Override
    public void setNovFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "November"));

    }

    @Override
    public void setDecFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "December"));

    }

    @Override
    public void setAllFilterLabel() {
        ((Main2Activity)getActivity()).setActionBarTitle(getString(R.string.title_format, "All"));

    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.saved_message));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showFilteringPopUpMenu() {

        android.support.v7.widget.PopupMenu popup = new android.support.v7.widget.PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.medications_filter_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.jan:
                        mPresenter.setFiltering(MedicationsFilterType.JANUARY_MEDICATIONS);
                        break;
                    case R.id.feb:
                        mPresenter.setFiltering(MedicationsFilterType.FEBRUARY_MEDICATIONS);
                        break;
                    case R.id.mar:
                        mPresenter.setFiltering(MedicationsFilterType.MARCH_MEDICATIONS);
                        break;
                    case R.id.apr:
                        mPresenter.setFiltering(MedicationsFilterType.APRIL_MEDICATIONS);
                        break;
                    case R.id.may:
                        mPresenter.setFiltering(MedicationsFilterType.MAY_MEDICATIONS);
                        break;
                    case R.id.jun:
                        mPresenter.setFiltering(MedicationsFilterType.JUNE_MEDICATIONS);
                        break;
                    case R.id.jul:
                        mPresenter.setFiltering(MedicationsFilterType.JULY_MEDICATIONS);
                        break;
                    case R.id.aug:
                        mPresenter.setFiltering(MedicationsFilterType.AUGUST_MEDICATIONS);
                        break;
                    case R.id.sep:
                        mPresenter.setFiltering(MedicationsFilterType.SEPTEMBER_MEDICATIONS);
                        break;
                    case R.id.oct:
                        mPresenter.setFiltering(MedicationsFilterType.OCTOBER_MEDICATIONS);
                        break;
                    case R.id.nov:
                        mPresenter.setFiltering(MedicationsFilterType.NOVEMBER_MEDICATIONS);
                        break;
                    case R.id.dec:
                        mPresenter.setFiltering(MedicationsFilterType.DECEMBER_MEDICATIONS);
                        break;
                    default:
                        mPresenter.setFiltering(MedicationsFilterType.ALL_MEDICATIONS);
                        break;
                }
                mPresenter.loadMedications();
                return true;
            }
        });

        popup.show();
    }




    @Override
    public void onMedicationClick(Medication clickedMedication) {
        mPresenter.openMedicationDetails(clickedMedication);
    }


    // TODO: Rename method, update argument and hook method into UI event
/*    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationAdapterViewHolder> {

        private List<Medication> mMedications;

        private MedicationItemListener mItemListener;

        public MedicationAdapter(List<Medication> medications, MedicationItemListener itemListener) {
            setList(medications);
            mItemListener = itemListener;
        }
        private void setList(List<Medication> medications){
            mMedications = checkNotNull(medications);
        }

        private Medication getItem(int position){
            return mMedications.get(position);
        }

        public void replaceData(List<Medication> medications){
            setList(medications);
            notifyDataSetChanged();
        }

        @Override
        public MedicationAdapter.MedicationAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_list_item, parent, false);
            view.setFocusable(true);


            return new MedicationAdapter.MedicationAdapterViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MedicationAdapter.MedicationAdapterViewHolder holder, int position) {
            Medication medication = getItem(position);
            holder.medicationNameView.setText(medication.getMMedicationName());
            holder.medicationDateView.setText(medication.getMStartDate());

        }

        @Override
        public int getItemCount() {
            return mMedications.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class MedicationAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView medicationNameView,
                    medicationDateView;
            CardView medicationsCardView;

            public MedicationAdapterViewHolder(View itemView) {
                super(itemView);

                medicationNameView = (TextView) itemView.findViewById(R.id.medication_name);
                medicationDateView = itemView.findViewById(R.id.medication_date);
                medicationsCardView = itemView.findViewById(R.id.medications_card_view);
                medicationsCardView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Medication clickedMedication = getItem(getAdapterPosition());
                mItemListener.onMedicationClick(clickedMedication);
                Toast.makeText(getContext(), "This item was clicked" + clickedMedication.getMMedicationName(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
