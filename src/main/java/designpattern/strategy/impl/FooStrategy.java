package designpattern.strategy.impl;

import designpattern.strategy.FooRequest;
import designpattern.strategy.Strategy;

public class FooStrategy implements Strategy<FooRequest> {

    @Override
    public void handle(FooRequest request) {
        System.out.println(request.getFoo());
    }
}
