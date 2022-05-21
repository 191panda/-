import static java.lang.Math.*;


public class day5180 {

	public static void main(String[] args) {
		
       // double a =4;
       //double b =sqrt(a);
       // System.out.println(b);  
       //System.out.println(pow(10,2));

        Studen s=new Studen();
        System.out.println("age:"+s.age);
        s.age=18;
        System.out.println("age:"+s.age);
        System.out.println("name:"+(s.name="风少牛逼"));
        s.sex='男';	//char无法转换为boolean
        System.out.println(s.sex);
         

        School school=new School();

        System.out.println(school.schoolName);

        school.schoolName="桂林信息";

        System.out.println(school.schoolName);

        System.out.println(school.schoolAge);

        school.schoolAge=100;

        System.out.println(school.schoolAge);
        
        System.out.println(school.one);

        school.one =new Studen();

      //  System.out.println(school.one);//Studen@15db9742

        System.out.println(school.one.age);

        school.one.age=10;

        System.out.println(school.one.age);
        
	}
}

