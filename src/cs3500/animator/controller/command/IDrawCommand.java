package cs3500.animator.controller.command;

import java.awt.Color;

/**
 * <p>Interface for a draw command as part of command pattern for controller to use.</p>
 */
public interface IDrawCommand {

  /**
   * <p>Executes this command (draws a shape on the view).</p>
   *
   * @param x      x position of the shape to draw
   * @param y      y position of the shape to draw
   * @param width  width of the shape to draw
   * @param height height of the shape to draw
   * @param color  color of the shape to draw
   * @throws UnsupportedOperationException if the view does not support drawing shapes
   */
  void execute(int x, int y, int width, int height, Color color)
      throws UnsupportedOperationException;

}
