package com.kubernetes.check.strategy;
import io.kubernetes.client.models.*;

public interface ContainerStateCheckStrategy {
    public void containerCheck(V1ContainerState state, V1Pod pod);
}
