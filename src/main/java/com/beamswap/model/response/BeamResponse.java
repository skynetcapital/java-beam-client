package com.beamswap.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BeamResponse {
    @SerializedName("jsonrpc")
    private String jsonVersion;

    @SerializedName("id")
    private int requestId;

    @SerializedName("error")
    private Map<String, Object> error;

    @SerializedName("result")
    private Map<String, Object> result;

    public String getJsonVersion() {
        return jsonVersion;
    }

    public int getRequestId() {
        return requestId;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "BeamResponse{" +
                "jsonVersion='" + jsonVersion + '\'' +
                ", requestId=" + requestId +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
