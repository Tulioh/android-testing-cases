package com.tuliohdev.myapplication;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.Callable;

public class TrampolineSchedulerRule implements TestRule {

    private final Function<Scheduler, Scheduler> trampolineScheduler = new Function<Scheduler, Scheduler>() {
        @Override public Scheduler apply(Scheduler scheduler) throws Exception {
            return Schedulers.trampoline();
        }
    };

    @Override
    public Statement apply(final Statement statement, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                RxJavaPlugins.setIoSchedulerHandler(trampolineScheduler);
                RxJavaPlugins.setComputationSchedulerHandler(trampolineScheduler);
                RxJavaPlugins.setNewThreadSchedulerHandler(trampolineScheduler);
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
                    @Override public Scheduler apply(Callable<Scheduler> schedulerCallable) {
                        return Schedulers.trampoline();
                    }
                });

                try {
                    statement.evaluate();
                } finally {
                    RxJavaPlugins.reset();
                    RxAndroidPlugins.reset();
                }
            }
        };
    }

}
