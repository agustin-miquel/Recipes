/*
 FatSecret API
 ----------------------
 This package is for the FatSecret PlatForm to achieve deep integration with the
 FatSecret JavaScript API. The FatSecret REST API calls covered in this library are
 profile.create, profile.get_auth and profile.request_script_session_key.

 If you need additional documentation, please refer to:
 * FatSecret REST API Documentation - http://platform.fatsecret.com/api/Default.aspx?screen=rapih
 * FatSecret JavaScript API Documentation - http://platform.fatsecret.com/api/Default.aspx?screen=jsapih
 * FatSecret Forum - http://groups.google.com/group/fatsecret-platform-api
 */
package com.dev.fatsecret;

import java.net.URL;
import java.util.Map;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/***********************************
 * Interface to FatSecret services *
 ***********************************
 * 
 * All requests to the to the REST API must be signed (using your pre-assigned Shared Secret). Requests must be signed with OAuth (OAuth
 * Core 1.0 protocol - http://oauth.net/core/1.0/)
 *
 * All request parameters (i.e. the HTTP Method, Request URL and Normalized Parameters) must be encoded using the [RFC3986] percent-encoding
 * (%xx) mechanism and concatenated by an '&' character.
 *
 * Use the HMAC-SHA1 signature algorithm as defined by the [RFC2104] to sign the request.
 */
public class FatsecretAPI {

    private static final String URL_BASE = "http://platform.fatsecret.com/rest/server.api?";
    private static final String CONSUMER_KEY = "bcf7f758800648428508b7e7a0d42dc8";
    private static final String SHARED_SECRET = "7921c5fde4ff44d7a400330d3fcb0ad5";

    /**
     * Call FatSecret service.
     *
     * @param service Eg: "food.get"
     * @param idType Eg: "food.id"
     * @param id Eg: "33691"
     * @param resultClass
     * @return 
     * @throws java.lang.Exception
     */
    public Object service(String service, String idType, String id, Class resultClass) throws Exception {
        
        String urlBase = URL_BASE + 
                        "method=" + service + "&" + 
                        idType + "=" + id + "&" +
                        "format=json";

        OAuthBase oAuthBase = new OAuthBase();
        URL url = new URL(urlBase);

        oAuthBase.generateSignature(url, "POST", CONSUMER_KEY, SHARED_SECRET, null, null);
        String postUrl = oAuthBase.getNormalizedUrl();

        MultiValueMap<String, Object> postRequest = new LinkedMultiValueMap<>();        
        for (Map.Entry<String, String> entry : oAuthBase.getParameters().entrySet()) {
            postRequest.add(entry.getKey(), entry.getValue());
        }
        
        //System.out.println("RESULT: " + new RestTemplate().postForObject(postUrl, postRequest, String.class));
        return new RestTemplate().postForObject(postUrl, postRequest, resultClass);        
    }
}
