package cs3500.animator.view;

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
    startAnimation();
  }

  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }
}