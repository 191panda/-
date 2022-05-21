import java.util.*;

public class day530 {
/**
 * hasNextInt()方法 输入n个数字 当EOF时停了
 *如果此扫描仪输入中的下一个标记可以使用 nextInt()方法解释为指定基数中的int值，则返回true。
 * @param args [description]
 */ 
	public static void main(String[] args) {

		Scanner s=new Scanner(System.in);
        int nub=1;
		if(s.hasNextInt())
		{     
			//int nub =s.nextInt();
			System.out.println("hhhh"+nub);
		}
	}
	/**
	 * 数学方法sqrt 开根号
	 * Math.sqrt(a)
	 * @param args [description]
	 */
	public static void main6(String[] args) {
		
		int  a= 9 ;
		double b=Math.sqrt(a);
		System.out.println(b);
	}

/**
 * 1-100 9 的个数
 * @param args [description]
 */
	public static void main5(String[] args) {
		
		int i=0;
		int cons=0;
		for(;i<100;i++)
		{
            //个位上的9
			if(i%10==9)
			{
                 cons++;
			}
            //十位上的9
			if(i/10==9)
			{
				cons++;
			}

		}

		System.out.println(cons);
	}
	/**
	 * 求二进制1的个数
	 * @param args [description]
	 */

	public static void main4(String[] args) {
		
		Scanner s=new Scanner(System.in);
        System.out.print("输入一个数字：");

		int n= s.nextInt();
        int cons =0;
        int i=0;
        //法一：
		/*for(;i<32;i++) 
		{ 
			if (((n>>i)&1)==1) 
			{
		    	  cons++;
		    }
                     
		}*/
		// 方法2
		while(n!=0)
		{

               n = n&(n-1);
               cons++;

		}
		System.out.println("1个数是："+cons);
	}
	/**
	 * 输入密码 只能错三次
	 *  equals()方法 equals() equals() equals()
	 *  equals 意思等于
	 * @param args [description]
	 */
	public static void main3(String[] args) {
		int cons=3;
		Scanner s=new Scanner(System.in);
		System.out.print("请输入密码：");
		

		while(cons>=1)
		{
			String pow=s.next();

            if(pow.equals("我爱你"))
            {
            	System.out.println("密码输入正确");
            	break;
            }
            else
            {  
            	System.out.println("密码输入错误");
                cons--;
                if(cons==0)
                {
                	break;
                }
                 System.out.println("你还有"+cons+"次机会");
            }
			
		}
		
	}
/**
 * 猜数字游戏 关键在随机数方法 
 * ***import java.util.*;
 * Random ran= new Random();
 * int nub=ran.nextInt(100); //[1-100)
 * int nub=ran.nextInt(100)+1; //[1-100]
 * @param args [description]
 */
	public static void main2(String[] args) {
		
		Random ran= new Random();
		Scanner s=new Scanner(System.in);

		int nub=ran.nextInt(100)+1; //[1-100)

		while(true)
		{      

		      System.out.println("外挂操作：你要猜的数字是"+nub);
              System.out.print("输入你要猜的数字：");
			
              int n=s.nextInt();
              
			if(n<nub)
			{
				System.out.println("猜小了");
			}
			else if (n>nub)
			{
				System.out.println("猜大了");
			}
			else
			{
				System.out.println("猜对了");
				break ;
			}

		}
	}

/**
 * 乘法表打印
 */
     public static void main1(String[] args) {
     	
     	int i=1;
     	for(i=1;i<=9;i++) 
     	{    int j=0;
             for(j=1;j<=i;j++)

             {     
                  //System.out.print(j+"*"+i+"="+(i*j)+" "); 
                  //用c语言中printf 要加 System.out.printf
                  System.out.printf("%d*%d=%2d ",j,i,i*j);

             }
               System.out.println();

     	}
     }

}