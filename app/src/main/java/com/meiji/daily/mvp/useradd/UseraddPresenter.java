package com.meiji.daily.mvp.useradd;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.database.dao.ZhuanlanDao;
import com.meiji.daily.mvp.postslist.PostsListView;
import com.meiji.daily.utils.Api;

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
    private Context mContext;
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

    UseraddPresenter(IUseradd.View view, Context mContext) {
        this.view = view;
        this.model = new UseraddModel();
        this.mContext = mContext;
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
    public void doCheckInputId(String input) {
        view.onShowRefreshing();
        final String url = Api.BASE_URL + input;
        System.out.println(url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = model.getRequestData(url);
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
        list = model.getList();
        for (ZhuanlanBean bean : list) {
            String type = String.valueOf(TYPE_USERADD);
            String avatarUrl = bean.getAvatar().getTemplate();
            String avatarId = bean.getAvatar().getId();
            String name = bean.getName();
            String followersCount = String.valueOf(bean.getFollowersCount());
            String postsCount = String.valueOf(bean.getPostsCount());
            String intro = bean.getIntro();
            String slug = bean.getSlug();
            zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
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

        Intent intent = new Intent(mContext, PostsListView.class);
        intent.putExtra(ZHUANLANBEAN_SLUG, slug);
        intent.putExtra(ZHUANLANBEAN_NAME, name);
        intent.putExtra(ZHUANLANBEAN_POSTSCOUNT, postsCount);
        mContext.startActivity(intent);
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
        String type = String.valueOf(TYPE_USERADD);
        String avatarUrl = bean.getAvatar().getTemplate();
        String avatarId = bean.getAvatar().getId();
        String name = bean.getName();
        String followersCount = String.valueOf(bean.getFollowersCount());
        String postsCount = String.valueOf(bean.getPostsCount());
        String intro = bean.getIntro();
        String slug = bean.getSlug();
        zhuanlanDao.add(type, avatarUrl, avatarId, name, followersCount, postsCount, intro, slug);
        doSetAdapter();
    }
}
