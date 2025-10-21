import java.util.Arrays;
import java.util.Scanner;

public class Prem {
    public static void main(String[] args) {
        Scanner q=new Scanner(System.in);
        int n = q.nextInt();
        int arr[]=new int[n];
        for(int i=0;i<n;i++)
        {
            arr[i]=q.nextInt();
        }
        int i=0;
        int j=n-1;
        while(i<j)
        {
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            i++;
            j--;
        }
        System.out.println(Arrays.toString(arr));
    }
}
