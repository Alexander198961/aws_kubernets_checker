package com.kubernetes.check.app;

import com.kubernetes.check.api.ExtendedCoreApi;
import com.kubernetes.check.auth.AwsClusterAutheticator;
import com.kubernetes.check.auth.EksAuthenticator;
import com.kubernetes.check.processing.PodHandler;
import com.kubernetes.check.processing.PodStream;
import io.kubernetes.client.models.V1Pod;


import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AppMain {



    public static void main(String [] args) throws Exception {
        AwsClusterAutheticator awsClusterAutheticator = new AwsClusterAutheticator();
        awsClusterAutheticator.prepareAuth();
        PodStream podStream  = new PodStream();
        PodHandler podHandler = new PodHandler();
        Stream<V1Pod> stream =  podStream.getAllPodsAsStream();
        stream.filter( pod -> pod.getStatus().getContainerStatuses()!= null && !pod.getStatus().getPhase().equals("Succeeded") ).forEach(podHandler::processPodWithNotNullContainerStatus);
        stream = podStream.getAllPodsAsStream();
        stream.filter(pod -> pod.getStatus().getContainerStatuses() == null).forEach(podHandler::processPodWithContainerStatusNull);

    }


}
