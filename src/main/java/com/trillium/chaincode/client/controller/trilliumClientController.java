package com.trillium.chaincode.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trillium.chaincode.client.data.ChaincodeResponse;
import com.trillium.chaincode.client.data.TrilliumResponseDetails;
import com.trillium.chaincode.client.data.TrilliumTransactionRequestDetails;
import com.trillium.chaincode.client.data.TrilliumUserDetails;
import com.trillium.chaincode.client.exception.TrilliumClientException;
import com.trillium.chaincode.client.service.TrilliumClientService;
import com.trillium.chaincode.client.user.TrilliumUser;

@RestController
public class trilliumClientController
{

    @Autowired
    private TrilliumClientService trilliumClientService;


    @RequestMapping(value = "/admin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrilliumResponseDetails<?>> enrollAdmin(
        @RequestBody TrilliumUserDetails adminDetails)
        throws TrilliumClientException
    {

        TrilliumResponseDetails<?> userResponse = trilliumClientService.enrollAdmin(adminDetails);

        return new ResponseEntity<TrilliumResponseDetails<?>>(userResponse, HttpStatus.OK);

    }


    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrilliumResponseDetails<?>> createTrilliumUserCredentials(
        @RequestBody TrilliumUserDetails userDetails)
        throws TrilliumClientException
    {

        TrilliumResponseDetails<TrilliumUser> userResponse = trilliumClientService.createTrilliumUserCredentials(userDetails);

        return new ResponseEntity<TrilliumResponseDetails<?>>(userResponse, HttpStatus.OK);

    }


    @RequestMapping(value = "/asset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>> createTrilliumAsset(@RequestBody TrilliumTransactionRequestDetails transactionRequestDetails)
        throws TrilliumClientException
    {

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = trilliumClientService.createAsset(transactionRequestDetails);

        return new ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>>(trilliumResponseDetails, HttpStatus.CREATED);

    }


    @RequestMapping(value = "/updateAsset", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>> updateTrilliumAsset(@RequestBody TrilliumTransactionRequestDetails transactionRequestDetails)
        throws TrilliumClientException
    {

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = trilliumClientService.updateAsset(transactionRequestDetails);

        return new ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>>(trilliumResponseDetails, HttpStatus.CREATED);

    }


    @RequestMapping(value = "/asset/{user}/{vin}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>> getTrilliumAsset(@PathVariable("user") String user, @PathVariable("vin") String vin)
        throws TrilliumClientException
    {

        TrilliumResponseDetails<ChaincodeResponse> trilliumResponseDetails = trilliumClientService.getAsset(user, vin);

        return new ResponseEntity<TrilliumResponseDetails<ChaincodeResponse>>(trilliumResponseDetails, HttpStatus.CREATED);

    }

}
