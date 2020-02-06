package com.kubernetes.check.strategy;
import io.kubernetes.client.models.*;
import com.kubernetes.check.ui.UiWindow;

public class ContainerStateTerminationStrategy implements ContainerStateCheckStrategy {
    private UiWindow uiWindow = new UiWindow();
    @Override
    public void containerCheck(V1ContainerState state, V1Pod pod) {
        V1ContainerStateTerminated terminatedState = state.getTerminated();
        String message = terminatedState.getMessage();
        if (message != null || terminatedState.getExitCode() > 0 || terminatedState.getReason() == null) {
            System.out.println("Terminated pod ===" + pod.getMetadata().getName() );
        }
        System.out.println("pod name=" + pod.getMetadata().getName());
    }
}
