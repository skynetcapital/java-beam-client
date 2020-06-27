package com.beamswap.model.response;

import com.beamswap.model.TransactionStatus;
import java.util.List;

public class TransactionListResponse extends BeamResponse<TransactionStatus> {

    public List<TransactionStatus> getTransactionStatuses(){
        return getResultList();
    }


}
