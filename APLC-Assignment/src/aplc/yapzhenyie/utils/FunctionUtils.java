package aplc.yapzhenyie.utils;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author Yap Zhen Yie
 */
public class FunctionUtils {

    /**
     * Source: https://stackoverflow.com/a/27872852
     * @param <T>
     * @param keyExtractor
     * @return 
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

}
