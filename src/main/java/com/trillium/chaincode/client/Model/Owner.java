package com.trillium.chaincode.client.Model;

public class Owner
{
    String ownerId;
    String name;


    public Owner()
    {
        // TODO Auto-generated constructor stub
    }


    public Owner(String id, String name)
    {
        super();
        this.ownerId = id;
        this.name = name;
    }


    public String getId()
    {
        return ownerId;
    }


    public void setId(String id)
    {
        this.ownerId = id;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    @Override
    public String toString()
    {
        return "Owner [ownerId=" + ownerId + ", name=" + name + "]";
    }

}
