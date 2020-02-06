package com.kubernetes.check.auth;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.kubernetes.check.auth.EksAuthenticator;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.Configuration;

public class AwsClusterAutheticator {

    public void prepareAuth() throws Exception
    {
        KubeConfig.registerAuthenticator(new EksAuthenticator());
        String content = new String(Files.readAllBytes(Paths.get(System.getProperty("user.home") +"/.kube/config")));
        KubeConfig kc = KubeConfig.loadKubeConfig(new StringReader(content));
        ApiClient client = ClientBuilder.kubeconfig(kc).build();
        Configuration.setDefaultApiClient(client);
    }

}
