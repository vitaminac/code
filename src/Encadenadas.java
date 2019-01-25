import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

class Encadenadas {
    public static void main(String[] args) throws Exception {
        HashMap<String, Integer> beginning = new HashMap<>();
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Scanner scanner = new Scanner(br);
            while (scanner.hasNext()) {
                words.clear();
                beginning.clear();
                int num = scanner.nextInt();
                scanner.nextLine();
                for (int i = 0; i < num; i++) {
                    String word = scanner.nextLine();
                    words.add(word);
                    String start = word.substring(0, 2);
                    if (beginning.containsKey(start)) {
                        beginning.put(start, beginning.get(start) + 1);
                    } else {
                        beginning.put(start, 1);
                    }
                }

                int count = 0;
                for (String word : words) {
                    String end = word.substring(word.length() - 2);
                    count += word.substring(0, 2).equals(end) ? (beginning.get(end) == null ? 0 : beginning.get(end)) - 1 : (beginning.get(end) == null ? 0 : beginning.get(end));
                }
                System.out.println(count);
            }
        }
    }
}
