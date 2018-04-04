package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import cs3500.animator.controller.IController;
import cs3500.animator.model.Animations;
import cs3500.animator.model.SimpleAnimation;
import cs3500.animator.model.SimpleAnimationModel;

public class InteractiveView extends AbstractVisualView {
  private JPanel buttonPane;
  private JPanel mainButtonPanel;
  private JPanel subsetPanel;
  private JButton start;
  private JButton restart;
  private JButton upTempo;
  private JButton downTempo;
  private JRadioButton loop;
  private JLabel tempoDisplay;
  private JLabel dropdownDisplay;
  private JComboBox<String> dropdown;
  private JButton subsetList;
  private JButton playSubset;
  private JButton svgSubset;
  private JButton svgAnim;

  protected int subsetTick;
  protected List<List<Animations>> subsetTimeline;
  private SimpleAnimationModel subsetModel;

  /**
   * Constructor for an abstract view.
   *
   * @param animationModel The model to transfer to a view.
   * @param tempo          Speed of the animation, as ticks per second.
   */
  public InteractiveView(SimpleAnimationModel animationModel, double tempo) {
    super(animationModel, tempo);
    subsetTick = 0;
    subsetTimeline = new ArrayList<>();
    subsetModel = new SimpleAnimation();

    mainButtonPanel = new JPanel();
    mainButtonPanel.setLayout(new FlowLayout());
    mainButtonPanel.setBorder(BorderFactory.createBevelBorder(1));

    buttonPane = new JPanel();
    buttonPane.setLayout(new FlowLayout());
    buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    start = new JButton("Play");
    start.setActionCommand("start");
    buttonPane.add(start);
    restart = new JButton("Restart");
    restart.setActionCommand("restart");
    buttonPane.add(restart);
    upTempo = new JButton("Increase Tempo");
    upTempo.setActionCommand("increase tempo");
    buttonPane.add(upTempo);
    downTempo = new JButton("Decrease Tempo");
    downTempo.setActionCommand("decrease tempo");
    buttonPane.add(downTempo);

    mainButtonPanel.add(buttonPane, FlowLayout.LEFT);

    tempoDisplay = new JLabel("Tempo: " + tempo);
    mainButtonPanel.add(tempoDisplay, FlowLayout.CENTER);

    JPanel radioPanel = new JPanel();
    radioPanel.setLayout(new FlowLayout());

    loop = new JRadioButton("Enable Looping");
    loop.setActionCommand("loop");
    radioPanel.add(loop);
    mainButtonPanel.add(radioPanel, FlowLayout.RIGHT);

    subsetPanel = new JPanel();
    subsetPanel.setLayout(new FlowLayout());
    subsetPanel.setBorder(BorderFactory.createBevelBorder(1));

    dropdownDisplay = new JLabel("Add shapes to new animation");
    subsetPanel.add(dropdownDisplay);
    dropdown = new JComboBox<>();
    dropdown.setActionCommand("add shape to subset");
    createShapeDropdown();
    subsetPanel.add(dropdown);
    subsetList = new JButton("Subset Shape List");
    subsetList.setActionCommand("view subset");
    subsetPanel.add(subsetList);
    playSubset = new JButton("Play Subset");
    playSubset.setActionCommand("play subset");
    subsetPanel.add(playSubset);
    svgSubset = new JButton("Export Subset to SVG");
    svgSubset.setActionCommand("SVG subset");
    subsetPanel.add(svgSubset);
    svgAnim = new JButton("Export Animation to SVG");
    svgAnim.setActionCommand("SVG animation");
    subsetPanel.add(svgAnim);

    mainPanel.add(mainButtonPanel, BorderLayout.NORTH);
    mainPanel.add(subsetPanel, BorderLayout.SOUTH);

    frame.pack();
    //mainButtonPanel.setVisible(true);
    //subsetPanel.setVisible(true);
    frame.validate();
    frame.setVisible(true);
  }

  private void createShapeDropdown() {
    for (int i = 0; i < shapes.size(); i++) {
      dropdown.addItem("Shape " + shapes.get(i).getShapeName() + " ("
              + shapes.get(i).getShapeType() + ")");
    }
  }

  /**
   * This method is only used by the Interactive view. It toggles the play/pause
   * function and updates the view the state whether the animation can be played
   * or paused
   */
  public void togglePlayOrPause() {
    isPaused = !isPaused;
    timer.cancel();
    timer = new Timer();
    if (!isPaused) {
      startAnimation(currTick);
      start.setText("Pause");
    }
    else {
      pauseAnimation();
      start.setText("Play");
    }
  }

  /**
   * This method is used only by the Interactive view. It assigns the tempo to the new
   * given value, updates the the tempo shown in the view, and continues running or pausing
   * the animation at the current tick.
   *
   * @param newTempo new tempo
   */
  @Override
  public void updateTempo(double newTempo) {
    tempo = newTempo;
    tempoDisplay.setText("Tempo: \n" + tempo);
    timer.cancel();
    timer = new Timer();
    if (!isPaused) {
      startAnimation(currTick);
    }
  }

