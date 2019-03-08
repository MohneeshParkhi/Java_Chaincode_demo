package com.trillium.chaincode.client.service;

import com.trillium.chaincode.client.data.ChaincodeResponse;
import com.trillium.chaincode.client.data.TrilliumResponseDetails;
import com.trillium.chaincode.client.data.TrilliumTransactionRequestDetails;
import com.trillium.chaincode.client.data.TrilliumUserDetails;
import com.trillium.chaincode.client.exception.TrilliumClientException;
import com.trillium.chaincode.client.user.TrilliumUser;

public interface TrilliumClientService
{

    TrilliumResponseDetails<TrilliumUser> enrollAdmin(TrilliumUserDetails userDetails) throws TrilliumClientException;


    TrilliumResponseDetails<TrilliumUser> createTrilliumUserCredentials(TrilliumUserDetails userDetails) throws TrilliumClientException;


    TrilliumResponseDetails<ChaincodeResponse> createAsset(TrilliumTransactionRequestDetails transactionRequestDetails) throws TrilliumClientException;


    TrilliumResponseDetails<ChaincodeResponse> updateAsset(TrilliumTransactionRequestDetails transactionRequestDetails) throws TrilliumClientException;


    TrilliumResponseDetails<ChaincodeResponse> getAsset(String user, String vin) throws TrilliumClientException;

}
