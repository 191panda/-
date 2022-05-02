import java.util.Scanner;
public class day522 {

	public static void main(String[] args) {
		
         int a=0;
         int conut1=0;
         int conut2=0;
         for(int i=3;i<=100;i++)
         {   
            for(int j=2;j<=i/2;j++)
            {
                 if(i%j==0)
                 {

                 	System.out.print(i+"不是素数  ");
                 	conut1++;
                 	break;
                 }
                 else
                 {

                 	System.out.print(i+"是素数  ");
                 	conut2++;
                 	break;
                    
                 }

            }
             

         }
            System.out.println(conut1+"个不是素数");
            System.out.println(conut2+"个素数");

	}

	/**
	 * 判断一个数是不是素数
	 * @param args [description]
	 */

	public static void main1(String[] args) {
		
		Scanner s=new Scanner(System.in);
        
        System.out.print("输入一个数：");

		int n= s.nextInt();
      
		for(int i=2;i<(n/2);i++)
		{
               if(n%i==0) 
               {   
                   
                    System.out.println(n+"不是素数");
                    break;
               }
               else
               {
                    System.out.println(n+"是素数");
                    break;

               }
          
		}



                          

	}
}