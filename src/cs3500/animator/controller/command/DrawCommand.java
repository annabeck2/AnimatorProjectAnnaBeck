package cs3500.animator.controller.command;

import cs3500.animator.view.IAnimatorView;
import java.awt.Color;
import java.util.Objects;

/**
 * <p>An abstract draw command for the controller to use with the view.</p>
 */
public abstract class DrawCommand implements IDrawCommand {

  protected final IAnimatorView view;

  /**
   * <p>Constructs a {@code DrawCommand}.</p>
   * @param view the view on which to execute the draw command
   */
  public DrawCommand(IAnimatorView view) {
    this.view = Objects.requireNonNull(view);
  }

  @Override
  public abstract void execute(int x, int y, int width, int height, Color color)
      throws UnsupportedOperationException;

}
