package com.navis.insightserver.config;

import com.nimbusds.oauth2.sdk.auth.ClientAuthenticationMethod;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Pac4JConfig {
    @Value("${openid.clientid}")
    private
    String clientID;
    @Value("${openid.authority}")
    private
    String authority;

    @Value("${openid.redirecturi}")
    String redirecturi;

    @Bean
    public Config config() {
        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId(clientID);
        oidcConfiguration.setSecret("XXX"); // required by config but not necessary for openidconnect login processing
        oidcConfiguration.setDiscoveryURI(authority + "/.well-known/openid-configuration");
        oidcConfiguration.setResponseType("id_token");
        oidcConfiguration.setResponseMode("form_post");
        oidcConfiguration.setScope("openid email realm");
        oidcConfiguration.setUseNonce(true);
        oidcConfiguration.setClientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_JWT);

        final OidcClient oidcClient = new OidcClient(oidcConfiguration);

        List<Client> clientlist = new ArrayList<Client>();
        clientlist.add(oidcClient);
        Clients clients = new Clients(redirecturi, clientlist);

        Config config = new Config(clients);

        return config;
    }

}
