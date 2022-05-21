import java.util.*;
import java.lang.String ;

public class day5170 {
   

   public static void main(String[] args) {
   	
   	int arr[]={1,2,3,4};
   	System.out.println(arr.length);
   }
/*

罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
I             1
V             5
X             10
L             50
C             100
D             500
M             1000


C语言的参考代码
int Solo(char s) {
	  
		switch (s)
		{
		case 'I':
			return 1;
		case 'V':
			return 5;
		case 'X':
			return 10;
		case 'L':
			return 50;
		case 'C':
			return 100;
		case 'D':
			return 500;
		case 'M':
			return 1000;
		default:
			return -1;
		}
	
}

int main()
{
	char arr[10] ="";
	int sum = 0;
	while (1)
	{
		int sum = 0;
		scanf("%s", &arr);

		for (int i = 0; i < strlen(arr); i++)
		{
			int N = Solo(arr[i]);
			sum += N;
		}

		for (int j = 0; j < strlen(arr) - 1; j++)
		{
			if (Solo(arr[j]) < Solo(arr[j + 1]))
			{
				sum = sum - 2 * Solo(arr[j]);
			}

		}
		printf("%d\n", sum);
		
	}
	return 0;
}
 */
	public static void main1(String[] args) {
		   //因为main方法的静态的 而Scanner下的方法不是 
		   //无法从静态上下文中引用非静态方法
		   //eg：public byte nextByte()
		  // Scanner str=new Scanner(System.in);
           String sb=new String("abcc");
           String arr="abc";
           
           //
           if (sb.equals(arr))
           {
           	System.out.println("密码正确"+arr);
           }
           else {

           	System.out.println("密码错误"+arr);
           }
           System.out.println(sb.equals(arr));
           //System.out.println(s);
           //System.out.println(a);

           //Byte a=Scanner.nextByte();
          // System.out.println(a);
	}
}