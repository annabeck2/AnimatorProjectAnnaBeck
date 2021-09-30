package view;

import static org.junit.Assert.assertEquals;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.shape.IModelShape;
import cs3500.animator.model.shape.ShapeType;
import cs3500.animator.view.TextualAnimationView;
import cs3500.animator.view.IAnimatorView;
import java.awt.Color;
import org.junit.Test;

import java.io.IOException;

/**
 * Tests for {@link TextualAnimationView}.
 */
public class TextualAnimationViewTest {

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
    IAnimatorView view = new TextualAnimationView(null, viewOutput, 2.0);
  }

  @Test(expected = NullPointerException.class)
  public void testNullAppendable() {
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(this.getModel(), null, 2.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTempo() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, -1.0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testZeroTempo() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 0);
  }

  @Test
  public void testDefaultCanvas() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();
    view.render();

    expected.append("canvas 0 0 800 600").append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testCanvasMatchesModel() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.setBounds(5, 6, 100, 200);
    view.render();

    expected.append("canvas 5 6 100 200").append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testNoShapes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();
    view.render();

    expected.append("canvas 0 0 800 600").append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeNoKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    view.render();

    expected.append("canvas 0 0 800 600")
        .append("\n")
        .append("shape c ellipse")
        .append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testMultipleShapesNoKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addShape("r", ShapeType.RECTANGLE);
    model.addShape("r2", ShapeType.RECTANGLE);
    view.render();

    expected.append("canvas 0 0 800 600")
        .append("\n")
        .append("shape c ellipse")
        .append("\n")
        .append("\n")
        .append("shape r rectangle")
        .append("\n")
        .append("\n")
        .append("shape r2 rectangle")
        .append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeWithSingleKeyframe() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    view.render();

    expected.append("canvas 0 0 800 600")
        .append("\n")
        .append("shape c ellipse")
        .append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 0 5 5 10 10 0 0 0")
        .append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeWithEvenKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    model.addKeyFrame(1, "c", 6, 6, 11, 11, Color.BLACK);
    view.render();

    expected.append("canvas 0 0 800 600")
        .append("\n")
        .append("shape c ellipse").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 0 5 5 10 10 0 0 0").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 1 6 6 11 11 0 0 0").append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testSingleShapeWithOddKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    model.addKeyFrame(1, "c", 6, 6, 11, 11, Color.BLACK);
    model.addKeyFrame(2, "c", 7, 7, 12, 12, Color.BLACK);
    view.render();

    expected.append("canvas 0 0 800 600").append("\n")
        .append("shape c ellipse").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 0 5 5 10 10 0 0 0").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 1 6 6 11 11 0 0 0").append("\n")
        .append("motion c 1 6 6 11 11 0 0 0 2 7 7 12 12 0 0 0").append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testMultipleShapesWithMultipleKeyframes() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("c", ShapeType.ELLIPSE);
    model.addKeyFrame(0, "c", 5, 5, 10, 10, Color.BLACK);
    model.addKeyFrame(1, "c", 6, 6, 11, 11, Color.BLACK);
    model.addShape("r", ShapeType.RECTANGLE);
    model.addKeyFrame(3, "r", 50, 50, 100, 100, Color.BLUE);
    model.addKeyFrame(5, "r", 50, 50, 100, 100, Color.GREEN);
    view.render();

    expected.append("canvas 0 0 800 600")
        .append("\n")
        .append("shape c ellipse").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 0 5 5 10 10 0 0 0").append("\n")
        .append("motion c 0 5 5 10 10 0 0 0 1 6 6 11 11 0 0 0").append("\n")
        .append("\n")
        .append("shape r rectangle").append("\n")
        .append("motion r 3 50 50 100 100 0 0 255 3 50 50 100 100 0 0 255").append("\n")
        .append("motion r 3 50 50 100 100 0 0 255 5 50 50 100 100 0 255 0").append("\n")
        .append("\n");
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testLargeRender() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    Appendable expected = new StringBuilder();

    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("C", ShapeType.ELLIPSE);

    model.addKeyFrame(1, "R", 200, 200, 50, 100, Color.RED); // in place at 1
    model.addKeyFrame(10, "R", 200, 200, 50, 100, Color.RED);
    model.addKeyFrame(50, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(51, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(70, "R", 300, 300, 25, 100, Color.RED);
    model.addKeyFrame(100, "R", 200, 200, 25, 100, Color.RED);

    model.addKeyFrame(6, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(20, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(50, "C", 440, 250, 120, 60, Color.BLUE);
    model.addKeyFrame(70, "C", 440, 370, 120, 60, new Color(0, 170, 85));
    model.addKeyFrame(80, "C", 440, 370, 120, 60, new Color(0, 255, 0));
    model.addKeyFrame(100, "C", 440, 370, 120, 60, new Color(0, 255, 0));

    expected.append(
        "canvas 0 0 800 600\n"
            + "shape R rectangle\n"
            + "motion R 1 200 200 50 100 255 0 0 1 200 200 50 100 255 0 0\n"
            + "motion R 1 200 200 50 100 255 0 0 10 200 200 50 100 255 0 0\n"
            + "motion R 10 200 200 50 100 255 0 0 50 300 300 50 100 255 0 0\n"
            + "motion R 50 300 300 50 100 255 0 0 51 300 300 50 100 255 0 0\n"
            + "motion R 51 300 300 50 100 255 0 0 70 300 300 25 100 255 0 0\n"
            + "motion R 70 300 300 25 100 255 0 0 100 200 200 25 100 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "motion C 6 440 70 120 60 0 0 255 6 440 70 120 60 0 0 255\n"
            + "motion C 6 440 70 120 60 0 0 255 20 440 70 120 60 0 0 255\n"
            + "motion C 20 440 70 120 60 0 0 255 50 440 250 120 60 0 0 255\n"
            + "motion C 50 440 250 120 60 0 0 255 70 440 370 120 60 0 170 85\n"
            + "motion C 70 440 370 120 60 0 170 85 80 440 370 120 60 0 255 0\n"
            + "motion C 80 440 370 120 60 0 255 0 100 440 370 120 60 0 255 0\n"
            + "\n");

    view.render();
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test
  public void testLargeRenderDoubleSpeed() throws IOException {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 2.0);
    Appendable expected = new StringBuilder();

    model.addShape("R", ShapeType.RECTANGLE);
    model.addShape("C", ShapeType.ELLIPSE);

    model.addKeyFrame(1, "R", 200, 200, 50, 100, Color.RED); // in place at 1
    model.addKeyFrame(10, "R", 200, 200, 50, 100, Color.RED);
    model.addKeyFrame(50, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(51, "R", 300, 300, 50, 100, Color.RED);
    model.addKeyFrame(70, "R", 300, 300, 25, 100, Color.RED);
    model.addKeyFrame(100, "R", 200, 200, 25, 100, Color.RED);

    model.addKeyFrame(6, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(20, "C", 440, 70, 120, 60, Color.BLUE);
    model.addKeyFrame(50, "C", 440, 250, 120, 60, Color.BLUE);
    model.addKeyFrame(70, "C", 440, 370, 120, 60, new Color(0, 170, 85));
    model.addKeyFrame(80, "C", 440, 370, 120, 60, new Color(0, 255, 0));
    model.addKeyFrame(100, "C", 440, 370, 120, 60, new Color(0, 255, 0));

    expected.append(
        "canvas 0 0 800 600\n"
            + "shape R rectangle\n"
            + "motion R 0.5 200 200 50 100 255 0 0 0.5 200 200 50 100 255 0 0\n"
            + "motion R 0.5 200 200 50 100 255 0 0 5 200 200 50 100 255 0 0\n"
            + "motion R 5 200 200 50 100 255 0 0 25 300 300 50 100 255 0 0\n"
            + "motion R 25 300 300 50 100 255 0 0 25.5 300 300 50 100 255 0 0\n"
            + "motion R 25.5 300 300 50 100 255 0 0 35 300 300 25 100 255 0 0\n"
            + "motion R 35 300 300 25 100 255 0 0 50 200 200 25 100 255 0 0\n"
            + "\n"
            + "shape C ellipse\n"
            + "motion C 3 440 70 120 60 0 0 255 3 440 70 120 60 0 0 255\n"
            + "motion C 3 440 70 120 60 0 0 255 10 440 70 120 60 0 0 255\n"
            + "motion C 10 440 70 120 60 0 0 255 25 440 250 120 60 0 0 255\n"
            + "motion C 25 440 250 120 60 0 0 255 35 440 370 120 60 0 170 85\n"
            + "motion C 35 440 370 120 60 0 170 85 40 440 370 120 60 0 255 0\n"
            + "motion C 40 440 370 120 60 0 255 0 50 440 370 120 60 0 255 0\n"
            + "\n");
    view.render();
    assertEquals(expected.toString(), viewOutput.toString());
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDrawRectangleUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    view.drawRectangle(5, 5, 5, 5, Color.BLACK);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testDrawEllipseUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    view.drawEllipse(5, 5, 5, 5, Color.BLACK);
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRefreshUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    view.refresh();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testSetTrailsUnsupported() {
    IAnimatorModel<IModelShape> model = this.getModel();
    Appendable viewOutput = new StringBuilder();
    IAnimatorView view = new TextualAnimationView(model, viewOutput, 1.0);
    view.setTrails(false);
  }

}
