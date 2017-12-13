package com.meiji.daily;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.data.remote.IApi;
import com.meiji.daily.util.RetrofitFactory;
import com.meiji.daily.util.SettingUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/1.
 */

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";
    private boolean isResult = false;
    private MaterialDialog mDialog;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.md_loading)
                .theme(SettingUtil.getInstance().getIsNightMode() ? Theme.DARK : Theme.LIGHT)
                .cancelable(true)
                .build();
        mDialog.show();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (action.equals(Intent.ACTION_SEND) && type != null) {
            if (type.equals("text/plain")) {
                String shareText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (!TextUtils.isEmpty(shareText)) {
                    handleSendText(shareText);
                }
            }
        } else {
            onFinish(getString(R.string.formal_incorrect));
        }
    }

    private void handleSendText(final String shareText) {

        final String regex = "^.*http.*://zhuanlan.zhihu.com/(.*)$";
        final Matcher matcher = Pattern.compile(regex).matcher(shareText);
        if (matcher.find()) {
            final String slug = matcher.group(1).toLowerCase();
            Disposable subscribe = InitApp.sDatabase.ZhuanlanNewDao().query(Constant.TYPE_USERADD)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<ZhuanlanBean>>() {
                        @Override
                        public void accept(List<ZhuanlanBean> list) throws Exception {
                            for (ZhuanlanBean bean : list) {
                                if (bean.getSlug().equals(slug)) {
                                    onFinish(getString(R.string.has_been_added));
                                    return;
                                }
                            }
                        }
                    });
            mDisposable.add(subscribe);

            Disposable disposable = RetrofitFactory.getRetrofit().create(IApi.class).getZhuanlanBean(slug)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ZhuanlanBean, Boolean>() {
                        @Override
                        public Boolean apply(ZhuanlanBean bean) throws Exception {
                            bean.setType(Constant.TYPE_USERADD);
                            isResult = InitApp.sDatabase.ZhuanlanNewDao().insert(bean) != -1;
                            return isResult;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(@NonNull Boolean result) throws Exception {
                            if (result) {
                                onFinish(getString(R.string.add_zhuanlan_id_success));
                            } else {
                                onFinish(getString(R.string.add_zhuanlan_id_error));
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(@NonNull Throwable throwable) throws Exception {
                            onFinish(getString(R.string.add_zhuanlan_id_error));
                        }
                    });
            mDisposable.add(disposable);
        } else {
            onFinish(getString(R.string.incorrect_link));
        }
    }

    @Override
    protected void onDestroy() {
        mDisposable.clear();
        super.onDestroy();
    }

    private void onFinish(String message) {
        mDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 800);
    }
}
