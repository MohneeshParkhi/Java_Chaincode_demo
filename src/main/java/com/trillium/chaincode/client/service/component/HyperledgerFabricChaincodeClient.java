package com.trillium.chaincode.client.service.component;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeID;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.SDKUtils;
import org.hyperledger.fabric.sdk.TransactionProposalRequest;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.NetworkConfigurationException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trillium.chaincode.client.Model.Asset;
import com.trillium.chaincode.client.constants.TrilliumConstants;
import com.trillium.chaincode.client.data.ChaincodeResponse;
import com.trillium.chaincode.client.user.TrilliumUser;

@Component
public class HyperledgerFabricChaincodeClient
{
    Logger logger = LoggerFactory.getLogger(HyperledgerFabricChaincodeClient.class);

    private final ChaincodeConnectionService chaincodeConnection;


    @Autowired
    public HyperledgerFabricChaincodeClient(ChaincodeConnectionService bcConnection)
    {
        this.chaincodeConnection = bcConnection;
    }


    public ChaincodeResponse createAsset(String transactionName, TrilliumUser userContext, Asset asset)
        throws InvalidArgumentException, NetworkConfigurationException, TransactionException, ProposalException, JsonParseException, JsonMappingException, IOException
    {
        logger.info(" COMPONENT : INVOKING CREATE TRANSACTION BY USER " + userContext.getName());

        this.chaincodeConnection.client.setUserContext(userContext);

        Channel channel = this.chaincodeConnection.client.getChannel(TrilliumConstants.CHANNEL);

        if (channel == null)
        {
            channel = this.chaincodeConnection.client.loadChannelFromConfig(TrilliumConstants.CHANNEL, this.chaincodeConnection.networkConfig);

        }

        channel.initialize();

        String[] assetDetils = {asset.getVin()};

        logger.info(" COMPONENT : PREPARING TRANSACTION PROPOSAL ");
        TransactionProposalRequest transactionProposalRequest = cratetTransactionProposalRequest(userContext, transactionName, assetDetils);

        logger.info(" COMPONENT : SENDING TRANSACTION PROPOSAL TO CHAINCODE ");
        Collection<ProposalResponse> transactionProposalResponse = channel.sendTransactionProposal(transactionProposalRequest);

        SDKUtils.getProposalConsistencySets(transactionProposalResponse);

        logger.info(" COMPONENT : SENDING TRANSACTION COMMIT REQUEST ");
        CompletableFuture<BlockEvent.TransactionEvent> finalTransactionResult = channel.sendTransaction(transactionProposalResponse, userContext);

        finalTransactionResult.join();

        ChaincodeResponse transactionDetails = null;

        for (ProposalResponse response : transactionProposalResponse)
        {
            if (response.getStatus().equals(Status.SUCCESS))
            {
                transactionDetails = new ObjectMapper().readValue(response.getChaincodeActionResponsePayload(), ChaincodeResponse.class);
            }
        }

        channel.shutdown(true);

        logger.info(" COMPONENT : TRANSACTION INVOKED SUCCESSFULLY  ");
        return transactionDetails;

    }


    public ChaincodeResponse updateAsset(String transactionName, TrilliumUser userContext, Asset asset)
        throws InvalidArgumentException, NetworkConfigurationException, TransactionException, ProposalException, JsonParseException, JsonMappingException, IOException
    {

        logger.info(" COMPONENT : INVOKING UPDATE TRANSACTION BY USER " + userContext.getName());

        this.chaincodeConnection.client.setUserContext(userContext);

        Channel channel = this.chaincodeConnection.client.getChannel(TrilliumConstants.CHANNEL);

        if (channel == null)
        {
            channel = this.chaincodeConnection.client.loadChannelFromConfig(TrilliumConstants.CHANNEL, this.chaincodeConnection.networkConfig);

        }

        channel.initialize();

        String[] assetDetils = createAssetDetails(transactionName, asset);

        logger.info(" COMPONENT : PREPARING TRANSACTION PROPOSAL ");
        TransactionProposalRequest transactionProposalRequest = cratetTransactionProposalRequest(userContext, transactionName, assetDetils);

        logger.info(" COMPONENT : SENDING TRANSACTION PROPOSAL TO CHAINCODE ");
        Collection<ProposalResponse> transactionProposalResponse = channel.sendTransactionProposal(transactionProposalRequest);

        SDKUtils.getProposalConsistencySets(transactionProposalResponse);

        logger.info(" COMPONENT : SENDING TRANSACTION COMMIT REQUEST ");
        CompletableFuture<BlockEvent.TransactionEvent> finalTransactionResult = channel.sendTransaction(transactionProposalResponse, userContext);

        finalTransactionResult.join();

        ChaincodeResponse transactionDetails = null;

        for (ProposalResponse response : transactionProposalResponse)
        {
            if (response.getStatus().equals(Status.SUCCESS))
                transactionDetails = new ObjectMapper().readValue(response.getChaincodeActionResponsePayload(), ChaincodeResponse.class);
        }

        channel.shutdown(true);

        logger.info(" COMPONENT : TRANSACTION INVOKED SUCCESSFULLY  ");
        return transactionDetails;
    }


