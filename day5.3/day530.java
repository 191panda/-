import java.util.*;

public class day530 {
/**
 * hasNextInt()���� ����n������ ��EOFʱͣ��
 *�����ɨ���������е���һ����ǿ���ʹ�� nextInt()��������Ϊָ�������е�intֵ���򷵻�true��
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
	 * ��ѧ����sqrt ������
	 * Math.sqrt(a)
	 * @param args [description]
	 */
	public static void main6(String[] args) {
		
		int  a= 9 ;
		double b=Math.sqrt(a);
		System.out.println(b);
	}

/**
 * 1-100 9 �ĸ���
 * @param args [description]
 */
	public static void main5(String[] args) {
		
		int i=0;
		int cons=0;
		for(;i<100;i++)
		{
            //��λ�ϵ�9
			if(i%10==9)
			{
                 cons++;
			}
            //ʮλ�ϵ�9
			if(i/10==9)
			{
				cons++;
			}

		}

		System.out.println(cons);
	}
	/**
	 * �������1�ĸ���
	 * @param args [description]
	 */

	public static void main4(String[] args) {
		
		Scanner s=new Scanner(System.in);
        System.out.print("����һ�����֣�");

		int n= s.nextInt();
        int cons =0;
        int i=0;
        //��һ��
		/*for(;i<32;i++) 
		{ 
			if (((n>>i)&1)==1) 
			{
		    	  cons++;
		    }
                     
		}*/
		// ����2
		while(n!=0)
		{

               n = n&(n-1);
               cons++;

		}
		System.out.println("1�����ǣ�"+cons);
	}
	/**
	 * �������� ֻ�ܴ�����
	 *  equals()���� equals() equals() equals()
	 *  equals ��˼����
	 * @param args [description]
	 */
	public static void main3(String[] args) {
		int cons=3;
		Scanner s=new Scanner(System.in);
		System.out.print("���������룺");
		

		while(cons>=1)
		{
			String pow=s.next();

            if(pow.equals("�Ұ���"))
            {
            	System.out.println("����������ȷ");
            	break;
            }
            else
            {  
            	System.out.println("�����������");
                cons--;
                if(cons==0)
                {
                	break;
                }
                 System.out.println("�㻹��"+cons+"�λ���");
            }
			
		}
		
	}
/**
 * ��������Ϸ �ؼ������������ 
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

		      System.out.println("��Ҳ�������Ҫ�µ�������"+nub);
              System.out.print("������Ҫ�µ����֣�");
			
              int n=s.nextInt();
              
			if(n<nub)
			{
				System.out.println("��С��");
			}
			else if (n>nub)
			{
				System.out.println("�´���");
			}
			else
			{
				System.out.println("�¶���");
				break ;
			}

		}
	}

/**
 * �˷����ӡ
 */
     public static void main1(String[] args) {
     	
     	int i=1;
     	for(i=1;i<=9;i++) 
     	{    int j=0;
             for(j=1;j<=i;j++)

             {     
                  //System.out.print(j+"*"+i+"="+(i*j)+" "); 
                  //��c������printf Ҫ�� System.out.printf
                  System.out.printf("%d*%d=%2d ",j,i,i*j);

             }
               System.out.println();

     	}
     }

}