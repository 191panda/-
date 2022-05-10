/**
 * 创建一个类 Studen
 */
class Studen {
     String name= "小顺";
     public void show(){

     	System.out.println("你的名字："+name);
     }

}
public class day571 {
      /**
       * 访问修饰限定符
       *  public 公有的
       *  private 私有的
       *  protected 受保护的
       * @param args [description]
       */
	public static void main(String[] args) {
		//实例化一个对象 通过关键字new
       Studen studen =new Studen();
       studen.show();
	}
}