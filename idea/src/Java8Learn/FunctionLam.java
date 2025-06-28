package Java8Learn;

public class FunctionLam {
    public static void  main(String[] args) {
        FunctionalAdd<Integer> f1 = (x, y) -> {
            return x + y;
        };

        System.out.println(f1.add(2, 3));
    }

}
