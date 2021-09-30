package cs3500.animator.controller;

import cs3500.animator.controller.command.DrawEllipse;
import cs3500.animator.controller.command.DrawPlus;
import cs3500.animator.controller.command.DrawRectangle;
import cs3500.animator.model.shape.ShapeType;

import cs3500.animator.controller.command.IDrawCommand;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;

import cs3500.animator.view.VisualAnimationView;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.Timer;

/**
 * A controller for the easy animator visual view.
 */
public class VisualAnimationController implements IAnimatorController {

  protected final IAnimatorModel<IModelShape> model;
  protected final VisualAnimationView view;
  protected final Map<ShapeType, Supplier<IDrawCommand>> knownCommands;
  protected final double tempo; // in ticks per second

  /**
   * <p>Constructs a {@code VisualAnimationController} to use with a pre-existing model and
   * visual view.</p>
   *
   * @param model the model of the animation
   * @param view  the view for the animation
   * @param tempo the speed of the animation, in ticks per second
   * @throws IllegalArgumentException if tempo is not positive
   */
  public VisualAnimationController(IAnimatorModel<IModelShape> model, VisualAnimationView view,
      double tempo) throws IllegalArgumentException {
    if (tempo <= 0) {
      throw new IllegalArgumentException("Tempo must be positive!");
    }
    this.tempo = tempo;
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.knownCommands = new HashMap<>();
    this.putKnownCommands();
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
    int delay = (int) (1000.0 / this.tempo); // in millis
    Timer timer = new Timer(delay, event -> {
      if (model.hasNextFrame()) {

        List<IModelShape> shapesToRender = model.getNextFrame();
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
      }
    });

    timer.setRepeats(true);
    timer.start();
  }

}
