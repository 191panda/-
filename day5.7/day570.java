import java.util.Arrays;

public class day570 {
	/**
	 * 规则和不规则的二维数组
	 * @param args [description]
	 */

	public static void main(String[] args) {
		
		int [][] arr=new int [][] {{1,2,3},{1,2,3,4}};//[][]不能写 要写就不能写后面
		int [][] arr1=new int [3][];//列可以不写
		//arr1[0] 里面放的是 地址 放的是列的地址
		arr1[0]=new int [] {1,2,3,4,5,6};
		arr1[1]=new int [] {1,2,3,4,5,6};
		arr1[2]=new int [] {1,2,3,4,5,6};
        String str =Arrays.deepToString(arr);
		System.out.println(str);
		System.out.println(Arrays.deepToString(arr1));

	}
}
