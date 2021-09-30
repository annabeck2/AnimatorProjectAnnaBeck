package view;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.IAnimatorView;
import cs3500.animator.view.SVGAnimationView;
import java.awt.Color;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests for {@link SVGAnimationView}.
 */
public class SVGAnimationViewTest {

  /**
   * Factory method for creating blank {@link AnimatorModel}s.
   *
   * @return a new {@link AnimatorModel}
   */
  public IAnimatorModel<IModelShape> getModel() {
    return new AnimatorModel();
  }

  @Test(expected = NullPointerException.class)
  public void testNullModel() {
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(null, viewOutput, 2.0);
  }

  @Test(expected = NullPointerException.class)
  public void testNullAppendable() {
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(this.getModel(), null, 2.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTempo() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, -1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTempo() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 0);
  }

  @Test
  public void testDefaultCanvas() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testCanvasMatchesModel() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.setBounds(5, 6, 100, 200);
    view.render();

    expected
        .append("<svg viewbox=\"5 6 100 200\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testNoShapes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeNoKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testMultipleShapesNoKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addShape("r", ShapeType.RECTANGLE);
    model.addShape("r2", ShapeType.RECTANGLE);
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeWithSingleKeyframe() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("<ellipse id=\"c\""
            + " x=\"5.0\" y=\"5.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(0,0,0)\""
            + " visibility=\"hidden\">")
        .append("\n")
        .append(
            "\t<set attributeName=\"visibility\" to=\"visible\" begin=\"0.0s\" dur=\"1ms\" fill=\"freeze\"/>\n"
                + "\t<set attributeName=\"visibility\" to=\"hidden\" begin=\"0.0s\" dur=\"1ms\" fill=\"freeze\"/>")
        .append("\n")
        .append("</ellipse>").append("\n")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeWithOneAnimate() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.RECTANGLE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    model.addKeyFrame(1, "c", 6, 5, 10, 10, Color.BLACK);
    view.render();

    expected
        .append("<svg viewbox=\"0 0 800 600\" xmlns=\"http://www.w3.org/2000/svg\">")
        .append("\n")
        .append("<rect id=\"c\""
            + " x=\"5.0\" y=\"5.0\" width=\"10.0\" height=\"10.0\" fill=\"rgb(0,0,0)\""
            + " visibility=\"hidden\">")
        .append("\n")
        .append("\t<set attributeName=\"visibility\""
            + " to=\"visible\" begin=\"0.0s\" dur=\"1ms\" fill=\"freeze\"/>")
        .append("\n")
        .append("\t<animate attributeName=\"x\""
            + " from=\"5.0\" to=\"6.0\" begin=\"0.0s\" dur=\"1.0s\" fill=\"freeze\"/>")
        .append("\n")
        .append("\t<animate attributeName=\"y\""
            + " from=\"5.0\" to=\"5.0\" begin=\"0.0s\" dur=\"1.0s\" fill=\"freeze\"/>\n"
            + "\t<animate attributeName=\"width\""
            + " from=\"10.0\" to=\"10.0\" begin=\"0.0s\" dur=\"1.0s\" fill=\"freeze\"/>\n"
            + "\t<animate attributeName=\"height\""
            + " from=\"10.0\" to=\"10.0\" begin=\"0.0s\" dur=\"1.0s\" fill=\"freeze\"/>\n"
            + "\t<animate attributeName=\"fill\" from=\"rgb(0,0,0)\" to=\"rgb(0,0,0)\""
            + " begin=\"0.0s\" dur=\"1.0s\" fill=\"freeze\"/>")
        .append("\n")
        .append("\t<set attributeName=\"visibility\" to=\"hidden\" "
            + "begin=\"1.0s\" dur=\"1ms\" fill=\"freeze\"/>")
        .append("\n")
        .append("</rect>").append("\n")
        .append("\n")
        .append("</svg>");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDrawRectangleUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    view.drawRectangle(5, 5, 5, 5, Color.BLACK);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDrawEllipseUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    view.drawEllipse(5, 5, 5, 5, Color.BLACK);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRefreshUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    view.refresh();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSetTrailsUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new SVGAnimationView(model, viewOutput, 1.0);
    view.setTrails(false);
  }

}

