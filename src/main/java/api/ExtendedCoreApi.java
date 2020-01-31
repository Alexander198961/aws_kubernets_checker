package api;

import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1EndpointsList;
import io.kubernetes.client.models.V1EndpointSubset;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.*;
import java.util.List;
import io.kubernetes.client.models.V1PodList;


public class ExtendedCoreApi  extends  CoreV1Api {


    public V1PodList extendedListPodForAllNamespaces() throws ApiException
    {
        V1PodList podList = null;
        podList = super.listPodForAllNamespaces(null, null, null, null, null, null, null, null , null);
        return podList;
    }




}