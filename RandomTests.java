/**
 * Created by Star on 12/29/16.
 */
public class RandomTests {
    public static void main(String[] args) {
        int[] b = new int[3];
        test(b);
        //b = test(b);
        for (int i : b) {
            System.out.print(i);
        }
        System.out.print(9/2);

    }
    public static int[] test(int[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] = 3;
        }
        for (int i : b) {
            System.out.print(i);
        }
        return b;
    }
}
