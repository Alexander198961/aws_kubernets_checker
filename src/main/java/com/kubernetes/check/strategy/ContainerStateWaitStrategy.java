package com.kubernetes.check.strategy;

import com.kubernetes.check.api.ExtendedPodLogs;
import com.kubernetes.check.ui.UiWindow;
import io.kubernetes.client.models.*;

public class ContainerStateWaitStrategy implements ContainerStateCheckStrategy {
    private UiWindow uiWindow = new UiWindow();
    private ExtendedPodLogs podLogs = new ExtendedPodLogs();
    @Override
    public void containerCheck(V1ContainerState state , V1Pod pod) {
        V1ContainerStateWaiting waitingState = state.getWaiting();
        if (waitingState.getReason().equals("CrashLoopBackOff") || waitingState.getReason().equals("ImagePullBackOff") || waitingState.getReason().equals("ErrImagePull")) {
            System.out.println("pod logs===" + pod.getMetadata().getName() + "====  " + podLogs.readContainerLog(pod));
        }
    }
}
