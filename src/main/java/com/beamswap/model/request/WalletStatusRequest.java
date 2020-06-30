package com.beamswap.model.request;

public class WalletStatusRequest extends BeamRequest {

    private static final String API_NAME = "wallet_status";

    public WalletStatusRequest() {
        super(API_NAME);
    }

    public WalletStatusRequest(final boolean includeAssets) {
        super(API_NAME);
        getParameters().put("assets", includeAssets);
    }

}
