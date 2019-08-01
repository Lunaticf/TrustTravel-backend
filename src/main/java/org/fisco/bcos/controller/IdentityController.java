package org.fisco.bcos.controller;

import org.fisco.bcos.model.Identity;
import org.fisco.bcos.service.IdentityService;
import org.fisco.bcos.utils.HelpUtils;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/identity")
public class IdentityController {
    private static Logger logger = LoggerFactory.getLogger(IdentityController.class);

    @Autowired
    private IdentityService identityService;

    @RequestMapping("/new")
    public String newAccount() {
        Identity identity = identityService.getIdentity();
        return HelpUtils.getResJson("1uihcas", true, "identity", identity);
    }

}
