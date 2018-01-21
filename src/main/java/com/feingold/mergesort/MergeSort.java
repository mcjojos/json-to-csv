package com.feingold.mergesort;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of MergeSort.
 *
 * @author karanikasg@gmail.com
 */
public class MergeSort {

    public static <T extends Comparable> void sort(List<T> list) {
        if (list.size() <= 1) {
            return;
        }

        List<T> leftList = new ArrayList<>();
        List<T> rightList = new ArrayList<>();
        copy(list, 0, list.size() / 2, leftList);
        copy(list, (list.size() / 2), list.size(), rightList);

        sort(leftList);
        sort(rightList);

        merge(leftList, rightList, list);
    }

    private static <T extends Comparable> void merge(List<T> srcLeft, List<T> srcRight, List<T> destList) {
        int indexLeft = 0;
        int indexRight = 0;
        int indexMerged = 0;
        while (indexLeft < srcLeft.size() && indexRight < srcRight.size()) {
            T leftValue = srcLeft.get(indexLeft);
            T rightValue = srcRight.get(indexRight);
            if (leftValue.compareTo(rightValue) < 0) {
                // if the left is smaller than the right consume the value and escape the inner loop of the right collection
                destList.set(indexMerged++, leftValue);
                indexLeft++;
            } else {
                // while the right one is smaller than the left keep up consuming elements from that list
                destList.set(indexMerged++, rightValue);
                indexRight++;
            }
        }
        if (indexLeft < srcLeft.size()) {
            set(srcLeft, indexLeft, destList, indexMerged, srcLeft.size());
        } else {
            set(srcRight, indexRight, destList, indexMerged, srcRight.size());
        }
    }

    private static <T> void copy(List<T> src, int start, int end, List<T> dest) {
        for (int i = start; i < end; i++) {
            dest.add(src.get(i));
        }
    }

    private static <T> void set(List<T> src, int start, List<T> dest, int destPos, int end) {
        for (int i = start; i < end && destPos < dest.size(); i++) {
            dest.set(destPos++, src.get(i));
        }
    }
}
