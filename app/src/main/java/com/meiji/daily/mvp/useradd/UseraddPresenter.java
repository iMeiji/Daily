package com.meiji.daily.mvp.useradd;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.daily.InitApp;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.mvp.postslist.PostsListView;

import java.util.List;

import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_NAME;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_POSTSCOUNT;
import static com.meiji.daily.bean.ZhuanlanBean.ZHUANLANBEAN_SLUG;
import static com.meiji.daily.mvp.zhuanlan.ZhuanlanModel.TYPE_USERADD;

/**
 * Created by Meiji on 2016/11/27.
 */

class UseraddPresenter implements IUseradd.Presenter {

    private IUseradd.View view;
    private IUseradd.Model model;
    private ZhuanlanDao zhuanlanDao;
    private List<ZhuanlanBean> list;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                doSaveInputId();
            }
            if (message.what == 0) {
                onFail();
            }
            return false;
        }
    });

    UseraddPresenter(IUseradd.View view) {
        this.view = view;
        this.model = new UseraddModel();
        zhuanlanDao = new ZhuanlanDao();
    }

    @Override
    public boolean doQueryDB() {
        list = zhuanlanDao.query(TYPE_USERADD);
        if (list.size() != 0) {
            doSetAdapter();
        }
        return list.size() != 0;
    }

    @Override
    public void doCheckInputId(final String input) {
        view.onShowRefreshing();
//        final String url = Api.COLUMN_URL + input;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.retrofitRequest(input);
                if (result) {
                    Message message = handler.obtainMessage(1);
                    message.sendToTarget();
                } else {
                    Message message = handler.obtainMessage(0);
                    message.sendToTarget();
                }
            }
        }).start();
    }

    @Override
    public void doSaveInputId() {
        try {
            ZhuanlanBean bean = model.getBean();
            zhuanlanDao.add(TYPE_USERADD, bean);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        view.onAddSuccess();
        doSetAdapter();
    }

    @Override
    public void doSetAdapter() {
        list = zhuanlanDao.query(TYPE_USERADD);
        view.onSetAdapter(list);
        view.onHideRefreshing();
    }

    @Override
    public void onFail() {
        view.onHideRefreshing();
        view.onAddFail();
    }

    @Override
    public void doOnClickItem(int position) {
        String slug = list.get(position).getSlug();
        String name = list.get(position).getName();
        int postsCount = list.get(position).getPostsCount();

        Intent intent = new Intent(InitApp.AppContext, PostsListView.class);
        intent.putExtra(ZHUANLANBEAN_SLUG, slug);
        intent.putExtra(ZHUANLANBEAN_NAME, name);
        intent.putExtra(ZHUANLANBEAN_POSTSCOUNT, postsCount);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        InitApp.AppContext.startActivity(intent);
    }

    @Override
    public void doRefresh() {
        view.onShowRefreshing();
        doSetAdapter();
        view.onHideRefreshing();
    }

    @Override
    public void doRemoveItem(final int position) {
        final ZhuanlanBean bean = list.get(position);
        zhuanlanDao.removeSlug(bean.getSlug());
        doSetAdapter();
    }

    @Override
    public void doRemoveItemCancel(ZhuanlanBean bean) {
        zhuanlanDao.add(TYPE_USERADD, bean);
        doSetAdapter();
    }
}
