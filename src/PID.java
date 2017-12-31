public class PID {

  private float kP, kI, kD;
  private float target, lastInput, integral, output;

  public PID(float kP, float kI, float kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
  }

  public PID(PIDGains gains) {
    kP = gains.kP;
    kI = gains.kI;
    kD = gains.kD;
  }

  public float step(float input) {
    float error = target - input;

    integral += error;

    float deriv = (input - lastInput);
    lastInput = input;

    output = kP * error + kI * input + kD * deriv;
    return output;
  }

  public float getOutput() {
    return output;
  }

  public float getkP() {
    return kP;
  }

  public void setkP(float kP) {
    this.kP = kP;
  }

  public float getkI() {
    return kI;
  }

  public void setkI(float kI) {
    this.kI = kI;
  }

  public float getkD() {
    return kD;
  }

  public void setkD(float kD) {
    this.kD = kD;
  }

  public float getTarget() {
    return target;
  }

  public void setTarget(float target) {
    this.target = target;
  }

}
