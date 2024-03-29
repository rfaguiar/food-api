package com.food.api.security.authorizationserver;

import com.nimbusds.jose.jwk.JWKSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class JwkSetController {
    private static final Logger LOGGER = LogManager.getLogger(JwkSetController.class);

    private final JWKSet jwkSet;

    @Autowired
    public JwkSetController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/.well-known/jwks.json")
    public Map<String, Object> keys() {
        LOGGER.info("generate JWKSet");
        return jwkSet.toJSONObject();
    }

}
