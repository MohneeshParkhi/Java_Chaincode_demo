package com.trillium.chaincode.client.data;

public class OwnerDTO
{
    String ownerId;
    String name;


    public OwnerDTO()
    {
        // TODO Auto-generated constructor stub
    }


    public OwnerDTO(String id, String name)
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
