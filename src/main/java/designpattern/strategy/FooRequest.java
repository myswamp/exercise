package designpattern.strategy;

public class FooRequest extends AbstractBaseRequest {
    private String foo;

    public FooRequest(String requestId, String type, String foo) {
        super(requestId, type);
        this.foo = foo;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
