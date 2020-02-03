
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import io.kubernetes.client.auth.*;
import io.kubernetes.client.apis.AuthenticationV1Api;


import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.apache.http.HttpEntity;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.stream.Stream;

import io.kubernetes.client.models.V1EndpointsList;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.models.V1EndpointSubset;

import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.*;
import com.kubernetes.check.strategy.ContainerStateCheckStrategy;
import com.kubernetes.check.strategy.ContainerStateTermitationStrategy;
import com.kubernetes.check.strategy.ContainerStateWaitStrategy;


public class KubeTest  extends TestPreparation {
   private  ContainerStateCheckStrategy strategy;

    @Test
    public void check() throws Exception
    {
      Stream<V1Pod> podStream =    api.extendedListPodForAllNamespaces().getItems().stream();
      podStream.filter( pod -> pod.getStatus().getContainerStatuses()!= null).forEach(this::process);
    }

    private void process(V1Pod pod)
    {
        pod.getStatus().getContainerStatuses().forEach(status -> {
            if (status.getState().getTerminated() != null) {
                strategy= new ContainerStateTermitationStrategy();
                strategy.containerCheck(status.getState(), pod);
            }
            else if (status.getState().getWaiting() != null) {
                strategy= new ContainerStateWaitStrategy();
                strategy.containerCheck(status.getState(), pod);
            }


        });
    }






}
