
package TeacherStudentChallenge;

class Whiteboard
{
    String text;
    int std=0;
    int count=0;
    
    public void attendance()
    {
        std++;
    }
    synchronized public void write(String str)
    {
        System.out.println("Teacher is writing " + str);
        while (count!=0)
            try{wait();}catch(Exception e){}
        text=str;
        count=std;
        notifyAll();
    }
    synchronized public String read()
    {
        while(count==0)
            try{wait();}catch(Exception e){}
        
        String t=text;
        count--;
        if (count==0)
            notify();
        return t;
    }
}

class Teacher extends Thread
{
    Whiteboard wb;
    
    String notes[]={"Java is language","It is OOPs","It is Platform Independent","It supports Thread","end"};
    
    public Teacher(Whiteboard w)
    {
        wb=w;
    }
    public void run()
    {
        for(int i=0;i<notes.length;i++)
        {
            wb.write(notes[i]);
        }
        
        wb.write("end");
    }
}

class Student extends Thread
{
    String name;
    Whiteboard wb;
    
    public Student(String s,Whiteboard w)
    {
        name=s;
        wb=w;
    }
    public void run()
    {
        String text;
        wb.attendance();
        
        do
        {
            text=wb.read();
            System.out.println(name + " Reading " + text);
            System.out.flush();
        
        } while (text.equals("end"));
    }
}


public class Challenge {
    public static void main(String[] args) {
        Whiteboard wb=new Whiteboard();
        Teacher t=new Teacher(wb);
        
        Student s1=new Student("Harsh",wb);
        Student s2=new Student("Tanvi",wb);
        Student s3=new Student("Anta",wb);
        Student s4=new Student("AK",wb);
        Student s5=new Student("Rana",wb);

        t.start();
        
        s1.start();
        s2.start();
        s3.start();
        s4.start();
        s5.start();

        
    }
}
