package cs3500.animator.view.svg;

import cs3500.animator.model.shape.IModelShape;
import java.awt.Color;
import java.io.IOException;

public class SVGEllipse extends AbstractSVGShape {

  public SVGEllipse() {
    super("ellipse");
  }

  @Override
  public void appendAnimate(IModelShape start, IModelShape end, double startTime, double endTime,
      Appendable out) throws IOException {
    this.appendAnimate("cx", start.getX(), end.getX(), startTime, endTime, out);
    this.appendAnimate("cy", start.getY(), end.getY(), startTime, endTime, out);
    this.appendAnimate("rx", start.getWidth(), end.getWidth(), startTime, endTime, out);
    this.appendAnimate("ry", start.getHeight(), end.getHeight(), startTime, endTime, out);

    Color startColor = start.getColor();
    Color endColor = end.getColor();
    String startColorStr = String
        .format("rgb(%d,%d,%d)", startColor.getRed(), startColor.getGreen(), startColor.getBlue());
    String endColorStr = String
        .format("rgb(%d,%d,%d)", endColor.getRed(), endColor.getGreen(), endColor.getBlue());
    this.appendAnimate("fill", startColorStr, endColorStr, startTime, endTime, out);
  }
}
