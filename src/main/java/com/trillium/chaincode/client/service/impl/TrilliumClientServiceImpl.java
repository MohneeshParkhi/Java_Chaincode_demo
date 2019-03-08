package com.trillium.chaincode.client.service.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trillium.chaincode.client.Model.Asset;
import com.trillium.chaincode.client.Model.Insurance;
import com.trillium.chaincode.client.Model.Owner;
import com.trillium.chaincode.client.constants.TrilliumConstants;
import com.trillium.chaincode.client.data.ChaincodeResponse;
import com.trillium.chaincode.client.data.TrilliumResponseDetails;
import com.trillium.chaincode.client.data.TrilliumTransactionRequestDetails;
import com.trillium.chaincode.client.data.TrilliumUserDetails;
import com.trillium.chaincode.client.data.ValidationResultDetails;
import com.trillium.chaincode.client.exception.TrilliumClientException;
import com.trillium.chaincode.client.service.TrilliumClientService;
import com.trillium.chaincode.client.service.component.CertificateAuthorityClientService;
import com.trillium.chaincode.client.service.component.HyperledgerFabricChaincodeClient;
import com.trillium.chaincode.client.user.TrilliumUser;

@Service
public class TrilliumClientServiceImpl implements TrilliumClientService
{
    Logger logger = LoggerFactory.getLogger(TrilliumClientServiceImpl.class);

    @Autowired
    private CertificateAuthorityClientService certificateAuthorityClientService;

    @Autowired
    private HyperledgerFabricChaincodeClient chaincodeClient;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public TrilliumResponseDetails<TrilliumUser> enrollAdmin(TrilliumUserDetails userDetails) throws TrilliumClientException
    {
        logger.info(" SERVICE : ENROLLING ADMIN TO GET ADMIN CREDENTIALS ");

        try
        {
            this.certificateAuthorityClientService.enrollAdmin(userDetails);
        }
        catch (EnrollmentException | InvalidArgumentException | IOException | NoSuchAlgorithmException | InvalidKeySpecException | ClassNotFoundException exception)
        {
            logger.error(" SERVICE : ENROLLING ADMIN FAILED  DUE TO " + exception.getMessage());
            throw new TrilliumClientException(exception.getMessage());
        }

        TrilliumResponseDetails<TrilliumUser> trilliumResponseDetails = new TrilliumResponseDetails<TrilliumUser>();
        trilliumResponseDetails.setStatus(TrilliumConstants.TRILLIUM_SUCCESS_STATUS);
        trilliumResponseDetails.setMessage("ADMIN SUCCESSFULLY ENROLLED");

        logger.info(" SERVICE : ENROLLED ADMIN SUCCESSFULLY ");

        return trilliumResponseDetails;
    }


    @Override
    public TrilliumResponseDetails<TrilliumUser> createTrilliumUserCredentials(TrilliumUserDetails userDetails) throws TrilliumClientException
    {
        logger.info(" SERVICE : ENROLLING USER FROM CA SERVER BY USING ADMIN CREDENTIALS ");

        try
        {
            this.certificateAuthorityClientService.registerUser(userDetails);
        }
        catch (Exception exception)
        {
            throw new TrilliumClientException(exception.getMessage());
        }

        TrilliumResponseDetails<TrilliumUser> trilliumResponseDetails = new TrilliumResponseDetails<TrilliumUser>();
        trilliumResponseDetails.setStatus(TrilliumConstants.TRILLIUM_SUCCESS_STATUS);
        trilliumResponseDetails.setMessage("USER SUCCESSFULLY ENROLLED");

        return trilliumResponseDetails;
    }


    @Override
    public TrilliumResponseDetails<ChaincodeResponse> createAsset(TrilliumTransactionRequestDetails transactionRequestDetails) throws TrilliumClientException
    {
        logger.info(" SERVICE : CREATING ASSET AT LEDGER ");

        ValidationResultDetails validationResult = validateTransactionRequest(transactionRequestDetails);

        if (!validationResult.isValidationStatus())
            throw new TrilliumClientException(validationResult.getValidationMessage());

        Asset asset = convertToAsset(transactionRequestDetails);

        if (asset.getVin() == null)
            throw new TrilliumClientException(" ASSET DETAILS NOT FOUND ");

        ChaincodeResponse transactionDetails;
        try
        {
            logger.info(" SERVICE : GETING CREDENTIALES TO INVOKE TRANSACTION ");

            TrilliumUser userContext =
                this.certificateAuthorityClientService.getUserCredentialesFromLocal(transactionRequestDetails.getOrganizationName(), transactionRequestDetails.getUserName());

            if (userContext == null)
            {
                logger.debug(" SERVICE  : USER NOT FOUND  ");
                throw new TrilliumClientException(" USER NOT FOUND ");
            }

            logger.info(" SERVICE : INVOKINGING CREATE ASSET REQUEST TO CHAINCODE COMPONENT  ");
            transactionDetails = this.chaincodeClient.createAsset(transactionRequestDetails.getTransactionName(), userContext, asset);

        }
        catch (Exception e)
        {
            throw new TrilliumClientException(e.getMessage());
        }

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = new TrilliumResponseDetails<ChaincodeResponse>();
        trilliumResponseDetails.setMessage(" ASSET CREATED SUCCESSFULY AT LEDGER ");
        trilliumResponseDetails.setStatus(TrilliumConstants.TRILLIUM_SUCCESS_STATUS);
        trilliumResponseDetails.setData(transactionDetails);

        logger.info(" SERVICE : TRANSACTION SUCCESSFULLY COMPLETED BY USER ");
        return trilliumResponseDetails;
    }


