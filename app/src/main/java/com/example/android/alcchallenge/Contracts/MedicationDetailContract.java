package com.example.android.alcchallenge.Contracts;

import com.example.android.alcchallenge.Interfaces.BasePresenter;
import com.example.android.alcchallenge.Interfaces.BaseView;
import com.example.android.alcchallenge.Source.Medication;

/**
 * Created by HARNY on 4/18/2018.
 */

public interface MedicationDetailContract {

    interface IPresenter extends BasePresenter {

        void editMedication();

        void deleteMedication();

//        void completeMedication();

//        void activateMedication();
    }

    interface IView extends BaseView<IPresenter> {

        void setMedicationDetails(Medication medicationDetails);

        void showEditMedication(String medicationId);

        void showMedicationDeleted();

//        void showMedicationCompleted();

//        void showMedicationMarkedActive();

        boolean isActive();
    }


}