    public ChaincodeResponse getAsset(TrilliumUser userContext, Asset asset)
        throws InvalidArgumentException, NetworkConfigurationException, ProposalException, JsonParseException, JsonMappingException, IOException, InterruptedException,
        ExecutionException, TransactionException
    {
        logger.info(" COMPONENT : INVOKING GET TRANSACTION BY USER " + userContext.getName());

        this.chaincodeConnection.client.setUserContext(userContext);

        Channel channel = this.chaincodeConnection.client.getChannel(TrilliumConstants.CHANNEL);

        if (channel == null)
        {
            channel = this.chaincodeConnection.client.loadChannelFromConfig(TrilliumConstants.CHANNEL, this.chaincodeConnection.networkConfig);

        }

        channel.initialize();

        String[] assetDetils = {asset.getVin()};

        logger.info(" COMPONENT : PREPARING TRANSACTION PROPOSAL ");
        TransactionProposalRequest transactionProposalRequest = cratetTransactionProposalRequest(userContext, TrilliumConstants.GET_ASSET_TRANSACTION, assetDetils);

        logger.info(" COMPONENT : SENDING TRANSACTION PROPOSAL TO CHAINCODE ");
        Collection<ProposalResponse> transactionProposalResponse = channel.sendTransactionProposal(transactionProposalRequest);

        SDKUtils.getProposalConsistencySets(transactionProposalResponse);

        logger.info(" COMPONENT : SENDING TRANSACTION COMMIT REQUEST ");
        CompletableFuture<BlockEvent.TransactionEvent> finalTransactionResult = channel.sendTransaction(transactionProposalResponse, userContext);

        finalTransactionResult.get();

        ChaincodeResponse transactionDetails = null;

        for (ProposalResponse response : transactionProposalResponse)
        {
            if (response.getStatus().equals(Status.SUCCESS))
            {
                transactionDetails = new ObjectMapper().readValue(response.getChaincodeActionResponsePayload(), ChaincodeResponse.class);
            }
        }

        channel.shutdown(true);

        logger.info(" COMPONENT : TRANSACTION INVOKED SUCCESSFULLY  ");
        return transactionDetails;

    }


    private TransactionProposalRequest cratetTransactionProposalRequest(TrilliumUser userContext, String transactionName, String[] assetDetils)
    {

        TransactionProposalRequest txnProposalRequest = TransactionProposalRequest.newInstance(userContext);
        txnProposalRequest.setChaincodeID(ChaincodeID.newBuilder().setName("trilliumchaincode").build());
        txnProposalRequest.setFcn(transactionName);
        txnProposalRequest.setArgs(assetDetils);

        return txnProposalRequest;
    }


    private String[] createAssetDetails(String transactionName, Asset asset)
    {
        String[] assetDetils = new String[3];
        if (transactionName.equals(TrilliumConstants.UPDATE_ASSET_OWNER_TRANSACTION))
        {
            assetDetils[0] = asset.getVin();
            assetDetils[1] = asset.getOwner().getId();
            assetDetils[2] = asset.getOwner().getName();
            return assetDetils;
        }
        assetDetils[0] = asset.getVin();
        assetDetils[1] = asset.getInsurance().getInsuranceId();
        assetDetils[2] = asset.getInsurance().getConsentDate();
        return assetDetils;
    }

}
