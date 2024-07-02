package designpattern.strategy;

public interface Strategy<R extends AbstractBaseRequest> {

    void handle(R request);

}
