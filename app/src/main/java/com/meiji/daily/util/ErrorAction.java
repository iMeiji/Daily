package com.meiji.daily.util;

import com.meiji.daily.BuildConfig;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Meiji on 2017/12/6.
 */

public abstract class ErrorAction {

    public static Consumer<Throwable> error() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                print(throwable);
            }
        };
    }

    public static void print(@NonNull Throwable throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace();
        }
    }

    public Consumer<Throwable> action() {
        return new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                print(throwable);
                doAction();
            }
        };
    }

    public abstract void doAction();
}
