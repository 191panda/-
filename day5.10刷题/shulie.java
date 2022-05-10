import java.lang.Math;
import java.lang.Long;
import java.lang.Number;

public class shulie {
	/**
	 * 有数列为：9，99，999，...，9999999999。
	 * Long.parseLong(a)
	 * 要求使用程序计算此数列的和，并在控制台输出结果。
	 */

      public static void main(String[] args) {
	   
        String a = "9";

        long sum = 0;
        for(int i = 0;i<10;i++)
        {

            sum = Long.parseLong(a) + sum;
            a = a + "9";

        }

        System.out.println(sum);
 
    }
}

// 	public static void main1(String[] args) {
		
// 		Scanner s=new Scanner(System.in);
// 		int n=s.nextInt();
// 		long x=9;
// 		long sum1=0;
// 		long sum2=0;
// 		for(int i=0;i<n;i++)
// 		{

// 			sum1=sum1+x*((long)Math.pow(10,i));
//             sum2=sum2+sum1;
// 		}


//           System.out.println(sum2);
// 	}
// }