package wiki.mdzz.leetcode.set;

import java.util.Arrays;
import java.util.HashSet;

public class _349_两个数组的交集 {
    public int[] intersection(int[] nums1, int[] nums2) {
        var set1 = new HashSet<Integer>();
        for (var a : nums1) {
            set1.add(a);
        }

        var set2 = new HashSet<Integer>();
        for (var a : nums2) {
            set2.add(a);
        }

        var idx = 0;
        int[] result;
        if (set1.size() < set2.size()) {
            result = new int[set1.size()];
            for (var a : set1) {
                if (set2.contains(a)) {
                    result[idx++] = a;
                }
            }
        } else {
            result = new int[set2.size()];
            for (var a : set2) {
                if (set1.contains(a)) {
                    result[idx++] = a;
                }
            }
        }
        return Arrays.copyOf(result, idx);
    }
}
