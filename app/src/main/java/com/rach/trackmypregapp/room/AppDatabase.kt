package com.rach.trackmypregapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PatientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao

    companion object {

        @Volatile
        private var INSTANCES: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCES ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "patient_Database"
                ).build()
                INSTANCES = instance
                instance
            }
        }

    }

}