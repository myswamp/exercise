package lambda;

public class LambdaExpression {

    static void testLambda(Test t) {

        System.out.println(t.accpectStringReturnInt("18"));
    }

    public static void main(String[] args) {
        Test b = c -> Integer.parseInt(c);

        testLambda(b);
    }
}


interface Test {
    int accpectStringReturnInt(String s);
}
