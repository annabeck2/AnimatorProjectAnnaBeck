package cs3500.animator.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Objects;

/**
 * An ellipse object for use with the canvas of the easy animator.
 */
public class DrawingPanelEllipse extends Rectangle implements IDrawingPanelShape {

  private final Color color;

  /**
   * <p>Constructs a {@code DrawingPanelEllipse}.</p>
   *
   * @param x      the x coordinate of the ellipse
   * @param y      the y coordinate of the ellipse
   * @param width  the width of the ellipse
   * @param height the height of the ellipse
   * @param color  the Color of the ellipse
   * @throws NullPointerException     if the Color is null
   * @throws IllegalArgumentException if the width or height are not positive values
   */
  public DrawingPanelEllipse(int x, int y, int width, int height, Color color)
      throws NullPointerException, IllegalArgumentException {
    super(x, y, width, height);
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive values!");
    }
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public void draw(Graphics g, boolean outline) {
    Objects.requireNonNull(g);
    g.setColor(this.color);
    if (outline) {
      g.drawOval(x, y, width, height);
    } else {
      g.fillOval(x, y, width, height);
    }
  }

}