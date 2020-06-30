package com.beamswap.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class BeamRequest {

    private static final int DEFAULT_REQUEST_ID = 0;

    @SerializedName("jsonrpc")
    private final String JSON_RPC_VERSION = "2.0";

    @SerializedName("id")
    private int requestId;

    @SerializedName("method")
    private String requestMethod;

    @SerializedName("params")
    private Map<String, Object> parameters;

    public BeamRequest(final String requestMethod) {
        this.requestId = DEFAULT_REQUEST_ID;
        this.requestMethod = requestMethod;
        this.parameters = new HashMap<>();
    }

    public BeamRequest(final int requestId, final String requestMethod) {
        this.requestId = requestId;
        this.requestMethod = requestMethod;
        this.parameters = new HashMap<>();
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
