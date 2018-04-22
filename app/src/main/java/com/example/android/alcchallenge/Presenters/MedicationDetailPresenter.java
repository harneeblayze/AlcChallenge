package com.example.android.alcchallenge.Presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.alcchallenge.Contracts.MedicationDetailContract;
import com.example.android.alcchallenge.Source.Medication;
import com.example.android.alcchallenge.Source.MedicationsDataSource;

import static com.google.android.gms.common.internal.Preconditions.checkNotNull;

/**
 * Created by HARNY on 04/12/2018.
 */

public class MedicationDetailPresenter implements MedicationDetailContract.IPresenter{

    private final MedicationsDataSource mMedicationsDataSource;

    private final MedicationDetailContract.IView mMedicationDetailView;

    @Nullable
    private String mMedicationId;

    public MedicationDetailPresenter(@Nullable String medicationId,
                                     @NonNull MedicationsDataSource medicationsDataSource,
                                     @NonNull MedicationDetailContract.IView medicationDetailView){

        mMedicationId = medicationId;
        mMedicationsDataSource = checkNotNull(medicationsDataSource);
        mMedicationDetailView = checkNotNull(medicationDetailView);

        mMedicationDetailView.setPresenter(this);
    }

    @Override
    public void start() {
        openMedication();
    }

    private void openMedication(){
        if(mMedicationId == null || mMedicationId.equals("")){
            return;
        }

        mMedicationsDataSource.getMedication(mMedicationId, new MedicationsDataSource.GetMedicationCallback() {
            @Override
            public void onMedicationLoaded(Medication medication) {
                if (!mMedicationDetailView.isActive()) {
                    return;
                }
                if (null == medication){
                }else {
                    mMedicationDetailView.setMedicationDetails(medication);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (!mMedicationDetailView.isActive()){
                    return;
                }
            }
        });
    }

    @Override
    public void editMedication() {
        if(mMedicationId == null || mMedicationId.equals("")){
            return;
        }
        mMedicationDetailView.showEditMedication(mMedicationId);

    }

    @Override
    public void deleteMedication() {
        if(mMedicationId == null || mMedicationId.equals("")){
            return;
        }
        mMedicationsDataSource.deleteMedication(mMedicationId);
        mMedicationDetailView.showMedicationDeleted();
    }

}
