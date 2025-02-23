public class Recursion3
{
    public static String insertStarV1(String in)
    {
        if( in.length() == 1)
        {
            return in;
        }
        if( in.length() ==0)
        {
            return "";
        }

        return in.substring(0, 1) + "*" + insertStarV1(in.substring(1));
    }

    public static String insertStarV2(String in)
    {
        if(in.length() == 1)
        {
            return in;
        }
        if(in.length() == 0)
        {
            return "";
        }

        String smallHalf = in.substring(0, in.length() / 2);
        String bigHalf = in.substring(in.length() / 2);
        return insertStarV2(smallHalf) + "*" + insertStarV2(bigHalf);
    }

    public static int countX(String in)
    {
        if(in.length() == 0)
        {
            return 0;
        }
        if(in.length() == 1)
        {
            if(in.equals("x"))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }

        String smallHalf = in.substring(0, in.length() / 2);
        String bigHalf = in.substring(in.length() / 2);
        return countX(smallHalf) + countX(bigHalf);
    }

    public static String removeX(String in)
    {
        if(in.length() == 0)
        {
            return "";
        }
        if(in.length() == 1)
        {
            if(in.equals("x"))
            {
                return "";
            }
            else
            {
                return in;
            }
        }

        String smallHalf = in.substring(0, in.length() / 2);
        String bigHalf = in.substring(in.length() / 2);
        return removeX(smallHalf) + removeX(bigHalf);
    }
    public static void main(String[] args) {
        System.out.println(insertStarV2("abcde"));
        System.out.println(countX("bacx"));
        System.out.println(removeX("axdefx"));

        System.out.println(removeDoubles("xxabxx"));
        System.out.println(removeDoubles("abxed"));
        System.out.println(removeDoubles("xxxxxab"));

        int[] arr = {4, 2, 1, 5, 3};
        int minIndex = indexOfMin(arr, 0, arr.length - 1);
        System.out.println("Index of the minimum element: " + minIndex);

        System.out.println(findMax(arr, 0, arr.length - 1));
    }


    public static int findMax(int[] arr) {
        return findMaxRecursive(arr, 0, arr.length - 1);
    }

    private static int findMaxRecursive(int[] arr, int start, int end) {
        // Base case: if the array has only one element
        if (start == end) {
            return arr[start];
        }

        // Recursive case: find the maximum of the first element and the maximum of the rest of the array
        int mid = (start + end) / 2;
        int maxLeft = findMaxRecursive(arr, start, mid);
        int maxRight = findMaxRecursive(arr, mid + 1, end);

        return Math.max(maxLeft, maxRight);
    }

    public static boolean isPalindrome(String in)
    {
        if(in.length() <= 1)
        {
            return true;
        }

        if(in.substring(0, 1).equals(in.substring(in.length() - 1)))
        {
            return isPalindrome(in.substring(1, in.length() - 1));
        }

        return false;
    }

    public static boolean nested(String in)
    {
        if(in.length() == 0)
        {
            return true;
        }

        if(in.substring(0, 1).equals("(") && in.substring(in.length() - 1).equals(")"))
        {
            return nested(in.substring(1, in.length() - 1));
        }

        return false;
    }

    public static String removeDoubles(String in)
    {
        if(in.length() == 0)
        {
            return "";
        }
        if(in.length() == 1)
        {
            return in;
        }

        if(in.substring(0, 1).equals(in.substring(1, 2)))
        {
            return removeDoubles(in.substring(2));
        }
        else
        {
            return in.substring(0, 2) + removeDoubles(in.substring(2));
        }
    }

    public static int indexOfMin(int[] arr, int start, int end)
    {
        if (start == end)
        {
            return start;
        }

        int mid = (start + end) / 2;
        int leftIndex = indexOfMin(arr, start, mid);
        int rightIndex = indexOfMin(arr, mid + 1, end);

        if (arr[leftIndex] < arr[rightIndex])
        {
            return leftIndex;
        }
        else
        {
            return rightIndex;
        }
    }

    public static int findMax(int[] arr, int start, int end)
    {
        if(start == end)
        {
            return arr[start];
        }

        int mid = (start + end) / 2;
        int leftMax = findMax(arr, start, mid);
        int rightMax = findMax(arr, mid + 1, end);

        return Math.max(leftMax, rightMax);
    }
}
