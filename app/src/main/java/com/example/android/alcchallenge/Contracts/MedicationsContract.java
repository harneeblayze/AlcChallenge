package com.example.android.alcchallenge.Contracts;

import android.support.annotation.NonNull;

import com.example.android.alcchallenge.Interfaces.BasePresenter;
import com.example.android.alcchallenge.Interfaces.BaseView;
import com.example.android.alcchallenge.Source.Medication;
import com.example.android.alcchallenge.Source.MedicationsFilterType;

import java.util.List;

/**
 * Created by HARNY on 4/18/2018.
 */

public interface MedicationsContract {
    
    interface IPresenter extends BasePresenter {
        void result(int requestCode, int resultCode);

        void loadMedications();

        void addNewMedication();

        void openMedicationDetails(@NonNull Medication requestedMedication);

        void setFiltering(MedicationsFilterType requestType);

        MedicationsFilterType getFiltering();
    }

    interface IView extends BaseView<IPresenter> {

        void setLoadingIndicator(boolean active);

        void showMedications(List<Medication> medications);

        void showAddMedication();

        void showMedicationDetailsUi(String medicationId);

        void showLoadingMedicationsError();

        void showNoMedications();

        void setJanFilterLabel();

        void setFebFilterLabel();

        void setMarFilterLabel();

        void setAprFilterLabel();

        void setMayFilterLabel();

        void setJunFilterLabel();

        void setJulFilterLabel();

        void setAugFilterLabel();

        void setSepFilterLabel();

        void setOctFilterLabel();

        void setNovFilterLabel();

        void setDecFilterLabel();

        void setAllFilterLabel();

        void showSuccessfullySavedMessage();

        boolean isActive();

        void showFilteringPopUpMenu();
    }
}
