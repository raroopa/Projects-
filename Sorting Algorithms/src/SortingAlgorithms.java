import java.util.ArrayList;
import java.util.Arrays;

public class SortingAlgorithms
{
    public static void main(String[] args)
    {
        // Some test arrays
        int[] test1 = {5, 2, 5, 5, 3, 3, 3, 2, 2, 2, 6, 3, 3, 5};
        int[] test2 = {7, 3, 4, 2, 6, 9, 1, 2};
        int[] test3 = {9, 8, 7, 6, 5, 4, 3, 2};
        int[] test4 = {3};
        int[] test5 = {};

        ArrayList<int[]> tests = new ArrayList<>();
        tests.add(test1);
        tests.add(test2);
        tests.add(test3);
        tests.add(test4);
        tests.add(test5);

        for( int i = 0; i < tests.size(); i++)
        {
            int[] test = tests.get(i);
            System.out.println("===== Test #" + i + "=========");
            System.out.println("Before: " + Arrays.toString(test));
            doubleSelectionSort(test);
            System.out.println("After: " + Arrays.toString(test));
            System.out.println("================================");
        }
    }

    public static void bubbleSort(int[] arr)
    {
        for(int i = 0; i < arr.length - 1; i++)
        {
            for(int j = 0; j < arr.length - 1 - i; j++)
            {
                if(arr[j] > arr[j + 1])
                {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void selectionSort(int[] arr)
    {
        for(int i = 0; i < arr.length - 1; i++)
        {
            int min = i;

            for(int j = i + 1; j < arr.length; j++)
            {
                if(arr[min] > arr[j])
                {
                    min = j;
                }
            }

            if(min != i)
            {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }

    public static void insertionSort(int[] arr)
    {
        for(int startLoc = 1; startLoc < arr.length; startLoc++)
        {
            int valToInsert = arr[startLoc];
            int whereToInsert = startLoc - 1;

            while((whereToInsert >= 0) && (valToInsert < arr[whereToInsert]))
            {
                arr[whereToInsert + 1] = arr[whereToInsert];
                whereToInsert--;
            }

            arr[whereToInsert + 1] = valToInsert;
        }
    }

    public static void selectionSort2(String[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            int min = i;

            for(int j = i + 1; j < arr.length; j++)
            {
                if(arr[min].length() > arr[j].length())
                {
                    min = j;
                }
            }

            if(min != i)
            {
                String temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }
        }
    }

    public static void doubleSelectionSort(int[] arr)
    {
        for(int i = 0; i < arr.length / 2; i++)
        {
            int min = i;
            int max = i;

            for(int j = i + 1; j < arr.length - i; j++)
            {
                if(arr[j] < arr[min])
                {
                    min = j;
                }
                else if(arr[j] > arr[max])
                {
                    max = j;
                }
            }

            if(min != i)
            {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }

            if(max == i)
            {
                max = min;
            }

            if(max != arr.length - i - 1)
            {
                int temp = arr[arr.length - i - 1];
                arr[arr.length - i - 1] = arr[max];
                arr[max] = temp;
            }
        }
    }

    public static void quickSort(int[] array, int low, int high)
    {
        if (low < high)
        {
            int pivot = partition(array, low, high);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high)
    {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++)
        {
            if (array[j] <= pivot)
            {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    public static void swap(int[] array, int i, int j)
    {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
