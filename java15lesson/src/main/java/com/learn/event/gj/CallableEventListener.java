package com.learn.event.gj;

import java.util.EventListener;
import java.util.concurrent.Callable;

public interface CallableEventListener extends EventListener, Callable<String> {
}
