
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.auth.*;
import io.kubernetes.client.apis.AuthenticationV1Api;

import java.awt.*;
import java.io.*;

import javax.swing.*;


import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.apache.http.HttpEntity;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.kubernetes.client.models.V1EndpointsList;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.models.V1EndpointSubset;
import java.util.List;
import java.util.stream.Collectors;

import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.*;
import ui.DialogInvoker;
import io.kubernetes.client.PodLogs;
import org.apache.commons.io.IOUtils;


public class KubeTest  extends TestPreparation {


   private DialogInvoker dialogInvoker = new DialogInvoker();
   private PodLogs podLogs = new PodLogs();
    @Test
    public void  testSendNotification() throws Exception
    {

        int exceptionCount=0;
        for(V1Pod pod:api.extendedListPodForAllNamespaces().getItems())
        {
            if( pod.getStatus().getContainerStatuses()!= null) {
                pod.getStatus().getContainerStatuses().forEach(status -> {
                    if (status.getState().getTerminated() != null) {
                        System.out.println("TERMINATED=="+ pod.getMetadata().getNamespace());
                        V1ContainerStateTerminated terminatedState = status.getState().getTerminated();
                        String message = terminatedState.getMessage();
                        if (message != null || terminatedState.getExitCode() > 0 || terminatedState.getReason() == null) {
                            dialogInvoker.showUiMessage(pod.getMetadata().getName() , pod.getMetadata().getNamespace());
                        }
                        System.out.println("my pod===" + pod.getMetadata().getName());
                    }
                    else if (status.getState().getWaiting() != null) {
                        System.out.println("INSIDEE");
                        V1ContainerStateWaiting waitingState = status.getState().getWaiting();
                        if (waitingState.getReason().equals("CrashLoopBackOff") || waitingState.getReason().equals("ImagePullBackOff") || waitingState.getReason().equals("ErrImagePull")) {
                            dialogInvoker.showUiMessage(pod.getMetadata().getName(),  pod.getMetadata().getNamespace());
                            try {
                                InputStream inputStream = podLogs.streamNamespacedPodLog(pod);
                                String podLogs = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
                                System.out.println("pod logs===" + podLogs);
                            }
                            catch (Exception ex)
                            {

                            }
                        }

                    }

                });
            }

        }

    }






}
