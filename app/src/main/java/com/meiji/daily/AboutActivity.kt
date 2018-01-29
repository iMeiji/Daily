package com.meiji.daily

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.DialogAction
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.meiji.daily.module.base.BaseActivity
import de.psdev.licensesdialog.LicensesDialog
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20
import de.psdev.licensesdialog.licenses.MITLicense
import de.psdev.licensesdialog.model.Notice
import de.psdev.licensesdialog.model.Notices
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Created by Meiji on 2016/12/3.
 */

class AboutActivity : BaseActivity(), View.OnClickListener {


    override fun attachLayoutId() = R.layout.activity_about

    override fun initData(savedInstanceState: Bundle) {
        try {
            val version = packageManager.getPackageInfo(packageName, 0).versionName
            tv_version.text = version
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    override fun initViews() {
        initToolBar(toolbar as Toolbar, true, null)

        changelogView.setOnClickListener(this)
        developersView.setOnClickListener(this)
        licensesView.setOnClickListener(this)
        sourceCodeView.setOnClickListener(this)
        copyRightView.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.changelogView -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.changelog_url))))
            R.id.developersView -> {
                val devDialog = MaterialDialog.Builder(this)
                        .title(R.string.about_developers_label)
                        .content(R.string.about_developers)
                        .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                        .positiveText(android.R.string.ok)
                        .build()
                devDialog.getActionButton(DialogAction.POSITIVE).setTextColor(mSettingHelper.color)
                devDialog.show()
            }
            R.id.licensesView -> createLicenseDialog()
            R.id.sourceCodeView -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.source_code_url))))
            R.id.copyRightView -> {
                val copyRightDialog = MaterialDialog.Builder(this)
                        .title(R.string.action_copyright)
                        .theme(if (mSettingHelper.isNightMode) Theme.DARK else Theme.LIGHT)
                        .content(R.string.copyright_content)
                        .positiveText(R.string.md_got_it)
                        .build()
                copyRightDialog.getActionButton(DialogAction.POSITIVE).setTextColor(mSettingHelper.color)
                copyRightDialog.show()
            }
        }
    }

    private fun createLicenseDialog() {
        val notices = Notices()
        notices.addNotice(Notice("Material Dialogs", "https://github.com/afollestad/material-dialogs", "Copyright (c) 2014-2016 Aidan Michael Follestad", MITLicense()))
        notices.addNotice(Notice("OkHttp", "https://github.com/square/okhttp", "Copyright 2016 Square, Inc.", ApacheSoftwareLicense20()))
        notices.addNotice(Notice("Gson", "https://github.com/google/sGson", "Copyright 2008 Google Inc.", ApacheSoftwareLicense20()))
        notices.addNotice(Notice("Glide", "https://github.com/bumptech/glide", "Sam Judd - @sjudd on GitHub, @samajudd on Twitter", ApacheSoftwareLicense20()))
        notices.addNotice(Notice("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "Copyright 2014 - 2016 Henning Dodenhof", ApacheSoftwareLicense20()))

        LicensesDialog.Builder(this)
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                val shareIntent = Intent()
                        .setAction(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text) + getString(R.string.source_code_url))
                startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun start(context: Context) {
            val starter = Intent(context, AboutActivity::class.java)
            context.startActivity(starter)
        }
    }
}