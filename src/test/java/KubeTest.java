
import com.kubernetes.check.processing.PodHandler;
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
import com.kubernetes.check.strategy.ContainerStateTerminationStrategy;
import com.kubernetes.check.strategy.ContainerStateWaitStrategy;


public class KubeTest  extends TestPreparation {

   private PodHandler podHandler = new PodHandler();

    @Test
    public void check() throws Exception
    {
      Stream<V1Pod> podStream =  getAllPodsAsStream();
      podStream.filter( pod -> pod.getStatus().getContainerStatuses()!= null && !pod.getStatus().getPhase().equals("Succeeded") ).forEach(podHandler::processPodWithNotNullContainerStatus);
      podStream = getAllPodsAsStream();
      podStream.filter(pod -> pod.getStatus().getContainerStatuses() == null).forEach(podHandler::processPodWithContainerStatusNull);
    }

    public  Stream<V1Pod> getAllPodsAsStream()
    {
       return api.extendedListPodForAllNamespaces().getItems().stream();
    }








}
