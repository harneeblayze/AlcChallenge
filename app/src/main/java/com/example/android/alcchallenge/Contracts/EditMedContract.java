package com.example.android.alcchallenge.Contracts;

import android.content.Context;
import android.widget.TextView;

import com.example.android.alcchallenge.Interfaces.BasePresenter;
import com.example.android.alcchallenge.Interfaces.BaseView;


/**
 * Created by HARNY on 04/04/2018.
 */

public interface EditMedContract {

    interface IPresenter extends BasePresenter{

        void saveMedication(String name, String description, int interval, String start, String end, String month, Context context);

        void populateMedication();

        boolean isDataMissing();

        void scheduleMedicationScheduler(Context context, String startDate, String medicationName, int interval);

    }

    interface IView extends BaseView<IPresenter> {

        void showStartDatePickerDialog();

        void showEndDatePickerDialog();

        void showStartTimePickerDialog();

        void showEndTimePickerDialog();

        void updateDateTime(int year, int month, int day, int hour, int minute, TextView v);

        void setEmptyFieldError();

        void setName(String name);

        void setDescription(String description);

        void setStartDate(String startDate);

        void setEndDate(String endDate);

        boolean isActive();

        void showMedicationsList();



    }
}
