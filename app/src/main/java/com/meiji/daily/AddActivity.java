package com.meiji.daily;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.utils.Api;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/12/1.
 */

public class AddActivity extends BaseActivity {

    private static final String TAG = "AddActivity";
    private ZhuanlanDao zhuanlanDao = new ZhuanlanDao();
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

    private void handleSendText(String shareText) {
        final String regex = "^.*http.*://zhuanlan.zhihu.com/(.*)$";
        final Matcher matcher = Pattern.compile(regex).matcher(shareText);
        if (matcher.find()) {
            final String slug = matcher.group(1).toLowerCase();
            List<ZhuanlanBean> query = zhuanlanDao.query(TYPE_USERADD);
            for (ZhuanlanBean bean : query) {
                if (bean.getSlug().equals(slug)) {
                    onFinish(getString(R.string.has_been_added));
                    return;
                }
            }

            RxTest(slug);

//            Api api = RetrofitFactory.getRetrofit().create(Api.class);
//            Call<ZhuanlanBean> call = api.getZhuanlanBean(slug);
//            call.enqueue(new Callback<ZhuanlanBean>() {
//                @Override
//                public void onResponse(Call<ZhuanlanBean> call, Response<ZhuanlanBean> response) {
//                    try {
//                        ZhuanlanBean bean = response.body();
//                        String type = String.valueOf(TYPE_USERADD);
//                        String avatarUrl = bean.getAvatar().getTemplate();
//                        String avatarId = bean.getAvatar().getId();
//                        String name = bean.getName();
//                        String followersCount = String.valueOf(bean.getFollowersCount());
//                        String postsCount = String.valueOf(bean.getPostsCount());
//                        String intro = bean.getIntro();
//                        String slug = bean.getSlug();
//                        result = zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                    }
//                    if (result) {
//                        onFinish("保存成功");
//                    } else {
//                        onFinish("保存失败");
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ZhuanlanBean> call, Throwable t) {
//                    onFinish("保存失败");
//                }
//            });

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

    private void RxTest(final String slug) {
        Api api = RetrofitFactory.getRetrofit().create(Api.class);
        Observable<ZhuanlanBean> observable = api.getZhuanlanRx(slug);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhuanlanBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ZhuanlanBean bean) {
                        result = zhuanlanDao.add(TYPE_USERADD, bean);
                        if (result) {
                            onFinish("保存成功");
                        } else {
                            onFinish("保存失败");
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onFinish("保存失败");
                    }

                    @Override
                    public void onComplete() {
                        onFinish("保存成功");
                    }
                });
    }
}
