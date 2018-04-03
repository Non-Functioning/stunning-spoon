package cs3500.animator.view;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimatedShape;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimationModel;

/**
 * This class represents a textual view of an animation.
 * It takes in and reads a SimpleAnimation model and using the information
 * from a model it prints out a list of shapes, animations, and characteristics
 * of each.
 */
public class TextView extends AbstractView {
  private String viewText;

  /**
   * Constructor for a text view.
   *
   * @param animationModel The model to present in a text view.
   * @param tempo          Tempo, representing the desired ticks per second.
   * @param outputFile     The output text file's name.
   */
  public TextView(SimpleAnimationModel animationModel, double tempo, String outputFile) {
    super(animationModel, tempo);
    viewText = printAnimation();

    try (PrintWriter out = new PrintWriter(outputFile)) {
      out.println(viewText);
    } catch (FileNotFoundException e) {
      // Nothing here
    }
  }

  /**
   * Constructor for a text view, without an output file.
   *
   * @param animationModel The model to present in a text view.
   * @param tempo          Tempo, representing the desired ticks per second.
   */
  public TextView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    viewText = printAnimation();

    System.out.print(viewText);
  }

  /**
   * Returns the String representation of the entire animation including
   * all shapes and Animations.
   *
   * @return animation as a String
   */
  private String printAnimation() {
    StringBuilder shapesString = new StringBuilder("Shapes:\n");
    StringBuilder animationString = new StringBuilder();
    for (int i = 0; i < shapes.size(); i++) {
      shapesString.append(shapeToString(i));
      shapesString.append("\n\n");
    }

    for (int i = 0; i < animations.size(); i++) {
      animationString.append(animationToString(i));
      if (i != (animations.size() - 1)) {
        animationString.append("\n");
      }
    }
    return shapesString.append(animationString).toString();
  }

  /**
   * This method converts a shape into a String representation.
   *
   * @param index index of a shape
   * @return shape as a String
   */
  private String shapeToString(int index) {
    AnimatedShape currentShape = shapes.get(index);
    return "Name: " + currentShape.getShapeName() + "\nType: " + currentShape.getShapeType()
            .getStringValue() + "\n"
            + currentShape.getRefPoint().getStringValue() + ": " + currentShape.getInitialPosition()
            .toString() + ", "
            + currentShape.sizeParamsToString(currentShape.getInitialSize()) + ", Color: "
            + currentShape.getInitialColor().toString()
            + "\nAppears at t=" + String.valueOf(currentShape.getAppearTime() / tempo)
            + "s\nDisappears at t="
            + String.valueOf(currentShape.getDisappearTime() / tempo) + "s";
  }

  /**
   * This method converts an animation into s String representation.
   *
   * @param index index of an animation
   * @return animation as a String
   */
  private String animationToString(int index) {
    Animations currentAnimations = animations.get(index);

    StringBuilder newString = new StringBuilder("Shape " + currentAnimations.getChangedShape()
            .getShapeName());
    switch (currentAnimations.getType()) {
      case MOVE:
        newString.append(" moves from ");
        newString.append(currentAnimations.getPosition1().toString());
        newString.append(" to ");
        newString.append(currentAnimations.getPosition2().toString());
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append("s to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        newString.append("s");
        break;
      case CHANGECOLOR:
        newString.append(" changes color from ");
        newString.append(currentAnimations.getColor1().toString());
        newString.append(" to ");
        newString.append(currentAnimations.getColor2().toString());
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append("s to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        newString.append("s");
        break;
      case CHANGESIZE:
        newString.append(" scales from ");
        newString.append(currentAnimations.getChangedShape().sizeParamsToString(currentAnimations
                .getSizeParams1()));
        newString.append(" to ");
        newString.append(currentAnimations.getChangedShape().sizeParamsToString(currentAnimations
                .getSizeParams2()));
        newString.append(" from t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append("s to t=");
        newString.append(currentAnimations.getTime2() / tempo);
        newString.append("s");
        break;
      case APPEAR:
        newString.append(" appears at t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append("s");
        break;
      default:
        newString.append(" disappears at t=");
        newString.append(currentAnimations.getTime1() / tempo);
        newString.append("s");
        break;
    }
    return newString.toString();
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
   * This method is used by both the Visual and Interactive views. It plays an
   * animation starting from the given tick. This method schedules all the tasks required
   * each tick to draw an animation. The method creates a task based on the timeline and
   * the animations within the timeline.
   * @param startTime   starting tick
   */
  @Override
  public void startAnimation(int startTime) {
    throw new UnsupportedOperationException("This view does not support visual views.");
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
   * This method is only used by the Interactive view. It toggles the pause function of the
   * animation. When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    throw new UnsupportedOperationException("This view does not support animation pausing.");
  }

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   * @param shapeName    action by user that includes shape
   * @param subset  new subset model
   */
  @Override
  public void addToSubset(String shapeName, SimpleAnimationModel subset) {
    throw new UnsupportedOperationException("This view does not support subset animations.");
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
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view the state whether the animation can be played
   * or paused
   */
  @Override
  public void togglePlayOrPause() {
    throw new UnsupportedOperationException("This view does not support pausing.");
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
