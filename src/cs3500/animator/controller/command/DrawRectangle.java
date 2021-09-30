package cs3500.animator.controller.command;

import cs3500.animator.view.IAnimatorView;
import java.awt.Color;

/**
 * <p>A draw rectangle command for the controller to use with the view.</p>
 */
public class DrawRectangle extends DrawCommand {

  /**
   * <p>Constructs a {@code DrawRectangle}.</p>
   * @param view the view on which to execute the draw command
   */
  public DrawRectangle(IAnimatorView view) {
    super(view);
  }

  @Override
  public void execute(int x, int y, int width, int height, Color color)
      throws UnsupportedOperationException {
    view.drawRectangle(x, y, width, height, color);
  }
}
