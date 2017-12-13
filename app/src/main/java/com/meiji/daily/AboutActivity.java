package com.meiji.daily;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.meiji.daily.module.base.BaseActivity;
import com.meiji.daily.util.SettingUtil;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.MITLicense;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

import static com.meiji.daily.R.id.changelogView;

/**
 * Created by Meiji on 2016/12/3.
 */

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView mTvVersion;
    private SettingUtil settingUtil = SettingUtil.getInstance();

    public static void start(@NonNull Context context) {
        Intent starter = new Intent(context, AboutActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int attachLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        try {
            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            mTvVersion.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        mTvVersion = findViewById(R.id.tv_version);
        LinearLayout changelogView = findViewById(R.id.changelogView);
        LinearLayout developersView = findViewById(R.id.developersView);
        LinearLayout licensesView = findViewById(R.id.licensesView);
        LinearLayout sourceCodeView = findViewById(R.id.sourceCodeView);
        LinearLayout copyRightView = findViewById(R.id.copyRightView);
        initToolBar((Toolbar) findViewById(R.id.toolbar), true, null);

        changelogView.setOnClickListener(this);
        developersView.setOnClickListener(this);
        licensesView.setOnClickListener(this);
        sourceCodeView.setOnClickListener(this);
        copyRightView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case changelogView:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))));
                break;
            case R.id.developersView:
                MaterialDialog devDialog = new MaterialDialog.Builder(this)
                        .title(R.string.about_developers_label)
                        .content(R.string.about_developers)
                        .theme(settingUtil.getIsNightMode() ? Theme.DARK : Theme.LIGHT)
                        .positiveText(android.R.string.ok)
                        .build();
                devDialog.getActionButton(DialogAction.POSITIVE).setTextColor(settingUtil.getColor());
                devDialog.show();
                break;
            case R.id.licensesView:
                createLicenseDialog();
                break;
            case R.id.sourceCodeView:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))));
                break;
            case R.id.copyRightView:
                MaterialDialog copyRightDialog = new MaterialDialog.Builder(this)
                        .title(R.string.action_copyright)
                        .theme(settingUtil.getIsNightMode() ? Theme.DARK : Theme.LIGHT)
                        .content(R.string.copyright_content)
                        .positiveText(R.string.md_got_it)
                        .build();
                copyRightDialog.getActionButton(DialogAction.POSITIVE).setTextColor(settingUtil.getColor());
                copyRightDialog.show();
                break;
        }
    }

    private void createLicenseDialog() {
        Notices notices = new Notices();
        notices.addNotice(new Notice("Material Dialogs", "https://github.com/afollestad/material-dialogs", "Copyright (c) 2014-2016 Aidan Michael Follestad", new MITLicense()));
        notices.addNotice(new Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Gson", "https://github.com/google/sGson", "Copyright 2008 Google Inc.", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "Copyright 2014 - 2016 Henning Dodenhof", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.source_code_url));
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}