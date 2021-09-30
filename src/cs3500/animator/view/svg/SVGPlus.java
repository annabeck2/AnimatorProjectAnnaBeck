package cs3500.animator.view.svg;

import cs3500.animator.model.shape.IModelShape;
import java.awt.Color;
import java.io.IOException;

/**
 * <p>Class to represent an SVG Plus Sign.</p>
 */
public class SVGPlus extends AbstractSVGShape {

  /**
   * <p> Constructs an SVG Polygon Shape.</p>
   */
  public SVGPlus() {
    super("polygon");
  }

  @Override
  public void appendAnimate(IModelShape start, IModelShape end, double startTime, double endTime,
      Appendable out) throws IOException {
    int npoints = 13;
    int[] startXPoints = this.calculateXPoints(start, npoints);
    int[] startYPoints = this.calculateYPoints(start, npoints);
    int[] endXPoints = this.calculateXPoints(end, npoints);
    int[] endYPoints = this.calculateYPoints(end, npoints);

    String startState = this.stringifyPoints(startXPoints, startYPoints);
    String endState = this.stringifyPoints(endXPoints, endYPoints);
    this.appendAnimate("points", startState, endState, startTime, endTime, out);

    Color startColor = start.getColor();
    Color endColor = end.getColor();
    String startColorStr = String
        .format("rgb(%d,%d,%d)", startColor.getRed(), startColor.getGreen(), startColor.getBlue());
    String endColorStr = String
        .format("rgb(%d,%d,%d)", endColor.getRed(), endColor.getGreen(), endColor.getBlue());
    this.appendAnimate("fill", startColorStr, endColorStr, startTime, endTime, out);
  }

  /**
   * <p>Get an SVG-compliant string representing the points in this polygon.</p>
   * @param xPoints the xpoints of the polygon
   * @param yPoints the ypoints of the polygon
   * @return a string representing the points in this polygon
   */
  private String stringifyPoints(int[] xPoints, int[] yPoints) {
    if (xPoints.length != yPoints.length) {
      throw new IllegalArgumentException("Points arrays must be of the same length!");
    }
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < xPoints.length; i++) {
      sb.append(xPoints[i]).append(",").append(yPoints[i]);
      if (i < xPoints.length - 1) {
        sb.append(", ");
      }
    }
    return sb.toString();
  }

  /**
   * <p>Returns an integer array of the x-points for the polygon.</p>
   *
   * @param state   the state of the polygon
   * @param npoints the number of points in the polygon
   * @return the array of x-points
   */
  private int[] calculateXPoints(IModelShape state, int npoints) {
    int[] xPoints = new int[npoints];
    int x = (int) state.getX();
    int width = (int) state.getWidth();

    xPoints[0] = x + width / 3;
    xPoints[1] = x + 2 * width / 3;
    xPoints[2] = x + 2 * width / 3;
    xPoints[3] = x + width;
    xPoints[4] = x + width;
    xPoints[5] = x + 2 * width / 3;
    xPoints[6] = x + 2 * width / 3;
    xPoints[7] = x + width / 3;
    xPoints[8] = x + width / 3;
    xPoints[9] = x;
    xPoints[10] = x;
    xPoints[11] = x + width / 3;
    xPoints[12] = x + width / 3;

    return xPoints;
  }

  /**
   * <p>Returns an integer array of the y-points for the polygon.</p>
   *
   * @param state   the state of the polygon
   * @param npoints the number of points in the polygon
   * @return the array of y-points
   */
  private int[] calculateYPoints(IModelShape state, int npoints) {

    int[] yPoints = new int[npoints];
    int y = (int) state.getY();
    int height = (int) state.getHeight();

    yPoints[0] = y;
    yPoints[1] = y;
    yPoints[2] = y + height / 3;
    yPoints[3] = y + height / 3;
    yPoints[4] = y + 2 * height / 3;
    yPoints[5] = y + 2 * height / 3;
    yPoints[6] = y + height;
    yPoints[7] = y + height;
    yPoints[8] = y + 2 * height / 3;
    yPoints[9] = y + 2 * height / 3;
    yPoints[10] = y + height / 3;
    yPoints[11] = y + height / 3;
    yPoints[12] = y;

    return yPoints;

  }

}
