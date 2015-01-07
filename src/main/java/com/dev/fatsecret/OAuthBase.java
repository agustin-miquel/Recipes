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

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class OAuthBase {

    /* OAuth Parameters */
    public static final String OAUTH_VERSION_NUMBER = "1.0";
    public static final String OAUTH_PARAMETER_PREFIX = "oauth_";
    public static final String XOAUTH_PARAMETER_PREFIX = "xoauth_";
    public static final String OPEN_SOCIAL_PARAMETER_PREFIX = "opensocial_";

    public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
    public static final String OAUTH_CALLBACK = "oauth_callback";
    public static final String OAUTH_VERSION = "oauth_version";
    public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
    public static final String OAUTH_SIGNATURE = "oauth_signature";
    public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
    public static final String OAUTH_NONCE = "oauth_nonce";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";

    protected String unreservedChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~";
    
    private String normalizedUrl;
    private String requestParameters;
    private String signatureBase;
    private String signature;
    
    private Map<String, String> parameters = new HashMap<>();

    public void generateSignature(URL url, String method, String consumerKey, String consumerSecret, String token, String tokenSecret) {
        GenerateSignatureBase(url, consumerKey, token, method, generateTimeStamp(), generateNonce(), "HMAC-SHA1");
        
        String secret = consumerSecret + "&";
        if (tokenSecret != null) {
            secret += tokenSecret;
        }

        signature = getHMACSHA1(secret, signatureBase);
        
        // Store the calculated signature in the parameters map:
        parameters.put(OAUTH_SIGNATURE, signature);
    }

    public String getNormalizedUrl() {
        return normalizedUrl;
    }

    public String getRequestParameters() {
        return requestParameters;
    }

    public String getSignatureBase() {
        return signatureBase;
    }

    public String getSignature() {
        return signature;
    }
    
    public Map<String, String> getParameters() {
        return parameters;
    }

    // PRIVATE -----------------------------------------------------------------------------------------------------------------------------
    
    private void GenerateSignatureBase(URL url, String consumerKey, String token, String httpMethod, String timeStamp, String nonce, 
            String signatureType) {

        parameters = getQueryParameters(url.getQuery(), parameters);

        parameters.put(OAUTH_VERSION, OAUTH_VERSION_NUMBER);
        parameters.put(OAUTH_NONCE, nonce);
        parameters.put(OAUTH_TIMESTAMP, timeStamp);
        parameters.put(OAUTH_SIGNATURE_METHOD, signatureType);
        parameters.put(OAUTH_CONSUMER_KEY, consumerKey);

        if (!IsNullOrEmpty(token)) {
            parameters.put(OAUTH_TOKEN, token);
        }

        normalizedUrl = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1 && !((url.getProtocol() == "http" && url.getPort() == 80) || (url.getProtocol() == "https" && url.getPort() == 443))) {
            normalizedUrl += ":" + url.getPort();
        }
        normalizedUrl += url.getPath();

        requestParameters = getRawRequestParameters(parameters);

        String normalizedRequestParameters = normalizeRequestParameters(parameters);
        signatureBase = httpMethod + "&" + encode(normalizedUrl) + "&" + encode(normalizedRequestParameters);
    }

    private boolean IsNullOrEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    private Map<String, String> getQueryParameters(String parameters, Map<String, String> result) {
        if (parameters.startsWith("?")) {
            parameters = parameters.substring(1);
        }

        if (!IsNullOrEmpty(parameters)) {
            String[] p = parameters.split("&");
            for (String s : p) {
                if (!IsNullOrEmpty(s) && !s.startsWith(OAUTH_PARAMETER_PREFIX) && !s.startsWith(XOAUTH_PARAMETER_PREFIX) && !s.startsWith(OPEN_SOCIAL_PARAMETER_PREFIX)) {
                    if (s.indexOf('=') > -1) {
                        String[] temp = s.split("=");
                        result.put(temp[0], temp[1]);
                    } else {
                        result.put(s, "");
                    }
                }
            }
        }

        return result;
    }

    private String encode(String value) {
        if (value == null) {
            return "";
        }

        try {
            return URLEncoder.encode(value, "utf-8")
                    .replace("+", "%20")
                    .replace("!", "%21")
                    .replace("*", "%2A")
                    .replace("\\", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29");
        } catch (UnsupportedEncodingException wow) {
            throw new RuntimeException(wow.getMessage(), wow);
        }
    }

    private String normalizeRequestParameters(Map<String, String> parameters) {
        List<String> parameterList = new ArrayList<>();

        for (String key : parameters.keySet()) {
            String parameter = key + "=" + encode(parameters.get(key));
            parameterList.add(parameter);
        }

        Collections.sort(parameterList);

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < parameterList.size(); i++) {
            s.append(parameterList.get(i));
            if (i != parameterList.size() - 1) {
                s.append("&");
            }
        }

        return s.toString();
    }

    private String getRawRequestParameters(Map<String, String> parameters) {
        List<String> parameterList = new ArrayList<>();

        for (String key : parameters.keySet()) {
            String parameter = key + "=" + parameters.get(key);
            parameterList.add(parameter);
        }

        Collections.sort(parameterList);

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < parameterList.size(); i++) {
            s.append(parameterList.get(i));
            if (i != parameterList.size() - 1) {
                s.append("&");
            }
        }

        return s.toString();
    }

    private String getHMACSHA1(String key, String data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HMAC-SHA1");

            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));

            return Base64Util.encodeBytes(rawHmac);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | IllegalStateException e) {
            throw new RuntimeException("Unable to generate HMAC-SHA1", e);
        }
    }

    private String generateTimeStamp() {
        long timestamp = (long) System.currentTimeMillis();// / 1000;
        return Long.toString(timestamp);
    }

    private String generateNonce() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
