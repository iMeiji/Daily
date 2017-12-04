package com.meiji.daily.mvp.useradd;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.meiji.daily.Constant;
import com.meiji.daily.R;
import com.meiji.daily.RxBus;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.injector.component.DaggerUserAddComponent;
import com.meiji.daily.injector.module.UserAddModule;
import com.meiji.daily.mvp.base.BaseFragment;
import com.meiji.daily.util.RecyclerViewUtil;
import com.meiji.daily.util.SettingUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2016/11/27.
 */
@Deprecated
public class UserAddView extends BaseFragment<IUserAdd.Presenter> implements IUserAdd.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView tvDesc;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private MaterialDialog dialog;
    private List<ZhuanlanBean> list = new ArrayList<>();
    private LinearLayout root;
    private Observable<Boolean> observable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        observable = RxBus.getInstance().register(Constant.RxBusEvent.REFRESHUI);
        observable.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean isNightMode) throws Exception {
                refreshUI();
            }
        });
    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(Constant.RxBusEvent.REFRESHUI, observable);
        super.onDestroy();
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_useradd;
    }

    @Override
    protected void initData() {
        presenter.doSetAdapter();
    }

    @Override
    protected void initInjector() {
        DaggerUserAddComponent.builder()
                .userAddModule(new UserAddModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initViews(View view) {
        tvDesc = view.findViewById(R.id.tv_description);
        recyclerView = view.findViewById(R.id.recycler_view);
        FloatingActionButton fab_add = view.findViewById(R.id.fab_add);
        refreshLayout = view.findViewById(R.id.refresh_layout);
        root = view.findViewById(R.id.root);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置下拉刷新的按钮的颜色
        refreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        refreshLayout.setOnRefreshListener(this);

        adapter = new MultiTypeAdapter();
        adapter.register(ZhuanlanBean.class, new ZhuanlanViewBinder());
        recyclerView.setAdapter(adapter);

        fab_add.setBackgroundTintList(ColorStateList.valueOf(SettingUtil.getInstance().getColor()));
        fab_add.setOnClickListener(this);

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        final ZhuanlanBean bean = list.get(position);
                        final String name = list.get(position).getName();
                        adapter.notifyItemRemoved(position);
                        presenter.doRemoveItem(position);
                        Snackbar.make(recyclerView, getString(R.string.deleted) + name, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        presenter.doRemoveItemCancel(bean);
                                    }
                                })
                                .show();
                    }
                });
        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add) {
            createDialog();
        }
    }

    private void refreshUI() {
        Resources.Theme theme = getActivity().getTheme();
        TypedValue rootViewBackground = new TypedValue();
        TypedValue itemViewBackground = new TypedValue();
        TypedValue textColorPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true);
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true);
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
        root.setBackgroundResource(rootViewBackground.resourceId);
        tvDesc.setTextColor(getResources().getColor(textColorPrimary.resourceId));

        Resources resources = getResources();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CardView cardView = recyclerView.getChildAt(i).findViewById(R.id.cardview);
            cardView.setBackgroundResource(itemViewBackground.resourceId);

            TextView tv_name = cardView.findViewById(R.id.tv_name);
            tv_name.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_followersCount = cardView.findViewById(R.id.tv_followersCount);
            tv_followersCount.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_postsCount = cardView.findViewById(R.id.tv_postsCount);
            tv_postsCount.setTextColor(resources.getColor(textColorPrimary.resourceId));

            TextView tv_intro = cardView.findViewById(R.id.tv_intro);
            tv_intro.setTextColor(resources.getColor(textColorPrimary.resourceId));
        }

        RecyclerViewUtil.invalidateCacheItem(recyclerView);
    }

    private void createDialog() {
        dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.md_zhuanlan_add_title)
                .content(R.string.md_zhuanlan_add_content)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .build();

        // 设置3个按键
        dialog.setActionButton(DialogAction.NEGATIVE, R.string.md_cancel);
        dialog.setActionButton(DialogAction.POSITIVE, R.string.md_ok);
        dialog.setActionButton(DialogAction.NEUTRAL, R.string.md_zhuanlan_add_help);

        dialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 校验填写 id 是否正确
                onCheckInputId();
                dialog.dismiss();
            }
        });

        dialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 什么是专栏 id
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.add_zhuanlan_id_help_url))));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onCheckInputId() {
        String input = dialog.getInputEditText().getText().toString();
        if (!TextUtils.isEmpty(input)) {
            presenter.doCheckInputId(input.trim().toLowerCase());
        }
    }

    @Override
    public void onSetAdapter(final List<ZhuanlanBean> mlist) {
        list = mlist;
        adapter.setItems(list);
        adapter.notifyDataSetChanged();

        if (list.size() == 0) {
            tvDesc.setVisibility(View.VISIBLE);
        } else {
            tvDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        presenter.doRefresh();
    }

    @Override
    public void onAddSuccess() {
        Snackbar.make(recyclerView, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show();
        tvDesc.setVisibility(View.GONE);
    }

    @Override
    public void onShowLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoading() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onShowNetError() {
        Snackbar.make(recyclerView, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show();
        refreshLayout.setEnabled(true);
    }
}
