package pkg8.puzzle;

import java.util.Arrays;
import java.util.Collections;

public class Shuffle1 {

    public static int[][] config(Integer data[]) {
        int puz[][] = new int[3][3];
        int arr[] = new int[9];
        int i = 0, j = 0, k = 0, inversions;

        Collections.shuffle(Arrays.asList(data));
        k = 0;
        for (Integer x : data) {
            arr[k++] = x.intValue();
        }

        inversions = Shuffle1.getinversions(arr);

        if (inversions % 2 != 0) {
            i = 0;
            for (j = 0; j < 3; j++) {
                for (k = 0; k < 3; k++) {
                    puz[j][k] = arr[i++];
                }
            }
            for (j = 0; j < 3; j++) {
                for (k = 0; k < 3; k++) {
                    System.out.print(puz[j][k]);
                }
                System.out.println();
            }
            System.out.println("Inversions= " + inversions
                    + " UnSolvable...Regenerating new config...");
        }

        i = 0;
        for (j = 0; j < 3; j++) {
            for (k = 0; k < 3; k++) {
                puz[j][k] = arr[i++];
            }
        }

        for (j = 0; j < 3; j++) {
            for (k = 0; k < 3; k++) {
                System.out.print(puz[j][k]);
            }
            System.out.println();
        }

        return puz;
    }

    public static int getinversions(int ar[]) {
        int inversionCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (ar[j] != 0 && ar[i] != 0 && ar[i] > ar[j]) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

}
