package cs3500.animator.view;

import java.awt.*;

import javax.swing.*;

import cs3500.animator.model.SimpleAnimationModel;

public class InteractiveView extends AbstractVisualView {
  private JPanel buttonPane;
  private JButton start;
  private JButton pause;
  private JButton resume;
  private JButton restart;
  private JButton loopEn;
  private JButton upTempo;
  private JButton downTempo;

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
    getContentPane().add(horizonScroll, BorderLayout.SOUTH);
    JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL);
    getContentPane().add(verticalScroll, BorderLayout.EAST);

    buttonPane = new JPanel();
    buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
    buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

    start = new JButton("Start");
    //start.addActionListener(???);
    buttonPane.add(start);
    pause = new JButton("Pause");
    //pause.addActionListener(???);
    buttonPane.add(pause);
    resume = new JButton("Resume");
    //resume.addActionListener(???);
    buttonPane.add(resume);
    restart = new JButton("Restart");
    //restart.addActionListener(???);
    buttonPane.add(restart);

    add(buttonPane, BorderLayout.NORTH); 

    animationPeriod = (long) (timeline.size()) * 100;
    startVisual();

    pack();
    setVisible(true);
  }
}
