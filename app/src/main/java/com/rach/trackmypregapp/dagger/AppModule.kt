package com.rach.trackmypregapp.dagger

import android.app.NotificationManager
import android.content.Context
import androidx.work.WorkManager
import com.rach.trackmypregapp.notification.MyNotification
import com.rach.trackmypregapp.room.AppDatabase
import com.rach.trackmypregapp.room.PatientDao
import com.rach.trackmypregapp.room.repo.PatientRepository
import com.rach.trackmypregapp.room.useCases.GetAllDataUsesCases
import com.rach.trackmypregapp.room.useCases.InsertPatientUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context):AppDatabase{
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun providePatientDao(database: AppDatabase): PatientDao {
        return database.patientDao()
    }

    @Provides
    @Singleton
    fun providePatientRepository(patientDao: PatientDao): PatientRepository {
        return PatientRepository(patientDao)
    }


    @Provides
    @Singleton
    fun provideNotificationManager(@ApplicationContext context: Context):NotificationManager{
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    @Singleton
    fun providesNotification(@ApplicationContext context: Context ,notificationManager: NotificationManager):MyNotification{
        return MyNotification(
            context, notificationManager
        )
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context):WorkManager{
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideInsertUseCases(patientRepository: PatientRepository):InsertPatientUseCases{
        return InsertPatientUseCases(patientRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllDataUseCases(patientRepository: PatientRepository):GetAllDataUsesCases{
        return GetAllDataUsesCases(patientRepository)
    }

}