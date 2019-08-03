package org.fisco.bcos.autoconfigure;

import lombok.Data;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.model.TrustTravel;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "contract")
public class ContractConfig {

    private String address;

    @Autowired
    private Web3j web3j;

    @Autowired
    private Credentials credentials;

    @Bean
    public TrustTravel getTrustTravel() {
        return TrustTravel.load(address, web3j, credentials, new StaticGasProvider(
                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
    }
}
