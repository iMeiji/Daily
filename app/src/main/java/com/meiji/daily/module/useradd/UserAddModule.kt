package com.meiji.daily.module.useradd

import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.di.scope.FragmentScoped
import com.meiji.daily.util.RxBusHelper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by Meiji on 2017/12/28.
 */
@Module
class UserAddModule(private val mUserAddView: UserAddView) {

    @FragmentScoped
    @Provides
    internal fun provideModel(@Named("application") application: Application,
                              appDatabase: AppDatabase,
                              retrofit: Retrofit,
                              rxBusHelper: RxBusHelper): UserAddViewModel {
        val factory = UserAddViewModel.Factory(application, appDatabase, retrofit, rxBusHelper)
        return ViewModelProviders.of(mUserAddView, factory).get(UserAddViewModel::class.java)
    }
}
