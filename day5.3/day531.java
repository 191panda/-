public class day531 {


static int cons =0;
/**
 * 方法不需要管在哪
 * 想用调出来就行
 * @param  x [description]
 * @param  y [description]
 * @return   [description]
 */
    //加法
    public static int add(int x,int y) {
       
       return x+y;
    
     
    }
    //比较两个数大小
    public static int max(int x ,int y) {

    	return x>y?x:y;
    }
    
    //求阶乘 
    public static int far(int n) {
       
       int ret=1;
       for(int i=1;i<=n;i++)
       {
     
            ret=ret*i;//1*1*2*3*4*5
       }
       return ret;
    }
    public static void move(char a,char b)
    {   cons++;
        System.out.println("第"+cons+"步"+":把"+a+"放到->>"+b);
        
    }
    //汉诺塔问题 
    public static void hanno(int n,char a,char b,char c)
    {
          if(n==1)
          {
          	 move(a,c);
          } 
          else 
          {
                //先把a借助c放到b上 
                hanno(n-1,a,c,b);//3
                //把a移动到c上
                move(a,c);
                //最后把b移动到c上
                hanno(n-1,b,a,c);
          }
    

    }
	public static void main(String[] args) {
            
            int ret = add(10 , 20);
            System.out.println(ret); 
            int max=max(20,30);
            System.out.println(max); 
            int far=far(5);
            System.out.println(far); 
            
            hanno(2,'1','2','3');
	}
}