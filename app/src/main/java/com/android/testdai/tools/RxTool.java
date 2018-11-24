package com.android.testdai.tools;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class RxTool {

    public static void autoDispose(Disposable... disposables) {

        for (Disposable disposable : disposables) {

            if (disposable != null && !disposable.isDisposed())
                disposable.dispose();

        }

    }

    public static final class TypeVoid {

        public static TypeVoid instance() {
            return new TypeVoid();
        }

    }

    public static class Optional<T> {

        private T value;

        public Optional(T value) {
            this.value = value;
        }

        public boolean hasValue() {
            return value != null;
        }

        public T get() {
            return value;
        }

        public Single<T> safeGet() {
            return Single.just(value);
        }

    }

    public static class OptionalWithError<T, E> extends Optional<T> {

        E error;

        public OptionalWithError(T value, E error) {
            super(value);
            this.error = error;
        }

        public boolean hasError() {
            return error != null;
        }

        public E getError() {
            return error;
        }

    }


}
