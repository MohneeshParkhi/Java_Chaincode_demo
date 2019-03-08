package com.trillium.chaincode.client.Model;

public class Asset
{

    String vin;
    Owner owner;
    Insurance insurance;


    public Asset()
    {
        // TODO Auto-generated constructor stub
    }


    public Asset(String vin, Owner owner, Insurance insurance)
    {
        super();
        this.vin = vin;
        this.owner = owner;
        this.insurance = insurance;
    }


    public String getVin()
    {
        return vin;
    }


    public void setVin(String vin)
    {
        this.vin = vin;
    }


    public Owner getOwner()
    {
        return owner;
    }


    public void setOwner(Owner owner)
    {
        this.owner = owner;
    }


    public Insurance getInsurance()
    {
        return insurance;
    }


    public void setInsurance(Insurance insurance)
    {
        this.insurance = insurance;
    }


    @Override
    public String toString()
    {
        return "Asset [vin=" + vin + ", owner=" + owner + ", insurance=" + insurance + "]";
    }

}
