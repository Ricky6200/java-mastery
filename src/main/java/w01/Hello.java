package w01;

public class Hello {
    static int add(int a, int b){
        return a+b;
    }
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        int a=1,b=2;
        int total=0;
        total=add(a,b);
        System.out.println("Total: "+total);
        /*
        getstatic - System.out basically means get the static field out of the class System called out. out is a static field of type PrintStream. So, getstatic gets the value of that field and pushes it onto the stack.
        ldc - pushes a constant onto the stack. In this case, it pushes the string "
Hello, World!" onto the stack.
        invokevirtual - invokes a method on an object. In this case, it invokes the println method on the PrintStream object that was pushed onto the stack by getstatic. The string that was pushed onto the stack by ldc is used as the argument to println.
        return - returns from the method
         */

        /*
        Jshell -
        jshell> 10 / 3
        $1 ==> 3
        jshell> 'a' + 1
        $2 ==> 98
        jshell> Integer.MAX_VALUE+1
        $3 ==> -2147483648

        jshell> 10 / 3;
        ...> 'a' + 1;
        ...> Integer.MAX_VALUE + 1;
        ...> 0.1 + 0.2;
        ...> 1 + 2 + "3";
        $6 ==> 3
        $7 ==> 98
        $8 ==> -2147483648
        $9 ==> 0.30000000000000004
        $10 ==> "33"
         */
    }
}
