package com.example.android.alcchallenge.Source;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by HARNY on 04/08/2018.
 */

public interface MedicationsDataSource {

    interface LoadMonthlyMedicationsCallBack {
        void onMonthlyMedicationsLoaded(List<Medication> medications);

        void onDataNotAvailable();

    }

    interface LoadAllMedicationsCallBack{
        void onMedicationsLoaded(List<Medication> medications);

        void onDataNotAvailable();
    }

    interface GetMedicationCallback {
        void onMedicationLoaded(Medication medication);

        void onDataNotAvailable();

    }

    void getAllMedications(@NonNull LoadAllMedicationsCallBack callBack);

    void getMonthlyMedications(@NonNull String month, @NonNull LoadMonthlyMedicationsCallBack callBack);

    void getMedication(@NonNull String medicationId, @NonNull GetMedicationCallback callback);

    void saveMedication(@NonNull Medication medication);

    void deleteMedication(@NonNull String medicationId);


}
