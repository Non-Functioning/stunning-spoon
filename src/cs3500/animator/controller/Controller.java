package cs3500.animator.controller;

import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.SimpleAnimationModel;
import cs3500.animator.view.ViewInterface;

public class Controller implements IController {
  private SimpleAnimationModel model;
  private SimpleAnimationModel subsetModel;
  private ViewInterface view;

  public Controller(SimpleAnimationModel m, ViewInterface v) {
    model = m;
    view = v;
    subsetModel = new SimpleAnimation();
    view.setListener(this);
  }

  @Override
  public void action(String e) {
    switch (e) {
      case "start":
        view.startAnimation();
        break;
      case "restart":
        view.restartAnimation();
        break;
      case "increase tempo":
        view.setTempo(view.getTempo() + 1);
        break;
      case "decrease tempo":
        view.setTempo(view.getTempo() - 1);
        break;
      case "pause":
        view.pauseAnimation();
        break;
      case "loop":
        view.loopAnimation();
        break;
      case "add shape to subset":
        break;
      default:
        break;
    }
  }
}
