package cs3500.animator.view.svg;

import cs3500.animator.model.shape.IModelShape;
import java.awt.Color;
import java.io.IOException;

public class SVGRect extends AbstractSVGShape {

  public SVGRect() {
    super("rect");
  }

  @Override
  public void appendAnimate(IModelShape start, IModelShape end,
      double startTime, double endTime, Appendable out) throws IOException {
    this.appendAnimate("x", start.getX(), end.getX(), startTime, endTime, out);
    this.appendAnimate("y", start.getY(), end.getY(), startTime, endTime, out);
    this.appendAnimate("width", start.getWidth(), end.getWidth(), startTime, endTime, out);
    this.appendAnimate("height", start.getHeight(), end.getHeight(), startTime, endTime, out);

    Color startColor = start.getColor();
    Color endColor = end.getColor();
    String startColorStr = String
        .format("rgb(%d,%d,%d)", startColor.getRed(), startColor.getGreen(), startColor.getBlue());
    String endColorStr = String
        .format("rgb(%d,%d,%d)", endColor.getRed(), endColor.getGreen(), endColor.getBlue());
    this.appendAnimate("fill", startColorStr, endColorStr, startTime, endTime, out);
  }

}
