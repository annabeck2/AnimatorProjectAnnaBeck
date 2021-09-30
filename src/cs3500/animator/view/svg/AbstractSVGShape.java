package cs3500.animator.view.svg;

import cs3500.animator.model.shape.IModelShape;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractSVGShape implements SVGShape {

  protected final String svgShapeType;

  public AbstractSVGShape(String svgShapeType) {
    this.svgShapeType = Objects.requireNonNull(svgShapeType);
  }

  @Override
  public void appendInitialState(String shapeId, IModelShape initialState, Appendable out)
      throws IOException {
    out.append("<").append(svgShapeType)
        .append(" id=\"").append(shapeId).append("\"")
        .append(" x=\"").append(String.valueOf(initialState.getX())).append("\"")
        .append(" y=\"").append(String.valueOf(initialState.getY())).append("\"")
        .append(" width=\"").append(String.valueOf(initialState.getWidth())).append("\"")
        .append(" height=\"").append(String.valueOf(initialState.getHeight())).append("\"")
        .append(" fill=").append("\"rgb(")
        .append(String.valueOf(initialState.getColor().getRed())).append(",")
        .append(String.valueOf(initialState.getColor().getGreen())).append(",")
        .append(String.valueOf(initialState.getColor().getBlue()))
        .append(")\"")
        .append(" visibility=\"hidden\"") // shapes start out as hidden until their first keyframe
        .append(">")
        .append("\n");
  }

  @Override
  public abstract void appendAnimate(IModelShape start, IModelShape end,
      double startTime, double endTime, Appendable out) throws IOException;

  /**
   * <p>Outputs the {@code <animate>} tag for the given attribute.</p>
   *
   * @param attributeName name of the attribute that is being animated
   * @param startVal      starting value of the attribute
   * @param endVal        ending value of the attribute
   * @param startTime     time at which starting value occurs, in seconds
   * @param endTime       time at which ending value occurs, in seconds
   * @throws IOException if output fails
   */
  protected void appendAnimate(String attributeName, String startVal, String endVal,
      double startTime, double endTime, Appendable out) throws IOException {
    double duration = endTime - startTime;

    out.append("\t<animate attributeName=")
        .append("\"").append(attributeName).append("\"")
        .append(" from=\"")
        .append(startVal).append("\"")
        .append(" to=\"")
        .append(endVal).append("\"")
        .append(" begin=\"")
        .append(String.valueOf(startTime)).append("s\"")
        .append(" dur=\"")
        .append(String.valueOf(duration)).append("s\"")
        .append(" fill=\"freeze\"")
        .append("/>")
        .append("\n");
  }

  /**
   * <p>Outputs the {@code <animate>} tag for the given attribute, represented as a double. Note:
   * Only to be used with attributes whose values can be represented as doubles.</p>
   *
   * @param attributeName name of the attribute that is being animated
   * @param startVal      starting value of the attribute
   * @param endVal        ending value of the attribute
   * @param startTime     time at which starting value occurs, in seconds
   * @param endTime       time at which ending value occurs, in seconds
   * @throws IOException if output fails
   */
  protected void appendAnimate(String attributeName, double startVal, double endVal,
      double startTime, double endTime, Appendable out) throws IOException {
    String startValStr = String.valueOf(startVal);
    String endValStr = String.valueOf(endVal);
    this.appendAnimate(attributeName, startValStr, endValStr, startTime, endTime, out);
  }

  @Override
  public void appendVisibility(double startTime, boolean visible, Appendable out)
      throws IOException {
    String visibilityStr = visible ? "visible" : "hidden";

    out.append("\t<set attributeName=")
        .append("\"").append("visibility").append("\"")
        .append(" to=\"").append(visibilityStr).append("\"")
        .append(" begin=\"").append(String.valueOf(startTime)).append("s\"")
        .append(" dur=\"").append("1ms").append("\"")
        .append(" fill=\"freeze\"/>\n");
  }

  @Override
  public void appendClosingTag(Appendable out) throws IOException {
    // print closing tag for the shape
    out.append("</").append(svgShapeType).append(">")
        .append("\n")
        .append("\n");
  }

}
