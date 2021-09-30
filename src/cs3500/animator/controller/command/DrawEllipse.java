package cs3500.animator.controller.command;

import cs3500.animator.view.IAnimatorView;
import java.awt.Color;

/**
 * <p>A draw ellipse command for the controller to use with the view.</p>
 */
public class DrawEllipse extends DrawCommand {

  /**
   * <p>Constructs a {@code DrawEllipse}.</p>
   * @param view the view on which to execute the draw command
   */
  public DrawEllipse(IAnimatorView view) {
    super(view);
  }

  @Override
  public void execute(int x, int y, int width, int height, Color color)
      throws UnsupportedOperationException {
    view.drawEllipse(x, y, width, height, color);
  }
}
