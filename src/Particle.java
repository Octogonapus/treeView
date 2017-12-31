import processing.core.PVector;

public class Particle {

  final Main main;
  final PVector position;
  private final PVector velocity;
  private final PVector acceleration;
  private final PID pushToPIDX, pushToPIDY, pushFromPIDX, pushFromPIDY, goToYPID;

  public Particle(Main main, PVector position) {
    this.main = main;
    this.position = position.copy();

    velocity = new PVector(0.0f, 0.0f);
    acceleration = new PVector(0.0f, 0.0f);

    PIDGains gains = new PIDGains(0.01f, 0.0f, 0.0f);
    pushToPIDX = new PID(gains);
    pushToPIDY = new PID(gains);
    pushFromPIDX = new PID(gains);
    pushFromPIDY = new PID(gains);
    goToYPID = new PID(gains);
  }

  public void run() {
    updatePosition();
    renderNode();
  }

  public void pushTo(PVector location, float offset) {
    if (position.dist(location) > offset) {
      pushToPIDX.setTarget(location.x);
      pushToPIDY.setTarget(location.y);
      velocity.x += pushToPIDX.step(position.x);
      velocity.y += pushToPIDY.step(position.y);
    }
  }

  public void pushFrom(PVector location, float minDist) {
    if (position.dist(location) <= minDist) {
      pushFromPIDX.setTarget(location.x);
      pushFromPIDY.setTarget(location.y);
      velocity.x += -pushFromPIDX.step(position.x);
      velocity.y += -pushFromPIDY.step(position.y);
    }
  }

  public void goToY(float y) {
    goToYPID.setTarget(y);
    velocity.y += goToYPID.step(position.y);
  }

  private void updatePosition() {
    clipVelocity();
    applyFriction();
    velocity.add(acceleration);
    position.add(velocity);
  }

  private void clipVelocity() {
    velocity.limit(10);
  }

  private void applyFriction() {
    velocity.mult(0.95f);
  }

  private void renderNode() {
    main.fill(255);
    main.ellipse(position.x, position.y, 8, 8);
  }

}
