package cs3500.animator.view;

import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.*;

import cs3500.animator.controller.IController;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * This class represents a visual view. It creates a window and
 * draws an animation in the window based on a given model.
 */
public class VisualView extends AbstractVisualView {
  /**
   * Constructor for a visual view.
   *
   * @param animationModel Model to represent in a visual view.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public VisualView(SimpleAnimationModel animationModel, double tempo) {
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
    startVisual();

    pack();
    setVisible(true);
  }

  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }
}