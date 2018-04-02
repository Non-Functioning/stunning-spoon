package cs3500.animator.view;

import cs3500.animator.controller.IController;

/**
 * This is the interface for the view. All the views are created
 * inside their own constructors. Because of this, there are no public
 * methods available to the user to modify the view. To modify  the view
 * you must first modify the model and then recreate the view.
 */
public interface ViewInterface {

  void setListener(IController listener);

  void setTempo(double newTempo);

  double getTempo();

  void setLooped(boolean is);

  void loopAnimation();

  void startVisual();
}
