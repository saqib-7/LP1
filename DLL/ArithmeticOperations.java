package DLL;

public class ArithmeticOperations {
    public native int add(int a, int b);

    public native int subtract(int a, int b);

    public native int multiply(int a, int b);

    public native int divide(int a, int b);

    static {
        System.loadLibrary("ArithmeticOperations");
    }

    public static void main(String[] args) {
        ArithmeticOperations ops = new ArithmeticOperations();
        int a = 10, b = 5;
        System.out.println("Addition: " + ops.add(a, b));
        System.out.println("Subtraction: " + ops.subtract(a, b));
        System.out.println("Multiplication: " + ops.multiply(a, b));
        System.out.println("Division: " + ops.divide(a, b));
    }
}

/*
javac ArithmeticOperations.java
javac -h . ArithmeticOperations.java
gcc -I /usr/lib/jvm/java-17-openjdk-amd64/include -I/usr/lib/jvm/java-17-openjdk-amd64/include/linux -fPIC -shared -o libArithmeticOperations.so ArithmeticOperations.c
java -Djava.library.path=. ArithmeticOperations
 */