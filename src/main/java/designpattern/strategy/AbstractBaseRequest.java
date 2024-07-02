package designpattern.strategy;

public abstract class AbstractBaseRequest {

    protected String requestId;
    protected String type;

    public AbstractBaseRequest(String requestId, String type) {
        this.type = type;
        this.requestId = requestId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
