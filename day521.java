import java.util.Scanner;

public class day521 {



		public static void main(String[] args) {
        
        Scanner str1 =new Scanner(System.in);

		System.out.print("输入星期几:");

		String sss =str1.next();

		switch(sss)
		{

           case "星期一":
               System.out.println("1");
               break;
           case "星期二":
                System.out.println("2");
                break;
           case "星期三":
               System.out.println("3");
               break;
           case "星期四":
                System.out.println("4");
                break;
           case "星期五":
               System.out.println("5");
               break;
           case "星期六":
                System.out.println("6");
                break;
           case "星期天":
                System.out.println("7");
           default :
               System.out.println("输入错误"); 
               break;
		}


	}
	
 /**
 *javac day52.java -encoding utf-8
 *switch不适用的类型
 *long flaot double boolean 
 */
		public static void main1(String[] args) {

	    Scanner s=new Scanner(System.in);	

		System.out.print("输入你的姓名：");

		String name =s.next();

		System.out.println(name);
        
		System.out.print("输入你的年龄：");

		int age=s.nextInt();

		System.out.println(age);
        
		System.out.print("输入你的性别：");

		String sex =s.next();

		System.out.println(sex);
        
		System.out.print("输入你的电话：");

		int nub=s.nextInt();

		System.out.println(nub);

	}

}
