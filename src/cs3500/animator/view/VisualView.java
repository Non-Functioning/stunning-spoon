package cs3500.animator.view;

import java.awt.event.ActionEvent;

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
    startAnimation(0);
    frame.pack();
    frame.setVisible(true);
  }

  @Override
  public void updateTempo(double newTempo) {
    throw new UnsupportedOperationException("This view does not support tempo updates.");
  }

  @Override
  public void togglePlayOrPause() {
    throw new UnsupportedOperationException("This view does not support pausing.");
  }

  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }

  @Override
  public void addToSubset(ActionEvent arg0, SimpleAnimationModel subset) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  @Override
  public void loopAnimation() {
    throw new UnsupportedOperationException("This view does not support animation looping.");
  }

  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("This view does not support animation restart.");
  }
}