package cs3500.animator.controller;

import java.awt.event.ActionEvent;

/**
 * This is the interface for the controller. It lists the public
 * method action which can be used to interact with the Interactive
 * view.
 */
public interface IController {
  /**
   * This is the action class that reads the user's input or actions with the
   * interactive view. Depending on the action, it calls upon the view to respond
   * accordingly to the action. Some actions included are play/pause, restart, and
   * increase/decrease tempo.
   * @param e   user's action
   */
  void action(ActionEvent e);
}
