package com.beamswap;

import com.beamswap.exception.BeamException;
import com.beamswap.model.TransactionStatus;
import com.beamswap.model.TransactionStatusType;
import com.beamswap.model.response.BeamResponse;
import com.beamswap.model.response.WalletStatusResponse;
import com.beamswap.model.request.BeamRequest;
import com.beamswap.model.request.WalletStatusRequest;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

public class BeamClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeamClient.class);
    private String host;
    private int port;
    private String endpoint = "/api/wallet";

    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public BeamClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private String sendHttpRequest(final String request) {
        try {
            final String response = post(String.format("http://%s:%s%s", host, port, endpoint), request);

            // Throw error as a RuntimeException if it exists
            throwErrorFromResponse(response);

            return response;
        } catch (IOException e) {
            throw new BeamException();
        }
    }

    private <E> E callBeamApi(final BeamRequest beamRequest, final Class<E> responseType) {
        // Convert request object to JSON payload
        final String jsonPayload = gson.toJson(beamRequest);
        LOGGER.debug("JSON Payload = {}", jsonPayload);

        // Post data as JSON
        final String jsonResponse = sendHttpRequest(jsonPayload);
        LOGGER.debug("Response Type = {}, JSON Response = {}", responseType.getName(), jsonResponse);

        // Retrieve result as BeamResponse, includes error Map
        final BeamResponse beamResponse = gson.fromJson(jsonResponse, BeamResponse.class);
        LOGGER.debug("Parsed JSON Result = {}", beamResponse);

        // Create response object from BeamResponse.getResult()
        final E response = gson.fromJson(gson.toJson(beamResponse.getResult()), responseType);
        LOGGER.debug("Final Response (toString) = {}", response);

        return response;
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (ProtocolException exception) {
            throw new BeamException("Error communicating with Wallet API, check IP and ports.");
        }
    }

    private void throwErrorFromResponse(String response) {
        JsonElement error = jsonParser.parse(response).getAsJsonObject().get("error");

        if (error != null) {
            String errorMessage = error.getAsJsonObject().get("message").toString();
            throw new RuntimeException("Error thrown by the API = " + errorMessage);
        }
    }

    /**
     * Returns wallet status, including available groth balances
     *
     * @return {@link WalletStatusResponse} object containing current wallet status
     */
    public WalletStatusResponse getWalletStatus() {
        WalletStatusRequest request = new WalletStatusRequest();
        WalletStatusResponse response = callBeamApi(request, WalletStatusResponse.class);
        return response;
    }

    public String getUtxos() {
        return sendHttpRequest("{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"get_utxo\"}");
    }

    /**
     * Returns list of all historical Beam transactions in the wallet.
     *
     * @return list of {@link TransactionStatus}
     */
    public List<TransactionStatus> getTransactions() {
        String response = sendHttpRequest("{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"tx_list\"}");

        Type listType = new TypeToken<ArrayList<TransactionStatus>>(){}.getType();
        List<TransactionStatus> txList = gson.fromJson(jsonParser.parse(response).getAsJsonObject().get("result"), listType);

        return txList;
    }

    /**
     * Returns list of all historical Beam transactions in the wallet. Filtered by {@link com.beamswap.model.TransactionStatusType}
     *
     * @return list of {@link TransactionStatus}
     */
    public List<TransactionStatus> getTransactions(TransactionStatusType transactionStatusType) {
        String response = sendHttpRequest("{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"tx_list\", \"params\": {\"filter\": {\"status\": " + transactionStatusType.code() + "}}}");

        Type listType = new TypeToken<ArrayList<TransactionStatus>>(){}.getType();
        List<TransactionStatus> txList = gson.fromJson(jsonParser.parse(response).getAsJsonObject().get("result"), listType);

        return txList;
    }


    /**
     * Send a BEAM transaction.
     * @param address
     * @param amount
     * @param fee
     * @return {@link TransactionStatus} object containing txId and other details.
     */
    public TransactionStatus sendTransaction(String address, long amount, long fee) {
        String response = sendHttpRequest("{\"jsonrpc\":\"2.0\", \"id\": 1,\"method\":\"tx_send\", \"params\":{\"value\" : " + amount + ",\"fee\" : " + fee + ",\"address\" : \"" + address + "\"}}");

        // Get the transaction we just created.
        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
        String txId = jsonObject.get("result").getAsJsonObject().get("txId").getAsString();

        TransactionStatus transactionStatus = getTransaction(txId);

        return transactionStatus;
    }

    /**
     * Cancel a transaction. Usually used on "stuck"/failed transactions.
     * @param txid
     * @return true/false if the transaction was successfully cancelled
     */
    public boolean cancelTransaction(String txid) {
        String response = sendHttpRequest("{\"jsonrpc\":\"2.0\", \"id\": 1,\"method\":\"tx_cancel\", \"params\":{\"txId\" : \"" + txid + "\"}}");

        boolean result = jsonParser.parse(response).getAsJsonObject().get("result").getAsBoolean();

        return result;
    }

    /**
     * Returns a {@link TransactionStatus} object for a given txId which contains fee, number of confirmations, height, kernel, and more
     *
     * @param txId
     * @return {@link TransactionStatus} containining fee, number of confirmations, height, kernel, and more
     */
    public TransactionStatus getTransaction(String txId){
        String response = sendHttpRequest("{\"jsonrpc\":\"2.0\", \"id\": 1,\"method\":\"tx_status\", \"params\":{\"txId\" : \"" + txId + "\" }}");

        String result = jsonParser.parse(response).getAsJsonObject().get("result").toString();
        TransactionStatus transactionStatus = gson.fromJson(result, TransactionStatus.class);

        return transactionStatus;
    }

    /**
     * Determines if walletAddress is a valid Beam address.
     *
     * @param walletAddress
     * @return true/false if walletAddress is a valid beam address
     */
    public boolean validateAddress(String walletAddress) {
        String response = sendHttpRequest("{\"jsonrpc\":\"2.0\", \"id\": 1,\"method\":\"validate_address\", \"params\":{\"address\" : \"" + walletAddress + "\" }}");

        JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
        boolean isValid = jsonObject.get("result").getAsJsonObject().get("is_valid").getAsBoolean();

        return isValid;
    }

    /**
     * Creates a new Beam address. If durationInHours is set to 0, the address will never expire.
     * @param expiration
     * @return {@link String} of the new address
     */
    public String createAddress(String expiration) {
        String response = sendHttpRequest("{\"jsonrpc\":\"2.0\", \"id\": 1,\"method\":\"create_address\", \"params\":{\"expiration\" : \"" + expiration + "\" }}");
        String result = jsonParser.parse(response).getAsJsonObject().get("result").getAsString();

        return result;

    }
}