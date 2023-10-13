import java.util.Scanner;

public class 方法1 {

	public static void main(String[] args) {
        
        Scanner s=new Scanner(System.in);

        //String b=s.next();
        //int a=s.nextInt(); 
      /**
       * 方法调用 方法一 类名.方法
       *         方法二 直接引用 默认从该类下面找 如果没有则报错 
       */
        day7140.m();
        //System.out.println(day7141.m(a));
        //System.out.println(b);
        //
    
       
        System.out.println(Integer.MAX_VALUE);//2147483647
        System.out.println(Byte.MAX_VALUE);//127
        System.out.println(Short.MAX_VALUE);//32767
        System.out.println(Long.MAX_VALUE);//9223372036854775807
        System.out.println(Float.MAX_VALUE);//3.4028235E38
        System.out.println(Double.MAX_VALUE);//1.7976931348623157E308

        System.out.println(Character.MAX_VALUE);//?
        //System.out.println(Boolean.MAX_VALUE);
	}
        
	
}
class day7140 {

	public static void m() {
        
		System.out.println("hello world!");
	}
}

class day7141 {

	public static int m(int a) {
        
		 if(a>1)
		 	return a*m(a-1);
         else
         	return 1;
	}
}