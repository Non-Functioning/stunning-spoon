package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cs3500.animator.controller.IController;
import cs3500.animator.model.SimpleAnimationModel;

public class InteractiveView extends AbstractVisualView {
  private JPanel buttonPane;
  private JPanel mainButtonPanel;
  private JPanel dropdownPanel;
  private JButton start;
  private JButton restart;
  private JButton upTempo;
  private JButton downTempo;
  private JRadioButton pause;
  private JRadioButton loop;
  private JLabel tempoDisplay;
  private JLabel dropdownDisplay;
  private JComboBox<String> dropdown;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public InteractiveView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);

    setSize(500, 500);
    setLocation(200, 25);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(true);
    setMinimumSize(new Dimension(500, 500));
    getContentPane().setLayout(new BorderLayout());

    JScrollBar horizonScroll = new JScrollBar(JScrollBar.HORIZONTAL);
    add(horizonScroll, BorderLayout.SOUTH);
    JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL);
    add(verticalScroll, BorderLayout.EAST);

    mainButtonPanel = new JPanel();
    mainButtonPanel.setLayout(new FlowLayout());

    buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    start = new JButton("Start");
    start.setActionCommand("start");
    buttonPane.add(start);
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    buttonPane.add(restart);
    upTempo = new JButton("Increase Tempo");
    upTempo.setActionCommand("increase tempo");
    buttonPane.add(upTempo);
    downTempo = new JButton("Decrease Tempo");
    downTempo.setActionCommand("decrease tempo");
    buttonPane.add(downTempo);

    mainButtonPanel.add(buttonPane, FlowLayout.LEFT);

    tempoDisplay = new JLabel("Tempo: " + tempo);
    mainButtonPanel.add(tempoDisplay, FlowLayout.CENTER);

    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));

    pause = new JRadioButton("Pause");
    pause.setActionCommand("pause");
    radioPanel.add(pause);
    loop = new JRadioButton("Enable Looping");
    loop.setActionCommand("loop");
    radioPanel.add(loop);

    dropdownPanel = new JPanel();
    dropdownPanel.setLayout(new BoxLayout(dropdownPanel, BoxLayout.PAGE_AXIS));
    add(dropdownPanel, BorderLayout.SOUTH);

    dropdownDisplay = new JLabel("Add shapes to new animation");
    dropdownPanel.add(dropdownDisplay);
    String[] shapes = {"Shape A", "Shape B", "Shape C"};
    dropdown = new JComboBox<>();
    dropdown.setActionCommand("add shape to subset");
    for (int i = 0; i < shapes.length; i++) {
      dropdown.addItem(shapes[i]);
    }
    dropdownPanel.add(dropdown);

    mainButtonPanel.add(radioPanel, FlowLayout.RIGHT);
    add(mainButtonPanel, BorderLayout.NORTH);

    pack();
    setVisible(true);
  }



  @Override
  public void setListener(IController listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.action(e.getActionCommand());
      }
    };
    start.addActionListener(listen);
    restart.addActionListener(listen);
    upTempo.addActionListener(listen);
    downTempo.addActionListener(listen);
    pause.addActionListener(listen);
    loop.addActionListener(listen);
    dropdown.addActionListener(listen);
  }
}
