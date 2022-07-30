public class Day717 {
     
  
	public static void main(String[] args) {
		/*
		//不兼容的类型: Studen无法转换为String
		String s=new Studen();//地址
		
         */
        Studen s=new Studen();//地址
    
        //System.out.println(Studen.inta);//无法从静态上下文中引用非静态 变量 inta
        System.out.println(new Studen().inta);
        System.out.println(new Studen().chara);
        System.out.println(new Studen().doublea);
        System.out.println(s.inta);
	}
}