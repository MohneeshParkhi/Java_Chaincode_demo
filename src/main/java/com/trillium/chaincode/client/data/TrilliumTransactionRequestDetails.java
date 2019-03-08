package com.trillium.chaincode.client.data;

public class TrilliumTransactionRequestDetails
{
    private AssetDTO asset;
    private String transactionName;
    private String userName;
    private String organizationName;


    public TrilliumTransactionRequestDetails()
    {
        // TODO Auto-generated constructor stub
    }


    public TrilliumTransactionRequestDetails(AssetDTO asset, String transactionName, String userName, String organizationName)
    {
        super();
        this.asset = asset;
        this.transactionName = transactionName;
        this.userName = userName;
        this.organizationName = organizationName;
    }


    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getOrganizationName()
    {
        return organizationName;
    }


    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }


    public AssetDTO getAsset()
    {
        return asset;
    }


    public void setAsset(AssetDTO asset)
    {
        this.asset = asset;
    }


    public String getTransactionName()
    {
        return transactionName;
    }


    public void setTransactionName(String transactionName)
    {
        this.transactionName = transactionName;
    }


    @Override
    public String toString()
    {
        return "TrilliumTransactionRequestDetails [asset=" + asset + ", transactionName=" + transactionName + "]";
    }

}
