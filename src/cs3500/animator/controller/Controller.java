package cs3500.animator.controller;

import cs3500.animator.model.SimpleAnimationModel;
import cs3500.animator.view.ViewInterface;

public class Controller implements IController {
  private SimpleAnimationModel model;
  private ViewInterface view;

  public Controller(SimpleAnimationModel m, ViewInterface v) {
    model = m;
    view = v;
    view.setListener(this);
  }

  @Override
  public void action(String e) {
    switch (e) {
      case "start":
        view.startVisual();
        break;
      case "restart":
        view.startVisual();
        break;
      case "increase tempo":
        view.setTempo(view.getTempo() + 1);
        break;
      case "decrease tempo":
        view.setTempo(view.getTempo() - 1);
        break;
      case "pause":
        break;
      case "loop":
        break;
    }
  }
}
