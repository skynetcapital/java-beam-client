# BeamClient java library
A Java library for the Beam cryptocurrency Wallet API.

# Building
* mvn clean install
* Note: For the tests to pass, you must be running an instance of Beam's wallet-api on localhost, port 10001. To skip tests. Add -DskipTests to the command line.

# Usage
 Include Maven dependency (update version as necessary):

```
<dependency>
            <groupId>com.beamswap</groupId>
            <artifactId>beamclient</artifactId>
            <version>1.1-SNAPSHOT</version>
</dependency>
```

# Example
1. Cancel transactions stuck In Progress
```
// Setup beam client
BeamClient beamClient = new BeamClient("localhost", 10001);
// Get list of transactions in progress
List<TransactionStatus> transactions = beamClient.getTransactions(TransactionStatusType.IN_PROGRESS);
// Cancel each transaction
transactions.forEach(transaction -> beamClient.cancelTransaction(transaction.getTxId()));

```

# To-do
- [ ] create_address
- [ ] validate_address
- [ ] tx_send
- [ ] tx_status
- [ ] tx_split
- [x] wallet_status
- [ ] get_utxo
- [ ] tx_list
- [ ] tx_cancel
- [ ] ...

# Dependencies
* Gson
* OkHttp
* JUnit (for testing)
* Logback + Log4j
