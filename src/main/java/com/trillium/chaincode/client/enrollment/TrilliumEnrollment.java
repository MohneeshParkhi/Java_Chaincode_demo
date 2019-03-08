package com.trillium.chaincode.client.enrollment;

import java.security.PrivateKey;

import org.hyperledger.fabric.sdk.Enrollment;

public class TrilliumEnrollment implements Enrollment
{

    private final PrivateKey privateKey;
    private final String certificate;


    public TrilliumEnrollment(PrivateKey privateKey, String certificate)
    {

        this.certificate = certificate;

        this.privateKey = privateKey;
    }


    @Override
    public PrivateKey getKey()
    {
        // TODO Auto-generated method stub
        return privateKey;
    }


    @Override
    public String getCert()
    {
        // TODO Auto-generated method stub
        return certificate;
    }

}
