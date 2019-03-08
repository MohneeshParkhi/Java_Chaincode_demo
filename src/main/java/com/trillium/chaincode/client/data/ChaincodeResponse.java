package com.trillium.chaincode.client.data;

import com.trillium.chaincode.client.Model.Asset;

public class ChaincodeResponse
{
    private String transactionId;
    private String transactionTimestamp;
    private Asset asset;


    public ChaincodeResponse()
    {
        // TODO Auto-generated constructor stub
    }


    public ChaincodeResponse(String transactionId, String transactionTimestamp, Asset asset)
    {
        super();
        this.transactionId = transactionId;
        this.transactionTimestamp = transactionTimestamp;
        this.asset = asset;
    }


    public String getTransactionId()
    {
        return transactionId;
    }


    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }


    public String getTransactionTimestamp()
    {
        return transactionTimestamp;
    }


    public void setTransactionTimestamp(String transactionTimestamp)
    {
        this.transactionTimestamp = transactionTimestamp;
    }


    public Asset getAsset()
    {
        return asset;
    }


    public void setAsset(Asset asset)
    {
        this.asset = asset;
    }


    @Override
    public String toString()
    {
        return "ChaincodeResponse [transactionId=" + transactionId + ", transactionTimestamp=" + transactionTimestamp + ", asset=" + asset + "]";
    }

}
