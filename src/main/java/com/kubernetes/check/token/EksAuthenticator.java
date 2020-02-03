package com.kubernetes.check.token;

import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.HttpMethodName;
import com.google.common.io.BaseEncoding;
import io.kubernetes.client.util.authenticators.Authenticator;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Workaround for this issue:
     * https://github.com/kubernetes-client/java/issues/315
 */
public class EksAuthenticator implements Authenticator {
    @Override
    public String getName() {
        return "eks";
    }

    @Override
    public String getToken(Map<String, Object> config) {
        return (String) config.get("token");
    }

    @Override
    public boolean isExpired(Map<String, Object> config) {
        System.out.println("Is expired!!!!!!!!!");
        if (config.get("token") == null) {
            return true;
        }

        // inspired by GCPAuthenticator
        Object expiryObj = config.get("expiry");
        if (expiryObj instanceof String) {
            Instant expiry = Instant.parse((String) expiryObj);
            return (expiry != null && expiry.compareTo(Instant.now()) <= 0);
        } else {
            throw new RuntimeException("Unexpected object type: " + expiryObj.getClass() + ", expiry value: " + expiryObj);
        }
    }

    /**
     * https://telegraphhillsoftware.com/awseksk8sclientauth/
     */
    @Override
    public Map<String, Object> refresh(Map<String, Object> config) {
        String clusterName = (String) config.get("cluster-name");
        if (clusterName == null) {
            throw new RuntimeException("cluster-name missing in auth-provider configuration in kube config file");
        }

        Request<Void> request = new DefaultRequest<Void>("sts");
        request.setHttpMethod(HttpMethodName.GET);
        request.setEndpoint(URI.create("https://sts.amazonaws.com/"));

        request.addParameter("Action", "GetCallerIdentity");
        request.addParameter("Version", "2011-06-15");
        request.addHeader("x-k8s-aws-id", clusterName);
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName("sts");

        Instant expiry = Instant.now().plusSeconds(60); // must be <= 60 seconds

        AWSCredentialsProvider credentials = new DefaultAWSCredentialsProviderChain();
        signer.presignRequest(request, credentials.getCredentials(), new Date(expiry.toEpochMilli()));

        StringBuilder sb = new StringBuilder("https://sts.amazonaws.com/");

        AtomicInteger count = new AtomicInteger(0);
        request.getParameters().forEach((k, v) -> {
            try {
                sb.append(count.getAndIncrement() == 0 ? "?" : "&");
                sb.append(URLEncoder.encode(k, "utf-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(v.get(0), "utf-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        config.put("token", "k8s-aws-v1." + BaseEncoding.base64Url().omitPadding().encode(sb.toString().getBytes()));
        config.put("expiry", expiry.toString());
        return config;
    }
}