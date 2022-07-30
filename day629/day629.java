import java.util.s;
public class day629 {
     //这里是 成员变量
	public static void main(String[] args) {
	
		//局部变量
		byte a =(byte)128;//byte范围 -128~127
		/**
		 * short 2个字节 2的16
		 * int   4个字节 2的32
		 * long  8个字节 2的64
		 * char 是无符号short类型的范围
		 */
		//128字面值是int 类型 128L 是long类型
		//8进制 0开头
		//16进制 0x开头
		//注意字面值不能超过范围
		//long b=2222222222;//整数太大  编译失败
		//自动类型转换 小类型向大类型
		
		System.out.println(a);
		Scanner s=new Scanner(System.in);
		int b=s.nextInt();
		System.out.println(b);
	}
}