package cs3500.animator.view;

import java.awt.*;

import javax.swing.*;

import cs3500.animator.model.SimpleAnimationModel;

public class InteractiveView extends AbstractVisualView {
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

    animationPeriod = (long) (timeline.size()) * 100;
    startVisual();

    pack();
    setVisible(true);
  }
}
