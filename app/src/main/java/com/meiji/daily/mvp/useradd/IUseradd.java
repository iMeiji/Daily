package com.meiji.daily.mvp.useradd;

import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.mvp.base.IBasePresenter;
import com.meiji.daily.mvp.base.IBaseView;

import java.util.List;

/**
 * Created by Meiji on 2016/11/27.
 */

public interface IUseradd {

    interface View extends IBaseView<Presenter> {

        void onCheckInputId();

        void onSetAdapter(List<ZhuanlanBean> list);

        void onAddSuccess();

        /**
         * 显示加载动画
         */
        void onShowLoading();

        /**
         * 隐藏加载
         */
        void onHideLoading();

        /**
         * 显示网络错误
         */
        void onShowNetError();
    }

    interface Presenter extends IBasePresenter {

        void doCheckInputId(String input);

        void doSetAdapter();

        void onFail();

//        void doOnClickItem(int position);

        void doRefresh();

        void doRemoveItem(int position);

        void doRemoveItemCancel(ZhuanlanBean bean);
    }
}
