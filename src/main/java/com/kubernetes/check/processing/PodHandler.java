package com.kubernetes.check.processing;

import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.*;
import com.kubernetes.check.strategy.ContainerStateCheckStrategy;
import com.kubernetes.check.strategy.ContainerStateTerminationStrategy;
import com.kubernetes.check.strategy.ContainerStateWaitStrategy;


public class PodHandler {
    private  ContainerStateCheckStrategy strategy;
    public void processPodWithContainerStatusNull(V1Pod pod)
    {
        System.out.println("Container status null=="+ pod.getMetadata().getName());
    }

    public void processPodWithNotNullContainerStatus(V1Pod pod)
    {
        pod.getStatus().getContainerStatuses().forEach(status -> {
            if (status.getState().getTerminated() != null) {
                strategy= new ContainerStateTerminationStrategy();
                strategy.containerCheck(status.getState(), pod);
            }
            else if (status.getState().getWaiting() != null) {
                strategy= new ContainerStateWaitStrategy();
                strategy.containerCheck(status.getState(), pod);
            }


        });
    }
}
