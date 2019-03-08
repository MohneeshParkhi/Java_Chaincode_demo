package com.trillium.chaincode.client.data;

public class TrilliumResponseDetails<T>
{

    private String status;
    private String message;
    private T data;


    public TrilliumResponseDetails()
    {
        // TODO Auto-generated constructor stub
    }


    public TrilliumResponseDetails(String status, String message, T data)
    {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public String getMessage()
    {
        return message;
    }


    public void setMessage(String message)
    {
        this.message = message;
    }


    public T getData()
    {
        return data;
    }


    public void setData(T data)
    {
        this.data = data;
    }


    public String getStatus()
    {
        return status;
    }


    public void setStatus(String status)
    {
        this.status = status;
    }

}
