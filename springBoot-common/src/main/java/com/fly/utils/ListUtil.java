package com.fly.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * list工具类
 */
public class ListUtil {

    /**
     * 数组转ArrayList
     *
     * @param array 数组
     * @param <T>   数组泛型
     * @return ArrayList
     */
    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>(array.length);
        Collections.addAll(list, array);
        return list;
    }

    public static <T> List<T> itemsToList(T... items) {
        return null == items || items.length == 0 ? Collections.emptyList() : arrayToList(items);
    }
}
