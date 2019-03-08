package com.trillium.chaincode.client.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.trillium.chaincode.client.constants.TrilliumConstants;
import com.trillium.chaincode.client.data.TrilliumResponseDetails;
import com.trillium.chaincode.client.exception.TrilliumClientException;

/**
 * Exception Handler contoller 
 * 
 * @author kandalakar.r
 *
 */
@ControllerAdvice
public class TrilliumExceptionControllerAdvice
{

    @ExceptionHandler(value = {TrilliumClientException.class, IOException.class})
    protected ResponseEntity<TrilliumResponseDetails<String>> AssetExceptionHandler(Exception exception)
    {

        TrilliumResponseDetails<String> contentResponse = new TrilliumResponseDetails<String>();
        contentResponse.setStatus(TrilliumConstants.STATUS_FAILURE);
        contentResponse.setMessage(exception.getMessage());

        return new ResponseEntity<TrilliumResponseDetails<String>>(contentResponse, HttpStatus.OK);
    }

}
