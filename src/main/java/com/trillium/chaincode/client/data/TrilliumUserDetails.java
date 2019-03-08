package com.trillium.chaincode.client.data;

public class TrilliumUserDetails
{

    private String username;
    private String secret;
    private String organization;


    public TrilliumUserDetails()
    {
        // TODO Auto-generated constructor stub
    }


    public TrilliumUserDetails(String username, String secret, String organization)
    {
        super();
        this.username = username;
        this.secret = secret;
        this.organization = organization;
    }


    public String getSecret()
    {
        return secret;
    }


    public void setSecret(String secret)
    {
        this.secret = secret;
    }


    public String getUsername()
    {
        return username;
    }


    public void setUsername(String username)
    {
        this.username = username;
    }


    public String getOrganization()
    {
        return organization;
    }


    public void setOrganization(String organization)
    {
        this.organization = organization;
    }

}