    private Asset convertToAsset(TrilliumTransactionRequestDetails transactionRequestDetails)
    {
        Asset asset = this.modelMapper.map(transactionRequestDetails.getAsset(), Asset.class);
        return asset;
    }


    @Override
    public TrilliumResponseDetails<ChaincodeResponse> updateAsset(TrilliumTransactionRequestDetails transactionRequestDetails) throws TrilliumClientException
    {
        logger.info(" SERVICE : UPDATING ASSET AT LEDGER ");

        ValidationResultDetails validationResult = validateTransactionRequest(transactionRequestDetails);

        if (!validationResult.isValidationStatus())
            throw new TrilliumClientException(validationResult.getValidationMessage());

        Asset asset = setAsset(transactionRequestDetails);

        TrilliumUser userContext;
        ChaincodeResponse transactionDetails;
        try
        {
            logger.info(" SERVICE : GETING CREDENTIALES TO INVOKE TRANSACTION ");
            userContext =
                this.certificateAuthorityClientService.getUserCredentialesFromLocal(transactionRequestDetails.getOrganizationName(), transactionRequestDetails.getUserName());

            if (userContext == null)
            {
                logger.debug(" SERVICE  : USER NOT FOUND  ");
                throw new TrilliumClientException(" USER NOT FOUND ");
            }

            logger.info(" SERVICE : INVOKINGING UPDATE ASSET REQUEST TO CHAINCODE COMPONENT  ");
            transactionDetails = this.chaincodeClient.updateAsset(transactionRequestDetails.getTransactionName(), userContext, asset);

        }
        catch (Exception e)
        {
            throw new TrilliumClientException(e.getMessage());
        }

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = new TrilliumResponseDetails<ChaincodeResponse>();
        trilliumResponseDetails.setMessage(" ASSET UPDATED SUCCESSFULY AT LEDGER FOR TRANSACTION NAME " + transactionRequestDetails.getTransactionName());
        trilliumResponseDetails.setStatus(TrilliumConstants.TRILLIUM_SUCCESS_STATUS);
        trilliumResponseDetails.setData(transactionDetails);

        logger.info(" SERVICE : TRANSACTION SUCCESSFULLY COMPLETED BY USER " + userContext.getName());

        return trilliumResponseDetails;
    }


    private Asset setAsset(TrilliumTransactionRequestDetails transactionRequestDetails)
    {
        Asset asset = new Asset();
        asset.setVin(transactionRequestDetails.getAsset().getVin());

        Owner owner = new Owner();
        owner.setId(transactionRequestDetails.getAsset().getOwner().getId());
        owner.setName(transactionRequestDetails.getAsset().getOwner().getName());

        Insurance insurance = new Insurance();
        insurance.setInsuranceId(transactionRequestDetails.getAsset().getInsurance().getInsuranceId());
        insurance.setConsentDate(transactionRequestDetails.getAsset().getInsurance().getConsentDate());

        asset.setOwner(owner);
        asset.setInsurance(insurance);
        return asset;

    }


    @Override
    public TrilliumResponseDetails<ChaincodeResponse> getAsset(String user, String vin) throws TrilliumClientException
    {
        logger.info(" SERVICE : GETING ASSET FROM LEDGER ");

        Asset asset = new Asset();
        asset.setVin(vin);

        TrilliumUser userContext;
        ChaincodeResponse transactionDetails;
        try
        {
            logger.info(" SERVICE : GETING CREDENTIALES TO INVOKE TRANSACTION ");
            userContext = this.certificateAuthorityClientService.getUserCredentialesFromLocal("Org1", user);

            logger.info(" SERVICE : INVOKINGING UPDATE ASSET REQUEST TO CHAINCODE COMPONENT  ");
            transactionDetails = this.chaincodeClient.getAsset(userContext, asset);

        }
        catch (Exception e)
        {
            throw new TrilliumClientException(e.getMessage());
        }

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = new TrilliumResponseDetails<ChaincodeResponse>();
        trilliumResponseDetails.setMessage(" GET ASSET DETA SUCCESSFULY FROM LEDGER FOR TRANSACTION NAME " + TrilliumConstants.GET_ASSET_TRANSACTION);
        trilliumResponseDetails.setStatus(TrilliumConstants.TRILLIUM_SUCCESS_STATUS);
        trilliumResponseDetails.setData(transactionDetails);

        logger.info(" SERVICE : TRANSACTION SUCCESSFULLY COMPLETED BY USER " + userContext.getName());

        return trilliumResponseDetails;
    }


    private ValidationResultDetails validateTransactionRequest(TrilliumTransactionRequestDetails transactionRequestDetails)
    {
        ValidationResultDetails validationResult = new ValidationResultDetails();
        validationResult.setValidationStatus(false);

        if (transactionRequestDetails.getTransactionName() == null)
        {
            validationResult.setValidationMessage(" TRANSACTION NAME NOT FOUND ");
            validationResult.setValidationStatus(false);
            return validationResult;
        }

        String transactionType = transactionRequestDetails.getTransactionName();

        if (transactionType.equals(TrilliumConstants.CREATE_ASEET_TRANSACTION)
            || transactionType.equals(TrilliumConstants.UPDATE_ASSET_INSURANCE_TRANSACTION)
            || transactionType.equals(TrilliumConstants.UPDATE_ASSET_OWNER_TRANSACTION) || transactionType.equals(TrilliumConstants.GET_ASSET_TRANSACTION))
            validationResult.setValidationStatus(true);

        if (!validationResult.isValidationStatus())
        {
            validationResult.setValidationMessage(" INVALID TRANSACTION NAME ");
            return validationResult;
        }

        validationResult.setValidationStatus(true);
        validationResult.setValidationMessage(" TRANSACTION IS VALID ");
        return validationResult;

    }

}
