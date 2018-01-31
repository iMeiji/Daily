package com.meiji.daily.module.useradd

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.meiji.daily.Constant
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.util.ErrorAction
import com.meiji.daily.util.RxBusHelper
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Meiji on 2017/12/4.
 */

class UserAddViewModel
constructor(application: Application,
            private val mAppDatabase: AppDatabase,
            private val mRetrofit: Retrofit,
            private val mRxBusHelper: RxBusHelper)
    : AndroidViewModel(application), SharedPreferences.OnSharedPreferenceChangeListener {

    private val mDisposable: CompositeDisposable

    private var mRxBus: Flowable<Any>? = null
    var isLoading: MutableLiveData<Boolean>? = null
        private set
    var isRefreshUI: MutableLiveData<Boolean>? = null
        private set
    var isAddResult: MutableLiveData<Boolean>? = null
        private set
    var mList: MutableLiveData<MutableList<ZhuanlanBean>>? = null
        private set

    init {
        isLoading = MutableLiveData()
        isRefreshUI = MutableLiveData()
        isAddResult = MutableLiveData()
        mList = MutableLiveData()
        mDisposable = CompositeDisposable()

        isLoading!!.value = true
        isRefreshUI!!.value = true
    }

    init {

        handleData()
        subscribeTheme()
    }

    fun handleData() {
        isLoading!!.value = true

        val subscribe = mAppDatabase.ZhuanlanNewDao().query(Constant.TYPE_USERADD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<MutableList<ZhuanlanBean>> { list -> mList!!.value = list }, ErrorAction.error())
        mDisposable.add(subscribe)
        isLoading!!.value = false
    }

    internal fun addItem(input: String) {
        isLoading!!.value = true

        val subscribe = mRetrofit.create(IApi::class.java).getZhuanlanBean(input)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { bean ->
                    if (bean != null) {
                        bean.type = Constant.TYPE_USERADD
                        mAppDatabase.ZhuanlanNewDao().insert(bean)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    isAddResult!!.value = true
                    handleData()
                }, object : ErrorAction() {
                    override fun doAction() {
                        isAddResult!!.value = false
                        isLoading!!.value = false
                    }
                }.action())
        mDisposable.add(subscribe)
    }

    private fun subscribeTheme() {
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI)
        val subscribe = mRxBus!!.subscribe(Consumer<Any> {
            isRefreshUI!!.setValue(isRefreshUI!!.value != null && (!isRefreshUI!!.value!!))
        }, ErrorAction.error())
        mDisposable.add(subscribe)
    }

    internal fun deleteItem(bean: ZhuanlanBean) {
        val subscribe = Single.create(SingleOnSubscribe<Any> { mAppDatabase.ZhuanlanNewDao().delete(bean.slug) }).subscribeOn(Schedulers.io()).subscribe()
        mDisposable.add(subscribe)
    }

    override fun onCleared() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus!!)
        mDisposable.clear()
        super.onCleared()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    class Factory internal constructor(private val mApplication: Application, private val mAppDatabase: AppDatabase,
                                       private val mRetrofit: Retrofit, private val mRxBusHelper: RxBusHelper) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserAddViewModel(mApplication, mAppDatabase, mRetrofit, mRxBusHelper) as T
        }
    }
}

