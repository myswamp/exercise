package designpattern.strategy;

public class BarRequest extends AbstractBaseRequest {
    private String bar;

    public BarRequest(String requestId, String type, String bar) {
        super(requestId, type);
        this.bar = bar;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
