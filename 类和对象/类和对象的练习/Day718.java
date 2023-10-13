public class Day718 {

	public static void main(String[] args) {
		
       Studen stu=new Studen();
       
       //方法一
       stu.cp =new Conpute();

       /* 
       方法二
       Conpute c=new Conpute();
       cp=c;
        */
       
       stu.cp.pinPai="戴尔";
       stu.cp.xinghao ="i11999";
       stu.cp.yanse ="黑色";
       stu.nub ="2151300519";
       stu.name="maki";

       System.out.println("品牌："+stu.cp.pinPai);
       System.out.println("型号："+stu.cp.xinghao);
       System.out.println("颜色："+stu.cp.yanse);

	}
}

class Conpute {
     
     String pinPai;
     String xinghao;
     String yanse;  
}

class Studen {

       String nub;
       String name;
       Conpute cp;
}