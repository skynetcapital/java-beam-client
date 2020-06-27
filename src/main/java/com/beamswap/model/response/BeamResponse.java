package com.beamswap.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class BeamResponse<E> {

    @SerializedName("jsonrpc")
    private String jsonVersion;

    @SerializedName("id")
    private int requestId;

    @SerializedName("error")
    private Map<String, Object> error;

    @SerializedName("result")
    private Object result;

    private List<E> resultList;

    public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    public String getJsonVersion() {
        return jsonVersion;
    }

    public int getRequestId() {
        return requestId;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public Object getResult() {
        return result;
    }

    public void setJsonVersion(String jsonVersion) {
        this.jsonVersion = jsonVersion;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }

    public void setResult(Object result) {
        this.result = result;
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
