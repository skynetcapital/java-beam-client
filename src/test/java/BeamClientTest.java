import com.beamswap.BeamClient;
import com.beamswap.model.response.TransactionListResponse;
import com.beamswap.model.response.WalletStatusResponse;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BeamClientTest {

    private final BeamClient beamClient = new BeamClient("localhost", 10001);

    @Test
    public void testWalletStatus() {
        boolean includeAssets = false;
        WalletStatusResponse walletStatusResponse = beamClient.getWalletStatus(includeAssets);

        assertNotNull(walletStatusResponse);
        assertTrue(walletStatusResponse.getAvailableGrothBalance() >= 0);

        System.out.println(walletStatusResponse.toString());

        //assertTrue(walletStatus.getDifficulty() > 0);
    }

    @Test
    public void testWalletStatusWithAssets() {
        boolean includeAssets = true;
        WalletStatusResponse walletStatusResponse = beamClient.getWalletStatus(includeAssets);

        assertNotNull(walletStatusResponse);
        assertTrue(walletStatusResponse.getAvailableGrothBalance() >= 0);

        System.out.println(walletStatusResponse.toString());

        //assertTrue(walletStatus.getDifficulty() > 0);
    }

    @Test
    public void testGetTransactions() {
        TransactionListResponse response = beamClient.getTransactionList();

        assertNotNull(response);

        System.out.println(response.getTransactionStatuses());

        System.out.println(response);
    }
//
//    //@Test
//    public void testGetTransaction() {
//        TransactionStatus transactionStatus = beamClient.getTransaction("2bef147d43bb4bf6abe9b107ea943f7c");
//
//        assertNotNull(transactionStatus);
//
//        System.out.println(transactionStatus);
//    }
//
//    @Test
//    public void testGetTransactionsFilteredByType() {
//        List<TransactionStatus> transactions = beamClient.getTransactions(TransactionStatusType.COMPLETED);
//
//        assertNotNull(transactions);
//
//        System.out.println(transactions);
//    }
//
//    @Test(expected = RuntimeException.class)
//    public void testCancelTransactionInvalid() {
//        boolean result = beamClient.cancelTransaction("123");
//        System.out.println(result);
//    }
//
//    //@Test
//    public void testSendTransaction() {
//        TransactionStatus response = beamClient.sendTransaction("215f68b6d217fd687402353ff9318a8d1149ffe96d8ce2ae2f4cda3360fc0bc62", 111112, 10);
//        System.out.println("Sent:"  + response);
//    }
//
//    @Test
//    public void testCreateAndValidateAddress() {
//        String address = beamClient.createAddress("24h");
//        System.out.println("New address = " + address);
//        assertTrue(beamClient.validateAddress(address));
//    }
}
