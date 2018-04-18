package com.example.android.alcchallenge.Presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.android.alcchallenge.Contracts.EditMedContract;
import com.example.android.alcchallenge.Source.Medication;
import com.example.android.alcchallenge.Source.MedicationsDataSource;
import com.example.android.alcchallenge.Utils.MedicationManagerSyncUtils;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

/**
 * Created by HARNY on 04/07/2018.
 */

public class EditMedPresenter implements EditMedContract.IPresenter,
        MedicationsDataSource.GetMedicationCallback{


    @NonNull
    private final MedicationsDataSource mMedicationsDataSource;

    @NonNull
    private final EditMedContract.IView mAddMedicationView;

    private String mMedicationId;

    private boolean mIsDataMissing;



    public EditMedPresenter(@NonNull MedicationsDataSource medicationsDataSource,
                            @NonNull EditMedContract.IView addMedicationView,
                            String medicationId, boolean shouldLoadDataFromRepo) {
        mMedicationsDataSource = checkNotNull(medicationsDataSource);
        mAddMedicationView = checkNotNull(addMedicationView);
        mMedicationId = medicationId;
        mIsDataMissing = shouldLoadDataFromRepo;

        mAddMedicationView.setPresenter(this);
    }


    @Override
    public void start() {
        if (!isNewMedication() && mIsDataMissing){
            populateMedication();
        }
    }

    @Override
    public void saveMedication(String name, String description, int interval, String start, String end, String month, Context context) {
        if (isNewMedication()){
            createMedication(name, description, interval, start, end, month);
        }else {
            updateMedication(name, description, interval, start, end, month);
        }

    }


    @Override
    public void populateMedication() {
        if(isNewMedication()){
            throw new RuntimeException("populateMedication() was called but medication is new");
        }
        mMedicationsDataSource.getMedication(mMedicationId, this);
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }

    @Override
    public void scheduleMedicationScheduler(Context context, String startDate, String medicationName, int interval) {
        MedicationManagerSyncUtils.scheduleMedicationScheduler(context, startDate, medicationName, interval);

    }


    @Override
    public void onMedicationLoaded(Medication medication) {
        if (mAddMedicationView.isActive()){
            mAddMedicationView.setName(medication.getMMedicationName());
            mAddMedicationView.setDescription(medication.getMDescription());
            mAddMedicationView.setStartDate(medication.getMStartDate());
            mAddMedicationView.setEndDate(medication.getMEndDate());
        }
    }

    @Override
    public void onDataNotAvailable() {
        if(mAddMedicationView.isActive()){
            mAddMedicationView.setEmptyFieldError();
        }
    }

    private boolean isNewMedication(){
        return mMedicationId == null;
    }

    private void createMedication(String name, String description, int interval, String start, String end, String month){
        Medication newMedication = new Medication(name, description, interval, start, month, end);
        if(newMedication.isEmpty()){
            mAddMedicationView.setEmptyFieldError();
        }else {
            mMedicationsDataSource.saveMedication(newMedication);
            mAddMedicationView.showMedicationsList();
        }

    }

    private void updateMedication(String name, String description, int interval, String start, String end, String month) {
        Medication medicationUpdate = new Medication(name, description, interval, start, month, end, mMedicationId);

        if(isNewMedication()){
            throw new RuntimeException("updateMedication() was called but medication is new");
        }
        mMedicationsDataSource.saveMedication(medicationUpdate);
        mAddMedicationView.showMedicationsList();
    }

}
