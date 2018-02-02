package com.meiji.daily.module.postslist

import android.app.Application
import android.arch.lifecycle.*
import com.meiji.daily.bean.PostsListBean
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.util.ErrorAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.*

/**
 * Created by Meiji on 2017/12/5.
 */

class PostsListViewModel
constructor(application: Application,
            private val mSlug: String,
            private val mPostCount: Int,
            private val mRetrofit: Retrofit) : AndroidViewModel(application) {
    private val mDisposable: CompositeDisposable
    var mList: MutableList<PostsListBean>
    var isLoading: MutableLiveData<Boolean>
        private set
    var isEnd: MutableLiveData<Boolean>
        private set
    var mOffset: MutableLiveData<Int>
    var listLiveData: LiveData<List<PostsListBean>>
        private set

    init {
        mList = ArrayList()
        mDisposable = CompositeDisposable()
        isLoading = MutableLiveData()
        isEnd = MutableLiveData()
        listLiveData = MutableLiveData()
        mOffset = MutableLiveData()

        isLoading.value = true
        mOffset.value = 0

        // 当 mOffset 的值发生改变，就会执行 apply
        listLiveData = Transformations.switchMap(mOffset) { input -> handleData(input!!) }
    }

    private fun handleData(offset: Int): LiveData<List<PostsListBean>> {

        val liveData = MutableLiveData<List<PostsListBean>>()

        val subscribe = mRetrofit.create(IApi::class.java).getPostsList(mSlug, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { list ->
                    mList.addAll(list)
                    liveData.value = mList
                }, object : ErrorAction() {
                    override fun doAction() {
                        liveData.value = null
                    }
                }.action())
        mDisposable.add(subscribe)
        isLoading.value = false
        return liveData
    }

    internal fun doRefresh() {
        mList.clear()
        mOffset.value = 0
    }

    internal fun loadMore() {
        if (mOffset.value != null && mOffset.value!! >= mPostCount - 1) {
            isEnd.value = true
            return
        }

        mOffset.value = mList.size
    }

    override fun onCleared() {
        mDisposable.clear()
        super.onCleared()
    }

    class Factory internal constructor(private val mApplication: Application, private val mSlug: String, private val mPostCount: Int, private val mRetrofit: Retrofit) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PostsListViewModel(mApplication, mSlug, mPostCount, mRetrofit) as T
        }
    }
}
