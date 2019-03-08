package com.trillium.chaincode.client.data;

public class AssetDTO
{

    String vin;
    OwnerDTO owner;
    InsuranceDTO insurance;


    public AssetDTO()
    {
        // TODO Auto-generated constructor stub
    }


    public AssetDTO(String vin, OwnerDTO owner, InsuranceDTO insurance)
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


    public OwnerDTO getOwner()
    {
        return owner;
    }


    public void setOwner(OwnerDTO owner)
    {
        this.owner = owner;
    }


    public InsuranceDTO getInsurance()
    {
        return insurance;
    }


    public void setInsurance(InsuranceDTO insurance)
    {
        this.insurance = insurance;
    }


    @Override
    public String toString()
    {
        return "Asset [vin=" + vin + ", owner=" + owner + ", insurance=" + insurance + "]";
    }

}
