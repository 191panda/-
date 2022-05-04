public class day540  { 

/**
 * 青蛙跳台阶 一次跳1阶或者2阶 n个台阶多少种跳法
 * @param  n [description]
 * @return   [description]
 */
    public static int qingwa(int n) {
       
       if(n==1) {
       	return 1;
       }

       else if(n==2) {
       	return 2;
       }

       else {
             return qingwa(n-1)+qingwa(n-2);
       }

    }

    public static void main(String[] args) {
	
	//n台阶
	int ret =qingwa(4); //3+2  
	int ret1 =qingwa(3); //2+1  
	System.out.println(ret);
	System.out.println(ret1);
}
    
    //1729 9 +2+ 7+ 1
    public static int fun2(int x) {

         if(x>9)
         {
         	return x%10+fun2(x/10);//9+2+7+1

         }

         return x;//1




    }
	public static void main5(String[] args) {
	
	int ret =fun2(1729);
	System.out.println(ret);	

	}
/**
 * 斐波那契数 1 1 2 3 5 8 13
 * @param  n [description]
 * @return   [description]
 */
    public static int febona(int n) {
       

       if(n==1|n==2)
       {

       	return 1;
       }
       else 
       {
       	  return febona(n-1)+febona(n-2); 
       }

     }

	public static void main4(String[] args) {
		
         int ret =febona(100);
         System.out.println(ret);

	}
     
     //1234   递归的话先4 3 2 1
     public static int fun(int x) {
           
          if(x>9)
          {
          	fun(x/10);
          }  
          
          System.out.print((x%10)+" ");//4 3 2 1
            
            return 0;
          
     }


     public static void main3(String[] args) {
	
           fun(1234);
           fun(5201314);

}
	/**
	 * 方法的重载  
	 * 1.变量名相同
	 * 2.参数列表必须不同 
	 *
	 */
    
    public static int add(int x,int y) {

    	return x+y;
    }
    public static float add(float x,float y) {

    	return x+y;
    }
	public static void main2(String[] args) {
  
         int ret =add(20,32);
         float flo=add(2.5f,2.7f);
         System.out.println(ret);
         System.out.println(flo);

	}
/**
 * 打印二进制位上的奇偶数位
 * @param args [description]
 */
	public static void main1(String [] args) {

		 int n=5;// 0000 0000 0000 0000 0000 0000 0000 0101
		 //移动31 得到32位上的数
		 //打印二进制上偶数数位的 
         for(int i=31;i>=1;i -=2) 
         {  
         	System.out.print(((n>>i)&1)+" ");
         }
         //打印二进制上奇数数位的 
         System.out.println();
         for(int i=30;i>=0;i -=2) 
         {
         	System.out.print(((n>>i)&1)+" ");
         }
	}
}