package com.meiji.daily;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.mvp.zhuanlan.ZhuanlanPresenter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Meiji on 2016/12/1.
 */

public class AddActivity extends RxAppCompatActivity {

    private static final String TAG = "AddActivity";
    //    private ZhuanlanDao zhuanlanDao = new ZhuanlanDao();
    private boolean result = false;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .content(R.string.md_loading)
                .cancelable(true)
                .build();
        dialog.show();

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
            Observable
                    .create(new ObservableOnSubscribe<List<ZhuanlanBean>>() {
                        @Override
                        public void subscribe(ObservableEmitter<List<ZhuanlanBean>> e) throws Exception {
                            List<ZhuanlanBean> query = InitApp.db.ZhuanlanNewDao().query(ZhuanlanPresenter.TYPE_USERADD);
                            e.onNext(query);
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.<List<ZhuanlanBean>>bindToLifecycle())
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

            IApi IApi = RetrofitFactory.getRetrofit().create(IApi.class);
            Observable<ZhuanlanBean> observable = IApi.getZhuanlanBeanRx(slug);
            observable.subscribeOn(Schedulers.io())
                    .map(new Function<ZhuanlanBean, Boolean>() {
                        @Override
                        public Boolean apply(ZhuanlanBean bean) throws Exception {
                            bean.setType(ZhuanlanPresenter.TYPE_USERADD);
                            result = InitApp.db.ZhuanlanNewDao().insert(bean) != -1;
                            return result;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(this.<Boolean>bindToLifecycle())
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
        } else {
            onFinish(getString(R.string.incorrect_link));
        }
    }

    private void onFinish(String message) {
        dialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 800);
    }
}
