package code.hashcode.y2020;

import java.io.*;
import java.util.*;

public class Solution {
    public static class Library {
        // the id of the library
        private int id;

        // the books in library
        private List<Integer> books;

        // the number of days it takes to finish the library signup
        private int sign_up;

        // the number of books that can be shipped from library per day
        private int ship_per_day;

        public Library(int id, List<Integer> books, int sign_up, int ship_per_day) {
            this.id = id;
            this.books = books;
            this.sign_up = sign_up;
            this.ship_per_day = ship_per_day;
        }

        private Set<Integer> choose(int days, Set<Integer> scanned) {
            days -= this.sign_up;
            Set<Integer> choice = new HashSet<>();
            for (int book : this.books) {
                if (!scanned.contains(book)) {
                    choice.add(book);
                    scanned.add(book);
                    if (choice.size() == this.ship_per_day * days) return choice;
                }
            }
            return choice;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] inputs = new String[]{
                "a_example.txt",
                "b_read_on.txt",
                "c_incunabula.txt",
                "d_tough_choices.txt",
                "e_so_many_books.txt",
                "f_libraries_of_the_world.txt"
        };
        for (String input : inputs) {
            try (
                    Scanner sc = new Scanner(new BufferedReader(new FileReader(input)));
                    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(input + ".out")))
            ) {
                Set<Integer> scanned = new HashSet<>();

                // There are B different books with IDs from 0 to B–1
                int[] scores = new int[sc.nextInt()];

                // There are L different libraries with IDs from 0 to L–1
                Library[] libraries = new Library[sc.nextInt()];

                // There are D days from day 0 to day D–1
                int D = sc.nextInt();

                // the score of individual books
                for (int i = 0; i < scores.length; i++) scores[i] = sc.nextInt();

                // the IDs of the books in the library
                for (int library = 0; library < libraries.length; library++) {
                    // the number of books in library
                    int N = sc.nextInt();
                    // the number of days it takes to finish
                    // the library signup process for library
                    int T = sc.nextInt();
                    // the number of books that can be shipped from library
                    int M = sc.nextInt();
                    List<Integer> books = new ArrayList<>(N);
                    for (int j = 0; j < N; j++) books.add(sc.nextInt());
                    // let books sorted in increasing order
                    books.sort((a, b) -> scores[b] - scores[a]);
                    libraries[library] = new Library(library, books, T, M);
                }

                long[] heuristic = new long[libraries.length];
                for (int i = 0; i < libraries.length; i++) {
                    heuristic[i] = libraries[i].books.stream().limit(((long) (D - libraries[i].sign_up)) * libraries[i].ship_per_day * libraries[i].ship_per_day).map(book -> scores[book]).mapToLong(Integer::intValue).sum() / libraries[i].sign_up / libraries[i].sign_up;
                }

                Comparator<Library> comparator = Comparator.comparingLong(a -> heuristic[a.id]);
                Arrays.sort(libraries, comparator.reversed());

                // decode which libraries will be opened
                List<Library> opened = new ArrayList<>();
                List<Set<Integer>> choices = new ArrayList<>();
                int days_left = D;
                for (int i = 0; i < libraries.length; i++) {
                    if (days_left < libraries[i].sign_up) break;
                    Set<Integer> choice = libraries[i].choose(days_left, scanned);
                    if (!choice.isEmpty()) {
                        days_left -= libraries[i].sign_up;
                        choices.add(choice);
                        opened.add(libraries[i]);
                    }
                }

                // output part
                // number of libraries will be signed up for scanning.
                pw.println(opened.size());
                StringBuilder sb = new StringBuilder();
                // for each libraries to be opened
                for (int i = 0; i < opened.size(); i++) {
                    // first line output the library to be opened
                    // and the n of books to be scanned
                    sb.setLength(0);
                    sb.append(opened.get(i).id);
                    sb.append(' ');
                    sb.append(choices.get(i).size());
                    pw.println(sb.toString());
                    sb.setLength(0);
                    for (int id : choices.get(i)) {
                        sb.append(id);
                        sb.append(' ');
                    }
                    sb.setLength(sb.length() - 1);
                    pw.println(sb.toString());
                }
            }
        }
    }
}
