package com.toolschallenge.api.gestao.pagamento.util;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

public class RandomCollectionUtils {

    public static <T> T pickRandom(final T[] array) {
        if (isEmpty(array)) {
            return null;
        }

        return array[current().nextInt(0, array.length)];
    }

}
