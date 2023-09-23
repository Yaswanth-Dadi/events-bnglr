package com.spring.reactiveConsumer;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class CustomSubscription<T> extends BaseSubscriber<T> {
    long batchLimit = 2;

    @Override
    protected void hookOnSubscribe(Subscription s) {
        s.request(batchLimit);
    }

    @Override
    protected void hookOnNext(T value) {
        batchLimit--;
        if (batchLimit == 0) {
            batchLimit = 2;
            request(batchLimit);
        }
    }
}
