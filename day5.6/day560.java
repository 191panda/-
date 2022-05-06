import java.util.Arrays;

public class day560 {
    /**
     * 二分查找
     * @param  arr [description]
     * @param  a   [description]
     * @return     [description]
     */
    public static int mySearch(int [] arr,int a) {

    	int letf=0;
    	int right=arr.length-1;
    	int ret=0;
      
      while (letf<=right)
      {
    		     	
    	ret=(right+letf)/2;
    	if(arr[ret]>a)
    	{
    		right=ret-1;
    	}
    	else if(arr[ret]<a)
    	{
    	      letf=ret+1;	
    	}
    	else if(arr[ret]==a)
    	{
    		return ret;
    	}

      }

      return -1;
  
    }
	public static void main(String[] args) {
		
		int [] arr = {1,4,3,7,5,2,9,8};
		int [] arr1= new int [9];
		int a=9;
		//数组排序
		//Arrays.sort(arr,0,8);
		Arrays.sort(arr);
		//数组拷贝
		arr1=Arrays.copyOf(arr,10);
		//自己实现数组查找
		int ret2=mySearch(arr,a);

		int ret = Arrays.binarySearch(arr,9);//返回下标
		int ret1 = Arrays.binarySearch(arr,0,1,1);//返回下标
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(arr1));
		//System.out.println(ret);
		//System.out.println(ret1);
		System.out.println(ret2);

	}
}