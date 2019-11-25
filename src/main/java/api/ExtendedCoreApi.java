package api;

import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1EndpointsList;
import io.kubernetes.client.models.V1EndpointSubset;
import io.kubernetes.client.models.V1Endpoints;
import io.kubernetes.client.*;
import java.util.List;
import io.kubernetes.client.models.V1PodList;

public class ExtendedCoreApi  extends  CoreV1Api {


    public V1PodList extendedListPodForAllNamespaces() throws  Exception
    {
        V1PodList podlist = super.listPodForAllNamespaces(null, null, null, null, null, null, null, null , null);
        if (podlist.getItems().isEmpty() == true )
            throw new Exception("Should happened");
        return podlist;
    }




}