package com.kubernetes.check.api;
import io.kubernetes.client.PodLogs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import io.kubernetes.client.models.V1Pod;
import org.apache.commons.io.IOUtils;

public class ExtendedPodLogs extends  PodLogs {

    public String readContainerLog(V1Pod pod)
    {
        String podLog = "";
        try (InputStream inputStream = super.streamNamespacedPodLog(pod.getMetadata().getNamespace(),pod.getMetadata().getName(),null, 200, 70, true) )
        {
            podLog = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

        }
        catch (Exception ex)
        {
            System.out.println(ex.getStackTrace());
        }
        return podLog;
    }
}
