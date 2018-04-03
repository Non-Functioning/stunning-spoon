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

  /**
   * This method is used only by the Interactive view. It assigns the tempo to the new
   * given value, updates the the tempo shown in the view, and continues running or pausing
   * the animation at the current tick.
   * @param newTempo  new tempo
   */
  @Override
  public void updateTempo(double newTempo) {
    throw new UnsupportedOperationException("This view does not support tempo updates.");
  }

  /**
   * This method is only used by the Interactive view. It toggles the pause function of the
   * animation. When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("This view does not support pausing.");
  }

  /**
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view the state whether the animation can be played
   * or paused
   */
  @Override
  public void togglePlayOrPause() {
    throw new UnsupportedOperationException("This view does not support pausing.");
  }

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   * @param listener  controller
   */
  @Override
  public void setListener(IController listener) {
    throw new UnsupportedOperationException("This view does not support listeners.");
  }

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   * @param arg0    action by user that includes shape
   * @param subset  new subset model
   */
  @Override
  public void addToSubset(ActionEvent arg0, SimpleAnimationModel subset) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  /**
   * This method is only used by the Interactive view. It toggles the loop function of the
   * animation and continues playing or pausing the animation.
   */
  @Override
  public void loopAnimation() {
    throw new UnsupportedOperationException("This view does not support animation looping.");
  }

  /**
   * This method is only used by the Interactive view and restarts the animation from the
   * beginning.
   */
  @Override
  public void restartAnimation() {
    throw new UnsupportedOperationException("This view does not support animation restart.");
  }

  /**
   * This method is only used by the Interactive view. It plays the subset animation
   * from the given starting tick in the current window.
   * @param model       subset model
   * @param subsetStart starting tick
   */
  @Override
  public void playSubset(SimpleAnimationModel model, int subsetStart) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }

  @Override
  public void svgSubset(SimpleAnimationModel model, String fileName) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
  }
}