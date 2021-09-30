package cs3500.animator.view.svg;

import cs3500.animator.model.shape.ShapeType;

/**
 * <p>Factory class for an SVG Shape.</p>
 */
public class SVGShapeFactory {

  /**
   * <p>Creates a new instance of the shapeType.</p>
   * @param shapeType the type of shape
   * @return a new Shape of type shapeType.
   */
  public static SVGShape create(ShapeType shapeType) {
    switch (shapeType) {
      case RECTANGLE:
        return new SVGRect();
      case ELLIPSE:
        return new SVGEllipse();
      case PLUS:
        return new SVGPlus();
      default:
        throw new IllegalArgumentException("Unknown shape type!");
    }
  }

}
