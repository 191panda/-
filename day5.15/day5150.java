public class day5150 {

     public static void main(String[] args) {
     	
        // Studen studen =new Studen();
      
          Studen.print();//类的方法 Studen.print()  不用重新定义

          m();
     }
     public static void m() {

        int i=0;
        for(;i<10;i++) {
                      
           if(i==5) {
               System.out.println(i+">");
               //break;
              return ;//与break 区分 break 是跳出for循环 而 return 是终止m（）方法
            }
          System.out.println(i+">");
         }
      System.out.println("你好");//break 可以访问"你好" 
    }

     

      
}
/**
 * 若定义的方法的外面 要有一个类的保存 不然错误 
 * 引用这个方法 要new 一下 也可以不用 直接引用
 */
class Studen {
    
     public static void print() {

	   System.out.println("nh");

     }
}
