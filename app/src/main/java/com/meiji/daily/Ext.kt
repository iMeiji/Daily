package com.meiji.daily

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


val io get() = Schedulers.io()
val mainThread get() = AndroidSchedulers.mainThread()!!