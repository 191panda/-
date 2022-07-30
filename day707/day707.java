

public class day707{
  
  public static void main(String[] args) {
  	
        int i=0;
        boolean lean =true;
     
       for(i=1;i<100;i++)
       {
           int j=0;
           for(j=2;j<i;j++)
           {

            	if(i%j==0) 
            	{

                  lean=false;
           		  break;

           	     }
           }
       }
          if(lean)
          System.out.print("素数"+i+" ");
          System.out.print(i+" ");
  }
}
	/*public static void main1(String[] arge) {

       int i=0;
       for(i=1;i<100;i++)
       {
           int j=0;
           for(j=2;j<Math.sqrt(i);j++)
           {

            	if(i%j==0)
           		break;
           }
          if(j>=Math.sqrt(i))
          System.out.print(i+" ");
       }
	}*/
