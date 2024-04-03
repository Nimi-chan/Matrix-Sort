import java.util.Arrays;
import java.util.Random;

public class Main {

    private final static Random rand = new Random(System.currentTimeMillis());
    private final static int lower_bound = 10;
    private final static int upper_bound = 99;
    private final static int matrix_size = 10;

    public static void main(String[] args) {
        int[][] mat = randomizeMatrix();
        System.out.println("Original randomized matrix:");
        printMatrix(mat);
        System.out.println("Matrix bubble sort");
        bubbleSort(mat);
        printMatrix(mat);
    }

    public static int[][] randomizeMatrix() {
        int[][] randMatrix = new int[matrix_size][matrix_size];
        for (int i = 0; i < matrix_size; i++) {
            for (int j = 0; j < matrix_size; j++) {
                randMatrix[i][j] = rand.nextInt(upper_bound - lower_bound) + lower_bound;
            }
        }
        return randMatrix;
    }

    public static void printMatrix(int[][] mat) {
        for (int[] ints : mat) {
            System.out.println(Arrays.toString(ints));
        }
    }

    public static void bubbleSort(int[][] mat) {
        if (mat.length == 0) {
            return;
        }
        boolean sorted = false;
        while(!sorted) {
            sorted = true;
            int prev = mat[0][0];
            for (int i = 0; i < matrix_size; i++) {
                for (int j = 0; j < matrix_size; j++) {
                    if (mat[i][j] < prev) {
                        sorted = false;
                        // if j is first element in the row, need to modify last element in previous row
                        int x = (j-1 > -1) ? i : i-1;
                        int y = (j-1 > -1) ? j-1 : matrix_size - 1;
                        mat[x][y] = mat[i][j];
                        mat[i][j] = prev;
                    }
                    prev = mat[i][j];
                }
            }
        }
    }

    // not space efficient, but should be on the faster side
    public static int[][] binSort(int[][] mat) {
        int[][] intCount = new int[9][10]; // a matrix storing count of every integer from 10 to 99 inclusive
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int n = mat[i][j];
                intCount[n / 10 - 1][n % 10]++; // 10s are rows, singles are columns
            }
        }
        int[][] sortedMat = new int[10][10];
        int c = 0; // sorted matrix counter
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                while (intCount[i][j] > 0) {
                    sortedMat[c / 10][c % 10] = (i + 1) * 10 + j;
                    c++;
                    intCount[i][j]--;
                }
            }
        }
        return sortedMat;
    }

    // doesn't quite work, still needs debugging
    public static void quickSort(int[][] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[][] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(int[][] arr, int begin, int end) {
        int pivot = arr[end / 10][end % 10];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j / 10][j % 10] <= pivot) {
                i++;

                int swapTemp = arr[i / 10][i % 10];
                arr[i / 10][i % 10] = arr[j / 10][j % 10];
                arr[j / 10][j % 10] = swapTemp;
            }
        }

        int swapTemp = arr[(i+1) / 10][(i+1) % 10];
        arr[(i+1) / 10][(i+1) % 10] = arr[end / 10][end % 10];
        arr[end / 10][end % 10] = swapTemp;
        return i+1;
    }

}
