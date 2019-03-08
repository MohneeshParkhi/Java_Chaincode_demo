package com.trillium.chaincode.client.Model;

public class Insurance
{

    String insuranceId;
    String consentDate;


    public Insurance()
    {
        // TODO Auto-generated constructor stub
    }


    public Insurance(String insuranceId, String consentDate)
    {
        super();
        this.insuranceId = insuranceId;
        this.consentDate = consentDate;
    }


    public String getInsuranceId()
    {
        return insuranceId;
    }


    public void setInsuranceId(String insuranceId)
    {
        this.insuranceId = insuranceId;
    }


    public String getConsentDate()
    {
        return consentDate;
    }


    public void setConsentDate(String consentDate)
    {
        this.consentDate = consentDate;
    }


    @Override
    public String toString()
    {
        return "Insurance [insuranceId=" + insuranceId + ", consentDate=" + consentDate + "]";
    }

}
