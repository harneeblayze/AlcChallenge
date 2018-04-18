package com.example.android.alcchallenge.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.example.android.alcchallenge.R;

public class EditMedActivity extends AppCompatActivity {
    private ActionBar mActionBar;

    public static final int REQUEST_ADD_MEDICATION = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "should_load_data_from_repo_key";

    private AddEditMedicationPresenter mAddEditMedicationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med);
    }
}
