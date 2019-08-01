package org.fisco.bcos.service;

import org.fisco.bcos.model.Identity;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.springframework.stereotype.Service;

@Service
public class IdentityService {
    public Identity getIdentity() {
        Credentials credentials = GenCredential.create();
        //账户地址
        String address = credentials.getAddress();
        //账户私钥
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //账户公钥
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);

        return new Identity(address, privateKey);
    }

}
