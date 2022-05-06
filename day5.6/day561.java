import java.util.Arrays;

public class day561 {
      public static void main(String[] args) {
	
      int [][] arr=new int [2][];
      arr[0] = new int []{1,2,3};
      arr[1] = new int []{1,2};

     System.out.println(Arrays.deepToString(arr));

}
/*	public static void main3(String[] args) {
		int [][] arr ={{1,2,3},{2,3,4}};
		//int [][] arr1 =new int [3][];//java 必须有行 
		//int [][] arr =new int [][3];//error 错
        int [][] arr2= new int[3][];
        arr2[0]=new int []{1,2,3};
        arr2[1]=new int []{1,2,3};
        arr2[2]=new int []{1,2,3};
        //arr2[2]=[]{1,2,3};error 错误

        System.out.println(Arrays.deepToString(arr2));
        main2(arr2);
	}*/
	/**
	 * 二维数组 打印
	 * @param args [description]
	 */
	/*public static void main2(String[] args) {

		int [][] arr ={{1,2,3},{2,3,4}};
		for (int i=0;i<arr.length ;i++ ) {

			for (int j=0;j<arr[0].length;j++) {

				System.out.print(arr[i][j]+" ");
			}
			System.out.println();
		}
	}*/
	
	/*public static void main1(String[] args) {
		
		int [][] arr ={{1,2,3},{2,3,4}};
		for (int tem[] : arr ) {
		  for (int x : tem )
		  {

		  	System.out.print(x+" ");

		  }
			System.out.println();
		}
	}*/
}