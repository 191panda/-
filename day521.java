import java.util.Scanner;

public class day521 {



		public static void main(String[] args) {
        
        Scanner str1 =new Scanner(System.in);

		System.out.print("�������ڼ�:");

		String sss =str1.next();

		switch(sss)
		{

           case "����һ":
               System.out.println("1");
               break;
           case "���ڶ�":
                System.out.println("2");
                break;
           case "������":
               System.out.println("3");
               break;
           case "������":
                System.out.println("4");
                break;
           case "������":
               System.out.println("5");
               break;
           case "������":
                System.out.println("6");
                break;
           case "������":
                System.out.println("7");
           default :
               System.out.println("�������"); 
               break;
		}


	}
	
 /**
 *javac day52.java -encoding utf-8
 *switch�����õ�����
 *long flaot double boolean 
 */
		public static void main1(String[] args) {

	    Scanner s=new Scanner(System.in);	

		System.out.print("�������������");

		String name =s.next();

		System.out.println(name);
        
		System.out.print("����������䣺");

		int age=s.nextInt();

		System.out.println(age);
        
		System.out.print("��������Ա�");

		String sex =s.next();

		System.out.println(sex);
        
		System.out.print("������ĵ绰��");

		int nub=s.nextInt();

		System.out.println(nub);

	}

}
