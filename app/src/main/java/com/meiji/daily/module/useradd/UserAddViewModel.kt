package com.meiji.daily.module.useradd

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.meiji.daily.Constant
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.io
import com.meiji.daily.mainThread
import com.meiji.daily.util.ErrorAction
import com.meiji.daily.util.RxBusHelper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import retrofit2.Retrofit

/**
 * Created by Meiji on 2017/12/4.
 */

class UserAddViewModel
constructor(application: Application,
            private val mAppDatabase: AppDatabase,
            private val mRetrofit: Retrofit,
            private val mRxBusHelper: RxBusHelper)
    : AndroidViewModel(application) {

    private val mDisposable: CompositeDisposable

    private lateinit var mRxBus: Flowable<Any>
    var isLoading: MutableLiveData<Boolean>
        private set
    var isRefreshUI: MutableLiveData<Boolean>
        private set
    var isAddResult: MutableLiveData<Boolean>
        private set
    var mList: MutableLiveData<MutableList<ZhuanlanBean>>
        private set

    init {
        isLoading = MutableLiveData()
        isRefreshUI = MutableLiveData()
        isAddResult = MutableLiveData()
        mList = MutableLiveData()
        mDisposable = CompositeDisposable()

        isLoading.value = true
        isRefreshUI.value = true
    }

    init {
        handleData()
        subscribeTheme()
    }

    fun handleData() {
        isLoading.value = true

        mAppDatabase.zhuanlanDao().query(Constant.TYPE_USERADD)
                .subscribeOn(io)
                .observeOn(mainThread)
                .subscribe(Consumer<MutableList<ZhuanlanBean>> { list ->
                    mList.value = list
                }, ErrorAction.error()).let { mDisposable.add(it) }
        isLoading.value = false
    }

    internal fun addItem(input: String) {
        isLoading.value = true

        mRetrofit.create(IApi::class.java).getZhuanlanBean(input)
                .doOnSuccess { bean ->
                    bean?.let {
                        it.type = Constant.TYPE_USERADD
                        mAppDatabase.zhuanlanDao().insert(it)
                    }
                }
                .subscribeOn(io)
                .observeOn(mainThread)
                .subscribe(Consumer {
                    isAddResult.value = true
                    handleData()
                }, object : ErrorAction() {
                    override fun doAction() {
                        isAddResult.value = false
                        isLoading.value = false
                    }
                }.action()).let { mDisposable.add(it) }
    }

    private fun subscribeTheme() {
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI)
        mRxBus.subscribe(Consumer {
            isRefreshUI.setValue(!(isRefreshUI.value)!!)
        }, ErrorAction.error()).let { mDisposable.add(it) }
    }

    internal fun deleteItem(bean: ZhuanlanBean) {
        Single.create(SingleOnSubscribe<Any> {
            mAppDatabase.zhuanlanDao().delete(bean.slug)
        }).subscribeOn(io)
                .subscribe().let { mDisposable.add(it) }

    }

    override fun onCleared() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus)
        mDisposable.clear()
        super.onCleared()
    }

    class Factory internal constructor(private val mApplication: Application,
                                       private val mAppDatabase: AppDatabase,
                                       private val mRetrofit: Retrofit,
                                       private val mRxBusHelper: RxBusHelper)
        : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserAddViewModel(mApplication, mAppDatabase, mRetrofit, mRxBusHelper) as T
        }
    }
}

