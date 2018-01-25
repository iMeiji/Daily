package com.meiji.daily.util

import com.crashlytics.android.Crashlytics
import com.meiji.daily.BuildConfig

import io.reactivex.annotations.NonNull
import io.reactivex.functions.Consumer

/**
 * Created by Meiji on 2018/1/25.
 */

abstract class ErrorAction {

    fun action(): Consumer<Throwable> {
        return Consumer { throwable ->
            print(throwable)
            doAction()
        }
    }

    abstract fun doAction()

    companion object {

        fun error(): Consumer<Throwable> {
            return Consumer { throwable -> print(throwable) }
        }

        fun print(@NonNull throwable: Throwable) {
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace()
            } else {
                Crashlytics.logException(throwable)
            }
        }
    }
}
