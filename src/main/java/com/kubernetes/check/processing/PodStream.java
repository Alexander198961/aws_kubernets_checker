package com.kubernetes.check.processing;

import com.kubernetes.check.api.ExtendedCoreApi;

import java.util.stream.Stream;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1Pod;

public class PodStream {

    public Stream<V1Pod> getAllPodsAsStream() throws ApiException
    {
        CoreV1Api coreV1Api = new CoreV1Api();
        return coreV1Api.listPodForAllNamespaces(null, null, null, null, null, null, null, null , null).getItems().stream();
    }
}
