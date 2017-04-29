package com.meiji.daily.mvp.useradd;

import com.meiji.daily.bean.ZhuanlanBean;

import java.util.List;

/**
 * Created by Meiji on 2016/11/27.
 */

public interface IUseradd {

    interface View {

        void onCheckInputId();

        void onSetAdapter(List<ZhuanlanBean> list);

        void onShowRefreshing();

        void onHideRefreshing();

        void onAddFail();

        void onAddSuccess();

    }

    interface Presenter {

        void doCheckInputId(String input);

        void doSetAdapter();

        void onFail();

        void doOnClickItem(int position);

        void doRefresh();

        void doRemoveItem(int position);

        void doRemoveItemCancel(ZhuanlanBean bean);
    }

    @Deprecated
    interface Model {

        boolean retrofitRequest(String slug);

        /**
         * 结束
         */
        void onDestroy();
    }
}
