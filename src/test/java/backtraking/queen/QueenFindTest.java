package backtraking.queen;

import org.junit.Test;

public class QueenFindTest {

    @Test
    public void find() {
        QueenFind queenFind = new QueenFind(15);
        queenFind.find();
        queenFind.print();
    }
}