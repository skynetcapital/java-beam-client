package com.beamswap.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WalletStatusResponse extends BeamResponse<Object> {

    // These longs are only present when assets = true in the request
    private long available;
    private long locked;
    private long maturing;
    private long receiving;
    private long sending;

    private double difficulty;

    @SerializedName("current_height")
    private long currentHeight;

    @SerializedName("current_state_hash")
    private String currentStateHash;

    @SerializedName("prev_state_hash")
    private String prevStateHash;

    private List<Total> totals;

    private class Total {
        private long available;
        public long getAvailable() {
            return available;
        }
    }

    // getAvailableGrothBalance (either returns available groth or cumulative from the totals)
    public long getAvailableGrothBalance(){
        if (totals == null) {
            return available;
        }

        // todo - search list for index of asset_id 0

        return totals.get(0).getAvailable();
    }

    // getAvailableBeamBalance (either returns available beam or cumulative from the totals)

    public long getCurrentHeight() {
        return currentHeight;
    }

    public long getLocked() {
        return locked;
    }

    public long getMaturing() {
        return maturing;
    }

    public long getReceiving() {
        return receiving;
    }

    public long getSending() {
        return sending;
    }

    public String getCurrentStateHash() {
        return currentStateHash;
    }

    public String getPrevStateHash() {
        return prevStateHash;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "WalletStatusResponse{" +
                "available=" + available +
                ", locked=" + locked +
                ", maturing=" + maturing +
                ", receiving=" + receiving +
                ", sending=" + sending +
                ", currentHeight=" + currentHeight +
                ", currentStateHash='" + currentStateHash + '\'' +
                ", prevStateHash='" + prevStateHash + '\'' +
                '}';
    }
}
