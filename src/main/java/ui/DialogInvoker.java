package ui;

import javax.swing.*;

public class DialogInvoker {

    public void showUiMessage(String podName, String namespace)
    {
        JOptionPane.showMessageDialog(null, podName  , namespace, 0);
    }
}
