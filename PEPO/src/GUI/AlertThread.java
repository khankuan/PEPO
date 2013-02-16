package GUI;

import Logic.LogicController;
import Logic.Memo.Task;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * @author kuan
 */
public class AlertThread extends Thread {

    public void run() {
        ArrayList<AlertPopup> popuparr = new ArrayList<>();
        GregorianCalendar firstNow = new GregorianCalendar();
        firstNow.setTime(new Date(System.currentTimeMillis()));
        try {
            Thread.sleep((60 - firstNow.get(GregorianCalendar.SECOND)) * 1000);
        } catch (InterruptedException ex) {
            this.yield();
            return;
        }
        while (true) {
            try {
                final AlertPopup notification = new AlertPopup(null, false);
                popuparr.add(notification);
                ArrayList<Task> tasks = LogicController.getCurrentAlerts();
                if (!tasks.isEmpty()) {
                    notification.setDefaultCloseOperation(AlertPopup.DISPOSE_ON_CLOSE);
                    notification.addWindowListener(new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                            notification.dispose();
                        }
                    });
                    notification.pack();
                    notification.setVisible(true);
                    notification.update(tasks);
                }
                Thread.sleep(60000);

            } catch (InterruptedException ex) {
                for (int i = 0; i < popuparr.size(); i++) {
                    popuparr.get(i).dispose();
                }
                this.yield();
                return;
            }
        }
    }
}