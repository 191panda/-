package com.java.day5211;

public class day5211 {
 /*
  * 封装 private 私人的 利用公共的方法访问
  * 注意 类和类之前 的访问 对象和对象之间
  *     引用接受地址 new 的对象是地址 
  *      ”引用“也是地址
  *     qizi.tangxiaoshun=zhangfu;
		zhangfu.yangyiping=qizi;
  */

	
	public static void main(String[] args) { 
		
		//实例化对象 qizi局部变量也是引用 new 后面的对象在堆中存储
		QiZi qizi =new QiZi();//地址 
		ZhangFu zhangfu=new ZhangFu();//地址
		qizi.tangxiaoshun=zhangfu;//地址
		zhangfu.yangyiping=qizi;//地址
		
		System.out.println("===结婚证===");
		/*
		  妻子
		 */
        //利用方法来修改  
		qizi.setAge(20);
		//利用方法来访问      
        System.out.println("年龄："+qizi.getAge());
        qizi.setName("妻子是杨丽萍");
        System.out.println(qizi.getName());
        
        /*
         丈夫
         */
        zhangfu.setAge(19);
		//利用方法来访问      
        System.out.println("年龄："+zhangfu.getAge());
        zhangfu.setName("丈夫是唐小顺");
        System.out.println(zhangfu.getName());
        
        
        /*
         通过妻子访问丈夫
         */
         System.out.println("==通过妻子访问丈夫==");
         System.out.println("年龄："+qizi.tangxiaoshun.getAge());
         System.out.println(qizi.tangxiaoshun.getName());
         
         /*/
          * 通过丈夫访问妻子
          */
         System.out.println("==通过丈夫访问妻子==");
         System.out.println("年龄："+zhangfu.yangyiping.getAge());
         System.out.println(zhangfu.yangyiping.getName());
         
	}

}
