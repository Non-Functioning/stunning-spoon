package cs3500.animator.view;

import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

import java.util.List;

import javax.swing.*;

/**
 * This class implements the view interface and holds the values that are used
 * and shared by all three view types. This class also extends the JFrame class
 * which is utilized by the visual view.
 */
public abstract class AbstractView extends JFrame implements ViewInterface {
  protected SimpleAnimationModel model;
  protected List<AnimatedShape> shapes;
  protected List<Animations> animations;
  protected List<List<Animations>> timeline;
  protected double tempo;

  /**
   * Constructor for an abstract view.
   * @param animationModel The model to transfer to a view.
   * @param tempo Speed of the animation, as ticks per second.
   */
  public AbstractView(SimpleAnimationModel animationModel, double tempo) {
    model = animationModel;
    this.shapes = model.getShapes();
    this.animations = model.getAnimations();
    this.timeline = model.getTimeline();

    if (tempo > 0) {
      this.tempo = tempo;
    } else {
      throw new IllegalArgumentException("The tempo cannot be equal to or fall below 0");
    }
  }
}
