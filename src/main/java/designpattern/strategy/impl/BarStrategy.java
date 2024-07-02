package designpattern.strategy.impl;

import designpattern.strategy.BarRequest;
import designpattern.strategy.Strategy;

public class BarStrategy implements Strategy<BarRequest> {
    @Override
    public void handle(BarRequest request) {
        System.out.println(request.getBar());
    }
}
