package gkzxhn.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by 方 on 2016/12/24.
 */

public class RxBus {

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    private static class InstanceHolder {
        private static RxBus instance = new RxBus();
    }

    public static RxBus getInstance() {return InstanceHolder.instance;}

    private final Subject<Object, Object> bus;

    public void send(Object o) {
        bus.onNext(o);
    }

    public<T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
