package com.meiji.daily.module.postscontent

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.util.ErrorAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

/**
 * Created by Meiji on 2017/12/5.
 */

class PostsContentViewModel
internal constructor(application: Application,
                     slug: String,
                     private val mRetrofit: Retrofit) : AndroidViewModel(application) {
    private val mDisposable: CompositeDisposable

    var isLoading: MutableLiveData<Boolean>
        private set
    var html: MutableLiveData<String>
        private set

    init {
        isLoading = MutableLiveData()
        html = MutableLiveData()
        mDisposable = CompositeDisposable()
        handleData(slug)
    }

    private fun handleData(slug: String) {
        isLoading.value = true

        mRetrofit.create(IApi::class.java).getPostsContentBean(slug)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { bean ->
                    isLoading.value = false
                    html.value = parserHTML(bean.content)
                }, object : ErrorAction() {
                    override fun doAction() {
                        isLoading.value = false
                        html.value = null
                    }
                }.action()).let { mDisposable.add(it) }
    }

    private fun parserHTML(content: String): String {
        val css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">"

        return ("<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "</body>\n</html>")
    }

    override fun onCleared() {
        mDisposable.clear()
        super.onCleared()
    }

    class Factory internal constructor(private val mApplication: Application,
                                       private val mSlug: String,
                                       private val mRetrofit: Retrofit) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PostsContentViewModel(mApplication, mSlug, mRetrofit) as T
        }
    }
}
