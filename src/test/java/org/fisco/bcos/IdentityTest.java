package org.fisco.bcos;


import org.fisco.bcos.service.IdentityService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IdentityTest extends BaseTest {
    @Autowired
    private IdentityService identityService;

    @Test
    public void getAddress() {
        System.out.println(identityService.getIdentity().address);
    }
}
