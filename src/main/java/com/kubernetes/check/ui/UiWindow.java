package com.kubernetes.check.ui;

import javax.swing.*;

public class UiWindow {

    public void showUiMessage(String podName, String namespace)
    {
        JOptionPane.showMessageDialog(null, podName  , namespace, 0);
    }
}
