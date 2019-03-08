package com.trillium.chaincode.client.data;

public class ValidationResultDetails
{
    private boolean validationStatus;
    private String validationMessage;


    public ValidationResultDetails()
    {
        // TODO Auto-generated constructor stub
    }


    public ValidationResultDetails(boolean validationStatus, String validationMessage)
    {
        super();
        this.validationStatus = validationStatus;
        this.validationMessage = validationMessage;
    }


    public boolean isValidationStatus()
    {
        return validationStatus;
    }


    public void setValidationStatus(boolean validationStatus)
    {
        this.validationStatus = validationStatus;
    }


    public String getValidationMessage()
    {
        return validationMessage;
    }


    public void setValidationMessage(String validationMessage)
    {
        this.validationMessage = validationMessage;
    }

}
