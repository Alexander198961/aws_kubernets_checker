package strategies;
import io.kubernetes.client.models.*;
import ui.DialogInvoker;

public class ContainerStateTermitationStrategy implements ContainerStateCheckStrategy {
    private DialogInvoker dialogInvoker = new DialogInvoker();
    @Override
    public void containerCheck(V1ContainerState state, V1Pod pod) {
        V1ContainerStateTerminated terminatedState = state.getTerminated();
        String message = terminatedState.getMessage();
        if (message != null || terminatedState.getExitCode() > 0 || terminatedState.getReason() == null) {
            dialogInvoker.showUiMessage(pod.getMetadata().getName() , pod.getMetadata().getNamespace());
        }
        System.out.println("pod name=" + pod.getMetadata().getName());
    }
}
