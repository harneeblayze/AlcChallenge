package com.example.android.alcchallenge.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.alcchallenge.Source.Medication;

/**
 * Created by HARNY on 4/08/2018.
 */

@Database(entities = {Medication.class}, version = 1, exportSchema = false)
public abstract class MedicationsDatabase extends RoomDatabase {

    private static MedicationsDatabase INSTANCE;

    public abstract MedicationsDao medicationsDao();


    public static MedicationsDatabase getInstance(Context context){
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        MedicationsDatabase.class, "medications.db")
                        .build();
            }
            return INSTANCE;
    }

//    public static void destroyInstance(){
//        INSTANCE = null;
//    }
}
