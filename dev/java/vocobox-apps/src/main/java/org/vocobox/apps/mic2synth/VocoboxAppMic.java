package org.vocobox.apps.mic2synth;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jzy3d.ui.LookAndFeel;
import org.vocobox.ui.EzLayout;
import org.vocobox.ui.EzLayout.Prop;

public class VocoboxAppMic extends JFrame{
    private static final long serialVersionUID = -1028611255347296822L;
    VocoboxPanelsMic panels;
    
    public VocoboxAppMic(){
        LookAndFeel.apply();
        windowExitListener(this);
        show();
        setVisible(true);
    }
    
    public void layout(VocoboxPanelsMic panels) {
        JPanel mainPanel = new JPanel();

        String lines = "[20px][250px][20px][900px]";
        String columns = "[200px][500px,grow]";
        EzLayout layout = new EzLayout(mainPanel, lines, columns);
        addInputRow(0, panels, layout);
        addSynthRow(2, panels, layout);
        //JScrollPane scrollPanel = new JScrollPane();
        //scrollPanel.add(mainPanel);
        add(mainPanel);
        pack();
    }
    
    public void addInputRow(int id, VocoboxPanelsMic panels, EzLayout layout) {
        layout.add(id, 0, new JLabel("Input"));
        layout.add(id+1, 1, panels.inputControl, Prop.GROW);
    }

    public void addSynthRow(int id, VocoboxPanelsMic panels, EzLayout layout) {
        layout.add(id, 0, new JLabel("Synthetizer"));
        if(panels.synthControl!= null)
            layout.add(id+1, 0, panels.synthControl);
        layout.add(id+1, 1, panels.synthCharts, Prop.GROW);
    }

    public static void windowExitListener(final JFrame frame) {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
    }
}
