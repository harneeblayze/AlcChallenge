package com.example.android.alcchallenge.Source;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by HARNY on 04/09/2018.
 */

public class MedManagerIntentService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *  name Used to name the worker thread, important only for debugging.
     */
    public MedManagerIntentService() {
        super("MedManagerIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        MedManagerTasks.executeTask(this, action, null);
    }
}
