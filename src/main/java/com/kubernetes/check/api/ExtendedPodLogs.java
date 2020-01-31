package com.kubernetes.check.api;
import io.kubernetes.client.PodLogs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import io.kubernetes.client.models.V1Pod;
import org.apache.commons.io.IOUtils;

public class ExtendedPodLogs extends  PodLogs {

    public String getLog(V1Pod pod)
    {
        InputStream inputStream = null;
        String podLog = "";
        try {
            inputStream = super.streamNamespacedPodLog(pod.getMetadata().getNamespace(),pod.getMetadata().getName(),null, 200, 70, true);
            if(inputStream!= null)
                podLog = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        }
        catch(io.kubernetes.client.ApiException exception)
        {
            System.out.println("Code of exception is ="+exception.getCode());
            return "";
        }
        catch (IOException ioException)
        {
            System.out.println("Exception ===="+ioException.getCause());
            return "";
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException ex)
            {
                System.out.println(ex.getCause());
            }
        }
        return podLog;
    }
}
