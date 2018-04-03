package cs3500.animator.view;

import java.awt.event.ActionEvent;

import cs3500.animator.controller.IController;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * This is the interface for the view. All the views are created
 * inside their own constructors. The only view that can be edited/interacted
 * with is the Interactive view. The methods here can be used to modify the
 * Interative view. In all other views, these methods are Unsupported.
 */
public interface ViewInterface {

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   * @param listener  controller
   */
  void setListener(IController listener);

  /**
   * 
   * @param newTempo
   */
  void setTempo(double newTempo);

  void updateTempo(double newTempo);

  double getTempo();

  void setLooped(boolean is);

  void loopAnimation();

  void restartAnimation();

  void pauseAnimation();

  void togglePlayOrPause();

  void addToSubset(ActionEvent arg0, SimpleAnimationModel subset);

  void playSubset(SimpleAnimationModel model, int subsetStart);

  void svgSubset(SimpleAnimationModel model, String fileName);

  void startAnimation(int startTime);
}
