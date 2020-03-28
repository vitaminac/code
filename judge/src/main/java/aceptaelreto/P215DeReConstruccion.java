package aceptaelreto;

import java.util.*;

public class P215DeReConstruccion {
    private static int i = 0;

    private static void postorder(int start, int end, StringBuilder sb, List<Integer> preorder, List<Integer> inorder, Map<Integer, Integer> map) {
        if (i < preorder.size() && start < end) {
            int val = preorder.get(i);
            int idx = map.get(preorder.get(i++));
            postorder(start, idx, sb, preorder, inorder, map);
            postorder(idx + 1, end, sb, preorder, inorder, map);
            sb.append(val);
            sb.append(' ');
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n;
            List<Integer> preorder = new ArrayList<>();
            List<Integer> inorder = new ArrayList<>();
            Map<Integer, Integer> map = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            while ((n = sc.nextInt()) != -1) {
                preorder.clear();
                inorder.clear();
                do preorder.add(n); while ((n = sc.nextInt()) != -1);
                while ((n = sc.nextInt()) != -1) inorder.add(n);
                for (int i = 0; i < inorder.size(); i++) map.put(inorder.get(i), i);
                sb.setLength(0);
                i = 0;
                postorder(0, preorder.size(), sb, preorder, inorder, map);
                sb.setLength(sb.length() - 1);
                System.out.println(sb.toString());
            }
        }
    }
}
