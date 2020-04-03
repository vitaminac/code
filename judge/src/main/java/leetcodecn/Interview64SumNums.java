package leetcodecn;

public class Interview64SumNums {
    public int sumNums(int n) {
        // n*(n+1)/2
        // binary multiplication plus right shifting division
        int n1 = (n & -(n + 1 >> 0 & 1)) << 0;
        int n2 = (n & -(n + 1 >> 1 & 1)) << 1;
        int n3 = (n & -(n + 1 >> 2 & 1)) << 2;
        int n4 = (n & -(n + 1 >> 3 & 1)) << 3;
        int n5 = (n & -(n + 1 >> 4 & 1)) << 4;
        int n6 = (n & -(n + 1 >> 5 & 1)) << 5;
        int n7 = (n & -(n + 1 >> 6 & 1)) << 6;
        int n8 = (n & -(n + 1 >> 7 & 1)) << 7;
        int n9 = (n & -(n + 1 >> 8 & 1)) << 8;
        int n10 = (n & -(n + 1 >> 9 & 1)) << 9;
        int n11 = (n & -(n + 1 >> 10 & 1)) << 10;
        int n12 = (n & -(n + 1 >> 11 & 1)) << 11;
        int n13 = (n & -(n + 1 >> 12 & 1)) << 12;
        int n14 = (n & -(n + 1 >> 13 & 1)) << 13;
        return (n1 + n2 + n3 + n4 + n5 + n6 + n7 + n8 + n9 + n10 + n11 + n12 + n13 + n14) >> 1;
    }
}
