package leetcodecn;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Interview45ArrangeTheArrayToTheSmallestNumber {
    public String minNumber(int[] nums) {
        List<String> numbers = Arrays.stream(nums).mapToObj(Integer::toString).collect(Collectors.toList());
        Collections.sort(numbers, (a, b) -> (a + b).compareTo(b + a));
        return String.join("", numbers);
    }
}
