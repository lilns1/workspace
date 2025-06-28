package Java8Learn;

public class ClassAdd<T extends Number> implements FunctionalAdd<T> {

    public T add(T a, T b) {
        if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() + b.intValue());
        }
        return null;
    }
}