  /**
   * This method is only used by the Interactive view. It toggles the loop function of the
   * animation and continues playing or pausing the animation.
   */
  @Override
  public void loopAnimation() {
    isLooped = !isLooped;
    timer.cancel();
    timer = new Timer();
    if (!isPaused) {
      startAnimation(currTick);
    }
  }

  /**
   * This method is only used by the Interactive view and restarts the animation from the
   * beginning.
   */
  @Override
  public void restartAnimation() {
    timer.cancel();
    timer = new Timer();
    start.setText("Pause");
    isPaused = false;
    startAnimation(0);
  }

  /**
   * This method is only used by the Interactive view. It toggles the pause function of the
   * animation. When unpausing/playing, the animation continues playing from the tick it was
   * paused at.
   */
  @Override
  public void pauseAnimation() {
    TimerTask task;
    final int finalTick = currTick;
    List<Animations> animationTime = timeline.get(finalTick);
    task = new TimerTask() {
      @Override
      public void run() {
        initializeParams();
        addShapeParamsAtTimeT(animationTime, finalTick);
        mainPanel.repaint();
      }
    };
    scheduleTimerTasks(task, currTick);
  }

  @Override
  public void svgAnimation(String fileName) {
    ViewInterface vi = new SVGView(model, this.tempo, fileName);
  }

  /**
   * This method is only used by the Interactive view. It adds a shape chosen by the user
   * to the new subset model.
   *
   * @param item   added shape's name
   */
  @Override
  public void addToSubset(String item) {
    String shapeName = item.split(" ")[1];
    if (subsetModel.getShapeByName(shapeName) == null) {
      subsetModel.copyShape(model.getShapeByName(shapeName));
      dropdownDisplay.setText("Added to Subset: " + shapeName);
      for (int i = 0; i < animations.size(); i++) {
        if (animations.get(i).getChangedShape().getShapeName().equals(shapeName)) {
          subsetModel.copyAnimation(animations.get(i));
        }
      }
    } else {
      subsetModel.removeShapeByName(shapeName);
      dropdownDisplay.setText("Removed from Subset: " + shapeName);
    }
  }

  /**
   * This method is only used by the Interactive view. It plays the subset animation
   * from the given starting tick in the current window.
   *
   * @param subsetStart starting tick
   */
  @Override
  public void playSubset(int subsetStart) {
    subsetTimeline = subsetModel.getTimeline();
    //subsetTick = subsetStart;

    timer.cancel();
    timer = new Timer();
    TimerTask task;

    for (int i = 0; i < 101; i++) {
      final int FINALI = i;//subsetTick;
      //List<Animations> animationTime = subsetTimeline.get(FINALI);

      task = new TimerTask() {
        @Override
        public void run() {
          initializeParams();
          addShapeParamsAtTimeT(subsetTimeline.get(FINALI), FINALI, subsetTimeline);
          mainPanel.repaint();
          //subsetTick = (subsetTick + 1) % subsetTimeline.size();
        }
      };
      scheduleTimerTasks(task, i);
      /*if ((subsetTick == (subsetTimeline.size() - 1)) & (!isLooped)) {
        subsetTick = (subsetTick + 1) % subsetTimeline.size();
        break;
      }*/
      //subsetTick = (subsetTick + 1) % subsetTimeline.size();
    }
  }

  /**
   * Generates a SVG view based on the subset selected in the interface, saves the file to a user
   * specified location.
   *
   * @param fileName The file name to save the SVG file as.
   */
  @Override
  public void svgSubset(String fileName) {
    ViewInterface vi = new SVGView(subsetModel, this.tempo, fileName);
  }

  @Override
  public void showSubsetList() {
    StringBuilder subsetShapes = new StringBuilder("Shapes currently in the subset: \n");

    for (int i = 0; i < subsetModel.getShapes().size(); i++) {
      subsetShapes.append("   -Shape ");
      subsetShapes.append(subsetModel.getShape(i).getShapeName());
      subsetShapes.append(" (" + subsetModel.getShape(i).getShapeType());
      subsetShapes.append(")\n");
    }
    subsetShapes.append("Select shape in dropdown again to remove it from subset.");
    JOptionPane.showMessageDialog(frame, subsetShapes.toString());
  }

  @Override
  public void createMessageDialog(String dialog) {
    JOptionPane.showMessageDialog(frame, dialog);
  }

  /**
   * This method is used to connect the controller(listener) to the Interactive view
   * so that the user can interact with the view.
   *
   * @param listener controller
   */
  @Override
  public void setListener(IController listener) {
    ActionListener listen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.action(e);
      }
    };
    start.addActionListener(listen);
    restart.addActionListener(listen);
    upTempo.addActionListener(listen);
    downTempo.addActionListener(listen);
    loop.addActionListener(listen);
    dropdown.addActionListener(listen);
    subsetList.addActionListener(listen);
    playSubset.addActionListener(listen);
    svgSubset.addActionListener(listen);
    svgAnim.addActionListener(listen);
  }
}
