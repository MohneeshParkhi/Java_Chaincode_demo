package com.trillium.chaincode.client.service.component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric_ca.sdk.RegistrationRequest;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trillium.chaincode.client.constants.TrilliumConstants;
import com.trillium.chaincode.client.data.TrilliumUserDetails;
import com.trillium.chaincode.client.enrollment.TrilliumEnrollment;
import com.trillium.chaincode.client.exception.TrilliumClientException;
import com.trillium.chaincode.client.user.TrilliumUser;

@Component
public class CertificateAuthorityClientService
{

    Logger logger = LoggerFactory.getLogger(CertificateAuthorityClientService.class);

    private final ChaincodeConnectionService bcConnection;


    @Autowired
    public CertificateAuthorityClientService(ChaincodeConnectionService bcConnection)
    {
        this.bcConnection = bcConnection;
    }


    public void enrollAdmin(TrilliumUserDetails userDetails)
        throws TrilliumClientException, EnrollmentException, InvalidArgumentException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, ClassNotFoundException
    {
        logger.info(" COMPONENT : ENROLLING ADMIN FROM CA SERVER ");

        TrilliumUser admin = getUserCredentialesFromLocal(userDetails.getOrganization(), userDetails.getUsername());

        if (admin != null)
        {
            logger.debug(" COMPONENT : ADMIN ALREADY ENROLLED FROM CA SERVER ");
            throw new TrilliumClientException(" ADMIN ALREADY ENROLLED ");
        }

        logger.debug(" COMPONENT : ENROLLING REQUEST TO CA SERVER USING CA CLIENT ");
        Enrollment enrollment = this.bcConnection.hfcaClient.enroll(userDetails.getUsername(), userDetails.getSecret());

        createCredentialesToFileSystem(userDetails, enrollment);

        logger.info(" COMPONENT : ENROLLED ADMIN FROM CA SERVER SUCCESSFULLY ");

    }


    public void registerUser(TrilliumUserDetails userDetails) throws Exception
    {
        logger.info(" COMPONENT : ENROLLING USER FROM CA SERVER ");

        TrilliumUser user = getUserCredentialesFromLocal(userDetails.getOrganization(), userDetails.getUsername());

        if (user != null)
        {
            logger.info(" COMPONENT : USER ALREADY REGISTERED AND ENROLLED ");
            throw new TrilliumClientException(" USER ALREADY REGISTERED AND ENROLLED ");
        }

        RegistrationRequest regRequest = new RegistrationRequest(userDetails.getUsername(), TrilliumConstants.AFFLICATION);
        TrilliumUser adminCredentiales = getUserCredentialesFromLocal(userDetails.getOrganization(), TrilliumConstants.ADMIN_USERNAME);

        if (adminCredentiales == null)
        {
            logger.error(" COMPONENT : ADMIN CREDENTIALES NOT FOUND.PLEASE FIRST ENROLL ADMIN ");
            throw new TrilliumClientException(" ADMIN CREDENTIALES NOT FOUND.PLEASE FIRST ENROLL ADMIN ");
        }

        String enrollSecret = this.bcConnection.hfcaClient.register(regRequest, adminCredentiales);

        Enrollment enrollment = this.bcConnection.hfcaClient.enroll(userDetails.getUsername(), enrollSecret);

        createCredentialesToFileSystem(userDetails, enrollment);

        logger.info(" COMPONENT :  USER ENROLLED SUCCESSFULLY ");

    }


    @SuppressWarnings("resource")
    private void createCredentialesToFileSystem(TrilliumUserDetails userDetails, Enrollment enrollment) throws IOException
    {

        String filePath = TrilliumConstants.CERTIFICATE_DIRECTORY + userDetails.getUsername() + ".pem";

        File directory = new File(TrilliumConstants.CERTIFICATE_DIRECTORY);
        if (!directory.exists())
            directory.mkdirs();

        FileOutputStream file = new FileOutputStream(filePath);
        ObjectOutputStream out = new ObjectOutputStream(file);

        out.writeObject(userDetails);

    }


    @SuppressWarnings("resource")
    public TrilliumUser getUserCredentialesFromLocal(String organizationName, String userName)
        throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, ClassNotFoundException
    {

        String filePath = TrilliumConstants.CERTIFICATE_DIRECTORY + userName + ".pem";

        TrilliumUser userCredentials = null;

        File file = new File(filePath);
        if (file.exists())
        {
            FileInputStream fileStream = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileStream);
            userCredentials = (TrilliumUser) in.readObject();
        }

        return userCredentials;

    }


}
