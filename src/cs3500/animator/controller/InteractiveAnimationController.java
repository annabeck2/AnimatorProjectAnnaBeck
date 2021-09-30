package cs3500.animator.controller;

import cs3500.animator.controller.command.*;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.IViewEventListener;
import cs3500.animator.view.InteractiveAnimationView;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.Timer;

/**
 * <p> A controller for the interactive animation view.</p>
 */
public class InteractiveAnimationController implements IAnimatorController, IViewEventListener {

  protected final IAnimatorModel<IModelShape> model;
  protected final InteractiveAnimationView view;
  protected final Map<ShapeType, Supplier<IDrawCommand>> knownCommands;
  protected double tempo; // in ticks per second
  private boolean loopEnabled;
  private boolean discreteEnabled;
  private final Timer timer;
  private final double speedIncrement = 5; // ticks per second

  /**
   * <p>Constructs a {@code InteractiveAnimationController} to use with a pre-existing model and
   * visual view.</p>
   *
   * @param model the model of the animation
   * @param view  the view for the animation
   * @param tempo the speed of the animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   */
  public InteractiveAnimationController(IAnimatorModel<IModelShape> model,
      InteractiveAnimationView view, double tempo) throws IllegalArgumentException {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    this.tempo = tempo;
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.loopEnabled = false;
    this.discreteEnabled = false;
    this.knownCommands = new HashMap<>();
    this.putKnownCommands();
    view.addViewEventListener(this);

    // initialize the timer
    int delay = (int) (1000.0 / this.tempo); // in millis
    this.timer = new Timer(delay, event -> {
      if (model.hasNextFrame()) {
        List<IModelShape> shapesToRender;

        if (this.discreteEnabled) {
          shapesToRender = model.getNextKeyFrame();
        } else {
          shapesToRender = model.getNextFrame();
        }

        for (IModelShape shape : shapesToRender) {
          // get a command object from the supplier from the map
          IDrawCommand command = knownCommands.get(shape.getType()).get();
          command.execute(
              (int) shape.getX(),
              (int) shape.getY(),
              (int) shape.getWidth(),
              (int) shape.getHeight(),
              shape.getColor());
        }

        view.refresh();

      } else {
        // if the end of the animation was reached
        if (this.loopEnabled) {
          model.reset();
        }
      }
    });

  }

  /**
   * <p>Put all known commands in the map of commands.</p>
   */
  private void putKnownCommands() {
    this.knownCommands.put(ShapeType.ELLIPSE, () -> new DrawEllipse(this.view));
    this.knownCommands.put(ShapeType.RECTANGLE, () -> new DrawRectangle(this.view));
    this.knownCommands.put(ShapeType.PLUS, () -> new DrawPlus(this.view));
  }

  @Override
  public void start() throws IOException, UnsupportedOperationException {
    view.render();
    timer.setRepeats(true);
  }

  @Override
  public void playbackToggled(boolean play) {
    if (play) {
      timer.start();
    } else {
      timer.stop();
    }
  }

  @Override
  public void playbackRestarted() {
    this.model.reset();
  }

  @Override
  public void speedIncreased() {
    this.tempo += this.speedIncrement;
    this.updateTimerDelay();
  }

  @Override
  public void speedDecreased() {
    double newTempo = this.tempo - this.speedIncrement;
    if (newTempo > 0) {
      this.tempo = newTempo;
      this.updateTimerDelay();
    }
  }

  /**
   * <p>Update the delay between frames of the animation, according to the tempo.</p>
   */
  private void updateTimerDelay() {
    int delay = (int) (1000.0 / this.tempo);
    this.timer.setDelay(delay);
  }

  @Override
  public void loopingToggled(boolean enabled) {
    this.loopEnabled = enabled;
  }

  @Override
  public void discreteToggled(boolean enabled) {
    this.discreteEnabled = enabled;
  }

  @Override
  public void outlineToggled(boolean enabled) {
  }
}
