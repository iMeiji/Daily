package com.meiji.daily.module.shareadd

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.meiji.daily.App
import com.meiji.daily.Constant
import com.meiji.daily.R
import com.meiji.daily.data.local.AppDatabase
import com.meiji.daily.data.remote.IApi
import com.meiji.daily.util.SettingHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.util.regex.Pattern
import javax.inject.Inject

/**
 * Created by Meiji on 2016/12/1.
 */

class ShareAddActivity : AppCompatActivity() {
    private val mDisposable: CompositeDisposable = CompositeDisposable()
    @Inject
    lateinit var mSettingHelper: SettingHelper
    @Inject
    lateinit var mAppDatabase: AppDatabase
    @Inject
    lateinit var mRetrofit: Retrofit
    private lateinit var mDialog: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerShareAddComponent.builder()
                .appComponent(App.sAppComponent)
                .build().inject(this)
        super.onCreate(savedInstanceState)
        mDialog = MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.md_loading)
                .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                .cancelable(true)
                .build()
        mDialog.show()

        val intent = intent
        val action = intent.action
        val type = intent.type
        val shareText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (action == Intent.ACTION_SEND && type == "text/plain" && !TextUtils.isEmpty(shareText)) {
            handleSendText(shareText)
        } else {
            onFinish(getString(R.string.formal_incorrect))
        }
    }

    private fun handleSendText(shareText: String) {

        val regex = "^.*http.*://zhuanlan.zhihu.com/(.*)$"
        val matcher = Pattern.compile(regex).matcher(shareText)
        if (matcher.find()) {
            val slug = matcher.group(1).toLowerCase()
            mAppDatabase.ZhuanlanNewDao().query(Constant.TYPE_USERADD)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { list ->
                        for (bean in list) {
                            if (bean.slug == slug) {
                                onFinish(getString(R.string.has_been_added))
                                return@Consumer
                            }
                        }
                    }).let { mDisposable.add(it) }

            mRetrofit.create(IApi::class.java).getZhuanlanBean(slug)
                    .subscribeOn(Schedulers.io())
                    .map { bean ->
                        bean.type = Constant.TYPE_USERADD
                        return@map mAppDatabase.ZhuanlanNewDao().insert(bean).toInt() != -1
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ isSuccess ->
                        if (isSuccess!!) {
                            onFinish(getString(R.string.add_zhuanlan_id_success))
                        } else {
                            onFinish(getString(R.string.add_zhuanlan_id_error))
                        }
                    }) { onFinish(getString(R.string.add_zhuanlan_id_error)) }
                    .let { mDisposable.add(it) }
        } else {
            onFinish(getString(R.string.incorrect_link))
        }
    }

    override fun onDestroy() {
        mDisposable.clear()
        super.onDestroy()
    }

    private fun onFinish(message: String) {
        mDialog.dismiss()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({ finish() }, 800)
    }

    companion object {

        internal val TAG = "ShareAddActivity"
    }
}
