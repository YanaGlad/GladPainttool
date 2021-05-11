import javax.swing.*;
import java.awt.event.ActionEvent;

class ExitAction extends AbstractAction {

    ExitAction() {
        putValue(NAME, "Exit");
    }

    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}