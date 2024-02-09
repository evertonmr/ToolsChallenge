package com.toolschallenge.api.gestao.pagamento.integration.processadora.util;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomCollectionUtils {

    public static <T> T pickRandom(final T[] array) {
        if (isEmpty(array)) {
            return null;
        }

        return array[current().nextInt(0, array.length)];
    }

    public static <T> T pickRandomExcept(final T[] array, final T exception){
        final List<T> list = new ArrayList<>();
        list.addAll(Arrays.asList(array));
        list.remove(exception);
        return pickRandom(list.toArray((T[]) new Object[list.size()]));
    }
}
