package com.kubernetes.check.strategy;

import com.kubernetes.check.api.ExtendedPodLogs;
import io.kubernetes.client.models.*;
import com.kubernetes.check.ui.DialogInvoker;

public class ContainerStateWaitStrategy implements ContainerStateCheckStrategy {
    private DialogInvoker dialogInvoker = new DialogInvoker();
    private ExtendedPodLogs podLogs = new ExtendedPodLogs();
    @Override
    public void containerCheck(V1ContainerState state , V1Pod pod) {
        V1ContainerStateWaiting waitingState = state.getWaiting();
        if (waitingState.getReason().equals("CrashLoopBackOff") || waitingState.getReason().equals("ImagePullBackOff") || waitingState.getReason().equals("ErrImagePull")) {
            dialogInvoker.showUiMessage(pod.getMetadata().getName(),  pod.getMetadata().getNamespace());
            System.out.println("pod logs===" + pod.getMetadata().getName() + "====  " + podLogs.getLog(pod));
        }
    }
}
