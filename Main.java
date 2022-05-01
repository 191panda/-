import java.util.Scanner;

public class Main{ 
	public static void main(String[] args) {
		
	}
	/**
	 * 
     * 闰年的计算
     */
	public static void main5(String[] args) {
		Scanner s =new Scanner(System.in);
		while(s.hasNextInt()){//ctr+c 直接中断程序 ctr+d 正常退出

			int year=s.nextInt();
			if(year%400==0|| (year%4==0 && year %100!=0))
			{
				System.out.println(year+"是闰年");
			}
			else
			{

				System.out.println(year+"不是闰年");
			}
			
		}
		
	}
 /**
  * 判断三个数的大小
  * @param args [description]
  */
 public static void main4(String[] args) {
 	int a=10;
 	int b=5;
 	int c=20;
 	if(b>a)
 	{
       a=a^b;
	   b=a^b;
	   a=a^b;
 	}
 	if(c>b)
 	{
         
         b=c^b;
		 c=c^b;
		 b=c^b;
 	}
 	if(b>a)
 	{

       a=a^b;
	   b=a^b;
	   a=a^b;

 	}
 	System.out.println("最大值是："+a);
	System.out.println("最小值是："+c);
 }
	/**
	 * 交换两个数
	 * @param args [description]
	 */
	public static void main3(String[] args) {
		

		int a=10;
		int b=20;
		 a=a^b;
		 b=a^b;
		 a=a^b;
		System.out.println(a);
		System.out.println(b);
	}
    /**
     * 数据类型 1.八大基本数据类型
     *         2.引用数据类型：类 String 数组 抽象类 接口 枚举
     * @param args [description]
     */
    public static void main2(String[] args) {
    	
      byte a=12;
      System.out.println(a);
      byte b=12+1;
      System.out.println(b);
      //byte c=a+b;//从int转换到byte可能会有损失
      //System.out.println(c);
      //char c=Character.MAX_VALUE;
      //System.out.printf("%d",c);
      int c =Integer.MAX_VALUE;
      System.out.println(c);
      long d =Long.MAX_VALUE;
      System.out.println(d);
      double f=Integer.MAX_VALUE+1;
      System.out.println(f);
      //Integer.MAX_VALUE 
      //Integer.MAX_VALUE
      final int p=10;
      //p=13;//无法为最终变量p分配值
      

    }

    /**
     *String[] 数组 arr 数组名随便取
     * arr.length数组长度
     * @param arr [description]
     */
	public static void main1(String[] arr) {

           
         for(int i=0;i<arr.length;i++)
         {

         	System.out.println(arr[i]);
         }
        System.out.println("hehe");

   
    }
          
       

}
