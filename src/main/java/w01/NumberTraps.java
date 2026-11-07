package w01;

public class NumberTraps {
    public static void main(String[] args) {

        // 1. int overflow
        int max = Integer.MAX_VALUE;
        System.out.println("1: " + (max + 1));
        // PREDICT:1: -2147XXXX something
        // ACTUAL: 1: -2147483648
        // WHY: Bcz Integer.MAX_VALUE is 2147483647, adding 1 causes overflow and wraps around to Integer.MIN_VALUE which is -2147483648

        // 2. (byte) 200
        System.out.println("2: " + (byte) 200);
        // PREDICT:2: -56
        // ACTUAL: 2: -56
        // WHY: Bcz byte is 8 bits, and 200 in binary is 11001000, which is -56 in two's complement representation.

        // 3. 0.1 + 0.2
        System.out.println("3: " + (0.1 + 0.2));
        // PREDICT:3: 0.30000000000000004
        // ACTUAL: 3: 0.30000000000000004
        // WHY: Bcz floating-point numbers cannot represent 0.1 and 0.2 exactly in binary, leading to a small precision error when they are added together.

        // 4. Integer 127 vs 128 with ==
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println("4a: " + (a == b));
        System.out.println("4b: " + (c == d));
        // PREDICT:4a: true
        // PREDICT:4b: false
        // ACTUAL: 4a: true
        // ACTUAL: 4b: false
        // WHY: Bcz Java caches Integer objects for values between -128 and 127, so a and b refer to the same object, while c and d are different objects since 128 is outside the cached range.

        // 5. unboxing a null Integer
        Integer n = null;
        try {
            int x = n;
            System.out.println("5: " + x);
        } catch (NullPointerException e) {
            System.out.println("5: " + e);
        }
        // PREDICT:NPE
        // ACTUAL: 5: java.lang.NullPointerException (try/catch added so snippets 6-10 still run)
        // WHY: Bcz unboxing a null Integer to an int causes a NullPointerException since there is no value to unbox.

        // 6. char + int
        char ch = 'A';
        System.out.println("6: " + (ch + 1));
        // PREDICT: 6: 66
        // ACTUAL: 6: 66
        // WHY: Bcz 'A' has an ASCII value of 65, and char + int is promoted to int, so it prints the number 66, not 'B'.

        // 7. integer division
        System.out.println("7: " + (7 / 2));
        // PREDICT: 7: 3
        // ACTUAL: 7: 3
        // WHY: Bcz integer division truncates the decimal part, so 7 / 2 results in 3.

        // 8. Math.round(-2.5)
        System.out.println("8: " + Math.round(-2.5));
        // PREDICT:8: -2
        // ACTUAL: 8: -2
        // WHY: Bcz Math.round() breaks a .5 tie towards positive infinity (it is floor(x + 0.5)), so -2.5 -> -2 and 2.5 -> 3.
        //      It is NOT "round to even": Math.round(-3.5) gives -3, not -4.
        System.out.println("8b: " + Math.round(2.5) + ", " + Math.round(-3.5));


        // 9. long literal without L
        // long big = 3_000_000_000;   // does not compile
        long big = 3_000_000_000L;     // uppercase L; lowercase l looks like the digit 1
        System.out.println("9: " + big);
        // PREDICT:9: error
        // ACTUAL:9: compile error "integer number too large"; with L it prints 3000000000
        // WHY: Bcz the literal 3_000_000_000 is treated as an int by default, which is too large for an int, so it causes a compilation error.

        // 10. 'a' + 'b'
        System.out.println("10: " + ('a' + 'b'));
        // PREDICT:10: 195
        // ACTUAL: 10: 195
        // WHY: Bcz 'a' has an ASCII value of 97 and 'b' has an ASCII value of 98, so adding them together results in 195.
    }
}