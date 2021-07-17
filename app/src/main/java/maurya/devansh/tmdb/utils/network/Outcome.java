package maurya.devansh.tmdb.utils.network;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Created by devansh on 17/07/21.
 */

public class Outcome<T> {

    public static class Success<T> extends Outcome<T> {
        private final T _data;

        Success(T data) {
            _data = data;
        }

        public T getData() {
            return _data;
        }
    }

    public static class Failure<T> extends Outcome<T> {
        private final Throwable _throwable;

        Failure(Throwable throwable) {
            _throwable = throwable;
        }

        public Throwable getThrowable() {
            return _throwable;
        }
    }

    @NotNull
    @Contract("_ -> new")
    public static <T> Success<T> success(T data) {
        return new Success<>(data);
    }

    @NotNull
    @Contract("_ -> new")
    public static <T> Failure<T> failure(Throwable throwable) {
        return new Failure<>(throwable);
    }
}
