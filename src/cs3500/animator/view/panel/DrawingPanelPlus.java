package cs3500.animator.view.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Objects;

public class DrawingPanelPlus extends Polygon implements IDrawingPanelShape {

  private final Color color;

  /**
   * <p>Constructs a {@code DrawingPanelPlus}.</p>
   *
   * @param x      x coordinate of top left corner of the plus
   * @param y      y coordinate of top left corner of the plus
   * @param width  width of the plus
   * @param height height of the plus
   * @param color  color of the plus
   * @throws IllegalArgumentException if the width or height are not positive
   */
  public DrawingPanelPlus(int x, int y, int width, int height, Color color)
      throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive values!");
    }
    this.initPoints(x, y, width, height);
    this.color = Objects.requireNonNull(color);
  }

  /**
   * <p>Initializes this Polygon's list of points defining the shape of the plus.</p>
   *
   * @param x      x coordinate of top left corner of the plus
   * @param y      y coordinate of top left corner of the plus
   * @param width  width of the plus
   * @param height height of the plus
   * @throws IllegalArgumentException if the width or height are not positive
   */
  private void initPoints(int x, int y, int width, int height) throws IllegalArgumentException {
    if (width <= 0 || height <= 0) {
      throw new IllegalArgumentException("Width and height must be positive values!");
    }
    this.npoints = 13;
    int[] xPoints = new int[this.npoints];
    int[] yPoints = new int[this.npoints];

    xPoints[0] = x + width / 3;
    yPoints[0] = y;

    xPoints[1] = x + 2 * width / 3;
    yPoints[1] = y;

    xPoints[2] = x + 2 * width / 3;
    yPoints[2] = y + height / 3;

    xPoints[3] = x + width;
    yPoints[3] = y + height / 3;

    xPoints[4] = x + width;
    yPoints[4] = y + 2 * height / 3;

    xPoints[5] = x + 2 * width / 3;
    yPoints[5] = y + 2 * height / 3;

    xPoints[6] = x + 2 * width / 3;
    yPoints[6] = y + height;

    xPoints[7] = x + width / 3;
    yPoints[7] = y + height;

    xPoints[8] = x + width / 3;
    yPoints[8] = y + 2 * height / 3;

    xPoints[9] = x;
    yPoints[9] = y + 2 * height / 3;

    xPoints[10] = x;
    yPoints[10] = y + height / 3;

    xPoints[11] = x + width / 3;
    yPoints[11] = y + height / 3;

    xPoints[12] = x + width / 3;
    yPoints[12] = y;

    this.xpoints = xPoints;
    this.ypoints = yPoints;
  }

  @Override
  public void draw(Graphics g, boolean outline) {
    Objects.requireNonNull(g);
    g.setColor(this.color);
    if (outline) {
      g.drawPolygon(this);
    } else {
      g.fillPolygon(this);
    }
  }

}
