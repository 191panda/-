import java.util.Arrays;


public class day541 { 
	/**
	 * Arrays.fill();填充
	 * @param args [description]
	 */

	public static void main(String[] args) {
		
        int []arr =new int [10];
        Arrays.fill(arr,8);//[8, 8, 8, 8, 8, 8, 8, 8, 8, 8]
        Arrays.fill(arr,10);//[10, 10, 10, 10, 10, 10, 10, 10, 10, 10] 
        System.out.println(Arrays.toString(arr));
	}
/**
 * 数组打印的四种方法
 * 1.for
 * 2.Aarryas.copyOf(arr.arr.length)
 * 3.arraycopy(源arr,源下标0,后arr1,后下标0,打印几个4)
 * 4.clone（）方法 ；用点.引用
 * @param args [description]
 */

	public static void main2(String[] args) {
	       
	        int [] arr ={1,2,3,4};
	        int [] arr1 ={1,2,3,4,5};
	        int [] tem = Arrays.copyOf(arr,arr.length);
	        System.out.println(Arrays.toString(tem));
	        //arraycopy(Object src, int srcPos, Object dest, int destPos, int length) 
	        System.arraycopy(arr,1,arr1,2,3);
	        System.out.println(Arrays.toString(arr1));//[1, 2, 2, 3, 4]
            
            int []arr3 =arr1.clone();
            System.out.println(Arrays.toString(arr3));//0{1,2,3,4,5};
	    }

/**
 * 一维数组 
 * @param array [description]
 */
    //打印数组的方法
	public static void printfarray(int [] array) {

		for(int i=0;i<array.length;i++)
		{

			System.out.print(array[i]);
		}
		System.out.println();
		//for-each   数组的遍历     前言 each 每个
		//不能指定打印
		for (int x :array ) {
             
             System.out.print(x);			
		}
	}

	//自己实现 把数组转换成字符串 mytoString
    public static String mytoString(int [] array) {

           String ret = "["; 
           for(int i=0;i<array.length;i++)
           {

            	//法一
/*           
               if (i<array.length-1) {
 	              
           	      ret +=array[i]+", ";
               }
               if(i==array.length-1)
               {
                  ret +=array[i];
               }
*/           
               //法二 更加简便
               ret +=array[i];
               if(i !=array.length-1)
               {
               	 ret +=", ";
               }
           }      

            return ret+"]";
       
    }

	public static void main1(String[] args) {
		
         int [] array = {1,2,3,4};//静态初始化
         int array1[] = {1,2,3,4}; //静态初始化
         int [] array2 = new int [] {1,2,3,4}; //动态初始化
         int [] array3 =new int [4];//表示每个元素都是0
         
         /**
          * [I@15db9742
          *1234
          *1234
          *1234  
          *0000
          */
         System.out.println(array);//[I@15db9742 假地址 经过改编的
         printfarray(array);  //1234
         System.out.println();
         printfarray(array1);
         System.out.println();
         printfarray(array2);
         System.out.println();
         printfarray(array3); //0000

         System.out.println();
         System.out.println("===============================");
         //把数组转换成字符串 
         System.out.println(Arrays.toString(array));
         //自己实现 把数组转换成字符串 mytoString
         System.out.println(mytoString(array));
	}
}
