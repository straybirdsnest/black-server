package com.example.config.security.test;

import com.example.config.security.AuthenticationWithToken;
import com.example.config.security.ExternalWebServiceStub;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticatedExternalWebService extends AuthenticationWithToken {

    private ExternalWebServiceStub externalWebService;

    public AuthenticatedExternalWebService(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public ExternalWebServiceStub getExternalWebService() {
        return externalWebService;
    }

    public void setExternalWebService(ExternalWebServiceStub externalWebService) {
        this.externalWebService = externalWebService;
    }
}
