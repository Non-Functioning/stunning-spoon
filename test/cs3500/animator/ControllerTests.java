package cs3500.animator;

import org.junit.Test;

import java.awt.event.ActionEvent;

import cs3500.animator.controller.Controller;
import cs3500.animator.controller.IController;
import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.SimpleAnimationModel;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.ViewInterface;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


public class ControllerTests {

  /**
   * Tests the button links.
   */
  @Test
  public void testAction() {
    boolean result = true;

    SimpleAnimationModel model = new SimpleAnimation();
    InteractiveView view = new InteractiveView(model, 20);
    IController controller = new Controller(model, view);
    ActionEvent start = new ActionEvent(this, 0, "start");
    ActionEvent restart = new ActionEvent(this, 0, "restart");
    ActionEvent increaseTempo = new ActionEvent(this, 0, "increase tempo");
    ActionEvent decreaseTempo = new ActionEvent(this, 0, "decrease tempo");
    ActionEvent loop = new ActionEvent(this, 0, "loop");
    ActionEvent addShape = new ActionEvent(this, 0, "add shape to subset");
    ActionEvent viewSubset = new ActionEvent(this, 0, "view subset");
    ActionEvent playSubset = new ActionEvent(this, 0, "play subset");
    ActionEvent svgSubset = new ActionEvent(this, 0, "SVG subset");

    try {
      controller.action(start);
      controller.action(restart);
      controller.action(increaseTempo);
      controller.action(decreaseTempo);
      controller.action(loop);
      controller.action(addShape);
      controller.action(viewSubset);
      controller.action(playSubset);
      controller.action(svgSubset);
    } catch (IllegalArgumentException e) {
      result = false;
    } catch (Throwable t) {
      //do nothing since other exceptions are OK
    }
    assertEquals(true, result);
  }

  /**
   * Tests the exception for invalid button info.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testActionException() {
    SimpleAnimationModel model = new SimpleAnimation();

    ViewInterface view = new InteractiveView(model, 20);
    IController controller = new Controller(model, view);
    ActionEvent start = new ActionEvent(this, 0, "haha");
    controller.action(start);
  }
}

