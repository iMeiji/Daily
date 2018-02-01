package com.meiji.daily.module.zhuanlan

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.meiji.daily.Constant
import com.meiji.daily.R
import com.meiji.daily.bean.ZhuanlanBean
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.util.ErrorAction
import com.meiji.daily.util.RxBusHelper
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*

/**
 * Created by Meiji on 2017/11/29.
 */

class ZhuanlanViewModel
constructor(application: Application,
            private val mType: Int,
            private val mAppDatabase: AppDatabase,
            private val mRetrofit: Retrofit,
            private val mRxBusHelper: RxBusHelper) : AndroidViewModel(application) {

    private val mDisposable: CompositeDisposable
    private lateinit var mIdArr: Array<String>
    private lateinit var mRxBus: Flowable<Any>
    var isLoading: MutableLiveData<Boolean>
        private set
    var isRefreshUI: MutableLiveData<Boolean>
        private set
    var mList: MutableLiveData<List<ZhuanlanBean>>
        private set

    init {
        isLoading = MutableLiveData()
        isRefreshUI = MutableLiveData()
        mList = MutableLiveData()
        mDisposable = CompositeDisposable()

        isLoading.value = true
        isRefreshUI.value = true
    }

    init {
        handleData()
        subscribeTheme()
    }

    private fun subscribeTheme() {
        mRxBus = mRxBusHelper.register(Constant.RxBusEvent.REFRESHUI)
        mRxBus.subscribe(Consumer {
            isRefreshUI.setValue(!(isRefreshUI.value)!!)
        }, ErrorAction.error()).let { mDisposable.add(it) }
    }

    internal fun handleData() {
        mAppDatabase.ZhuanlanNewDao().query(mType)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    if (it.size > 0) {
                        return@flatMap Maybe.just(it)
                    } else {
                        val l = retrofitRequest()
                        return@flatMap Maybe.just(l)
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer<List<ZhuanlanBean>> { list ->
                    mList.value = list
                    isLoading.setValue(false)
                }, object : ErrorAction() {
                    override fun doAction() {
                        mList.value = null
                        isLoading.value = false
                    }
                }.action()).let { mDisposable.add(it) }
    }

    override fun onCleared() {
        mRxBusHelper.unregister(Constant.RxBusEvent.REFRESHUI, mRxBus)
        mDisposable.clear()
        super.onCleared()
    }

    private fun retrofitRequest(): List<ZhuanlanBean> {

        val resources = getApplication<Application>().resources

        when (mType) {
            Constant.TYPE_PRODUCT -> mIdArr = resources.getStringArray(R.array.product)
            Constant.TYPE_MUSIC -> mIdArr = resources.getStringArray(R.array.music)
            Constant.TYPE_LIFE -> mIdArr = resources.getStringArray(R.array.life)
            Constant.TYPE_EMOTION -> mIdArr = resources.getStringArray(R.array.emotion)
            Constant.TYPE_FINANCE -> mIdArr = resources.getStringArray(R.array.profession)
            Constant.TYPE_ZHIHU -> mIdArr = resources.getStringArray(R.array.zhihu)
        }

        val list = ArrayList<ZhuanlanBean>()
        val api = mRetrofit.create(IApi::class.java)

        val maybeList = mIdArr.map { api.getZhuanlanBean(it) }

        Maybe.merge(maybeList)
                .doOnComplete { mAppDatabase.ZhuanlanNewDao().insert(list) }
                .subscribe(Consumer { bean ->
                    if (bean != null) {
                        bean.type = mType
                        list.add(bean)
                    }
                }, ErrorAction.error()).let { mDisposable.add(it) }

        return list
    }

    class Factory(private val mApplication: Application,
                  private val mType: Int,
                  private val mAppDatabase: AppDatabase,
                  private val mRetrofit: Retrofit,
                  private val mRxBusHelper: RxBusHelper) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ZhuanlanViewModel(mApplication, mType, mAppDatabase, mRetrofit, mRxBusHelper) as T
        }
    }

    companion object {

        internal val TAG = "ZhuanlanViewModel"
    }
}
