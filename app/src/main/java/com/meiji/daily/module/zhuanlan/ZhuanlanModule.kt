package com.meiji.daily.module.zhuanlan

import android.app.Application
import android.arch.lifecycle.ViewModelProviders
import com.meiji.daily.Constant
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.di.scope.FragmentScoped
import com.meiji.daily.util.RxBusHelper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by Meiji on 2017/12/21.
 */
@Module
class ZhuanlanModule(private val mZhuanlanView: ZhuanlanView) {

    @FragmentScoped
    @Provides
    internal fun provideModel(@Named("application") application: Application,
                              type: Int, appDatabase: AppDatabase,
                              retrofit: Retrofit, rxBusHelper: RxBusHelper): ZhuanlanViewModel {
        val factory = ZhuanlanViewModel.Factory(application, type, appDatabase, retrofit, rxBusHelper)
        return ViewModelProviders.of(mZhuanlanView, factory).get(ZhuanlanViewModel::class.java)
    }

    @FragmentScoped
    @Provides
    internal fun provideType(): Int {
        val bundle = mZhuanlanView.arguments
        var type = Constant.TYPE_PRODUCT
        if (bundle != null) {
            type = bundle.getInt(ZhuanlanView.ARGUMENT_TYPE, Constant.TYPE_PRODUCT)
        }
        return type
    }
}
