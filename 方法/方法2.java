import java.util.Scanner;
 /**
  * 练习输入
  */
public class 方法2 {

	public static void main(String[] args) {
		Scanner s=new Scanner(System.in);
		int a=s.nextInt();
		byte b=s.nextByte();
		short c=s.nextShort();
		float d=s.nextFloat();
		double e=s.nextDouble();
		boolean f=s.nextBoolean();//true or false
        /*
         InputMismatchException 输入不匹配异常
         */

		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
		System.out.println(d);
		System.out.println(e);
		System.out.println(f);
	}
}