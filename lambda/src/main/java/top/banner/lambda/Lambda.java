package top.banner.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class Lambda {

    public static void main(final String[] args) {
//        allMatch();

//        andThen();

        // i1 -> 10
        // i2 -> 5
        // true if a/2 == b
        boolean result = compare((a, b) -> a / 2 == b, 10, 5);
        System.out.println("Compare result: " + result);
    }

    /**
     * 检查列表中的所有值是否与条件匹配。
     * {@link java.util.stream.Stream#allMatch}
     */
    public static void allMatch() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");


        boolean allStartsWithA = stringCollection
                .stream()
                .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false
    }

    /**
     * 如何组合函数使用andThen
     */
    public static void andThen() {
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String, String> backToString = toInteger
                .andThen(String::valueOf);

        System.out.println(backToString.apply("123"));
    }


    /**
     * 如何将两个值与BiPredicate进行比较。
     */
    public static boolean compare(BiPredicate<Integer, Integer> bi, Integer i1,
                                  Integer i2) {
        return bi.test(i1, i2);
    }

}