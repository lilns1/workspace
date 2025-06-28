package Java8Learn;
@FunctionalInterface
public interface FunctionalAdd<T extends Number> {
    T add(T a, T b);
}
