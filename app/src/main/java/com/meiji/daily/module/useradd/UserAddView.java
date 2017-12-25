package com.meiji.daily.module.useradd;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
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
import com.afollestad.materialdialogs.Theme;
import com.meiji.daily.App;
import com.meiji.daily.R;
import com.meiji.daily.bean.ZhuanlanBean;
import com.meiji.daily.binder.ZhuanlanViewBinder;
import com.meiji.daily.module.base.BaseFragment;
import com.meiji.daily.util.RecyclerViewUtil;
import com.meiji.daily.util.SettingUtil;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Meiji on 2017/12/4.
 */

public class UserAddView extends BaseFragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView mTvDesc;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private MaterialDialog mDialog;
    private LinearLayout mRoot;

    private List<ZhuanlanBean> mList;
    private MultiTypeAdapter mAdapter;
    private UserAddViewModel mModel;
    private boolean mIsdelete;

    @Override
    protected int attachLayoutId() {
        return R.layout.fragment_useradd;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void subscribeUI() {
        UserAddViewModel.Factory factory = new UserAddViewModel.Factory(App.sApp);
        mModel = ViewModelProviders.of(this, factory).get(UserAddViewModel.class);
        mModel.getList().observe(this, new Observer<List<ZhuanlanBean>>() {
            @Override
            public void onChanged(@Nullable List<ZhuanlanBean> list) {
                if (null != list && list.size() > 0) {
                    onSetAdapter(list);
                } else {
                    onShowNetError();
                }
            }
        });
        mModel.isLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    onShowLoading();
                } else {
                    onHideLoading();
                }
            }
        });
        mModel.isRefreshUI().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                refreshUI();
            }
        });
        mModel.isAddResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    onAddSuccess();
                } else {
                    onAddFail();
                }
            }
        });
    }

    @Override
    protected void initViews(View view) {
        mTvDesc = view.findViewById(R.id.tv_description);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        FloatingActionButton fab_add = view.findViewById(R.id.fab_add);
        mRefreshLayout = view.findViewById(R.id.refresh_layout);
        mRoot = view.findViewById(R.id.root);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // 设置下拉刷新的按钮的颜色
        mRefreshLayout.setColorSchemeColors(SettingUtil.getInstance().getColor());
        mRefreshLayout.setOnRefreshListener(this);

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
                        final ZhuanlanBean bean = mList.get(position);
                        final String name = mList.get(position).getName();
                        mList.remove(bean);
                        mAdapter.notifyDataSetChanged();
                        mIsdelete = true;
                        Snackbar.make(mRecyclerView, getString(R.string.deleted) + name, Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        mIsdelete = false;
                                        mModel.handleData();
                                    }
                                })
                                .show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mIsdelete) {
                                    mModel.deleteItem(bean);
                                }
                            }
                        }, 1500);
                    }
                });
        helper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab_add) {
            createDialog();
        }
    }

    private void refreshUI() {
        Resources.Theme theme = getActivity().getTheme();
        Resources resources = getResources();
        TypedValue rootViewBackground = new TypedValue();
        TypedValue itemViewBackground = new TypedValue();
        TypedValue textColorPrimary = new TypedValue();
        theme.resolveAttribute(R.attr.rootViewBackground, rootViewBackground, true);
        theme.resolveAttribute(R.attr.itemViewBackground, itemViewBackground, true);
        theme.resolveAttribute(R.attr.textColorPrimary, textColorPrimary, true);
        mRoot.setBackgroundResource(rootViewBackground.resourceId);
        mTvDesc.setTextColor(resources.getColor(textColorPrimary.resourceId));

        int childCount = mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            CardView cardView = mRecyclerView.getChildAt(i).findViewById(R.id.cardview);
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

        RecyclerViewUtil.invalidateCacheItem(mRecyclerView);
    }

    private void createDialog() {
        mDialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.md_zhuanlan_add_title)
                .content(R.string.md_zhuanlan_add_content)
                .theme(SettingUtil.getInstance().getIsNightMode() ? Theme.DARK : Theme.LIGHT)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {

                    }
                })
                .build();

        // 设置3个按键
        mDialog.setActionButton(DialogAction.NEGATIVE, R.string.md_cancel);
        mDialog.setActionButton(DialogAction.POSITIVE, R.string.md_ok);
        mDialog.setActionButton(DialogAction.NEUTRAL, R.string.md_zhuanlan_add_help);

        mDialog.getActionButton(DialogAction.NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mDialog.getActionButton(DialogAction.POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 校验填写 id 是否正确
                onCheckInputId();
                mDialog.dismiss();
            }
        });

        mDialog.getActionButton(DialogAction.NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 什么是专栏 id
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.add_zhuanlan_id_help_url))));
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }

    public void onCheckInputId() {
        String input = mDialog.getInputEditText().getText().toString();
        if (!TextUtils.isEmpty(input)) {
            mModel.addItem(input.trim().toLowerCase());
        }
    }

    public void onSetAdapter(final List<ZhuanlanBean> list) {
        if (mAdapter == null) {
            mAdapter = new MultiTypeAdapter();
            mAdapter.register(ZhuanlanBean.class, new ZhuanlanViewBinder());
            mRecyclerView.setAdapter(mAdapter);
        }
        mList = list;
        mAdapter.setItems(mList);
        mAdapter.notifyDataSetChanged();

        if (mList.size() == 0) {
            mTvDesc.setVisibility(View.VISIBLE);
        } else {
            mTvDesc.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {
        mModel.handleData();
    }

    public void onAddSuccess() {
        Snackbar.make(mRecyclerView, R.string.add_zhuanlan_id_success, Snackbar.LENGTH_SHORT).show();
        mTvDesc.setVisibility(View.GONE);
    }

    public void onShowLoading() {
        mRefreshLayout.setRefreshing(true);
    }

    public void onHideLoading() {
        mRefreshLayout.setRefreshing(false);
    }

    public void onAddFail() {
        Snackbar.make(mRecyclerView, R.string.add_zhuanlan_id_error, Snackbar.LENGTH_SHORT).show();
        mRefreshLayout.setEnabled(true);
    }

    private void onShowNetError() {
        Snackbar.make(mRefreshLayout, R.string.network_error, Snackbar.LENGTH_SHORT).show();
        mRefreshLayout.setEnabled(true);
    }
}
