package com.trillium.chaincode.client.service.component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.PostConstruct;

import org.hyperledger.fabric.sdk.HFClient;
import org.hyperledger.fabric.sdk.NetworkConfig;
import org.hyperledger.fabric.sdk.NetworkConfig.CAInfo;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.hyperledger.fabric.sdk.security.CryptoSuite;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ChaincodeConnectionService
{
    public HFCAClient hfcaClient;
    public NetworkConfig networkConfig;
    public HFClient client;
    public String ORG = "Org1";
    private final ResourceLoader resourceLoader;


    @Autowired
    public ChaincodeConnectionService(ResourceLoader resourceLoader)
    {
        this.resourceLoader = resourceLoader;

    }


    @PostConstruct
    private void init()
    {

        try
        {
            Resource resource = this.resourceLoader.getResource("classpath:connection.json");

            //this.networkConfig = NetworkConfig.fromJsonStream(CertificateAuthorityClientService.class.getResourceAsStream("connection.json"));
            this.networkConfig = NetworkConfig.fromJsonStream(resource.getInputStream());

            CAInfo ca = networkConfig.getOrganizationInfo("Org1").getCertificateAuthorities().get(0);

            CryptoSuite cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
            this.hfcaClient = HFCAClient.createNewInstance(ca);
            this.hfcaClient.setCryptoSuite(cryptoSuite);
            init2();

        }
        catch (
            IOException | InvalidArgumentException | org.hyperledger.fabric.sdk.exception.InvalidArgumentException
            | IllegalAccessException | InstantiationException | ClassNotFoundException | CryptoException | NoSuchMethodException
            | InvocationTargetException | NetworkConfigurationException e)
        {
            System.out.println(" init error");
            e.printStackTrace();
        }

    }


    public void init2()
    {
        this.client = HFClient.createNewInstance();
        CryptoSuite cryptoSuite;
        try
        {
            cryptoSuite = CryptoSuite.Factory.getCryptoSuite();
            client.setCryptoSuite(cryptoSuite);

        }
        catch (
            IllegalAccessException | InstantiationException | ClassNotFoundException | CryptoException | NoSuchMethodException
            | InvocationTargetException | org.hyperledger.fabric.sdk.exception.InvalidArgumentException e)
        {
            e.printStackTrace();
        }

    }
}
