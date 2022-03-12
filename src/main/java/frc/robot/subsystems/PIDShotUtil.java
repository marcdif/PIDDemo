// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants;

public class PIDShotUtil extends PIDSubsystem {
  private CANSparkMax flywheelMotor;
  private RelativeEncoder flywheelEncoder;

  /** Creates a new PIDShotUtil. */
  public PIDShotUtil() {
    super(
        // The PIDController used by the subsystem

        // TODO - Update your P/I/D constants values in the Constants class!
        new PIDController(Constants.Shot_PID_P_Value, Constants.Shot_PID_I_Value, Constants.Shot_PID_D_Value));

    // Instantiate your Spark Max motor controller.
    // You can use any motor controller!
    // However, you'll need an encoder that outputs a "velocity" value.
    // Spark Max motor controllers have a built-in encoder for this.

    // TODO - Ensure the Constants.shotMotorCANID matches your shot motor's CAN ID!
    flywheelMotor = new CANSparkMax(Constants.shotMotorCANID, MotorType.kBrushless);

    // Brake vs Coast idle modes determine how the motor spins down when it's
    // powered off. Brake mode applies a "brake" force which spins down quicker,
    // whereas Coast mode allows the motor to spin down at its own pace.
    //
    // With a flywheel motor controlled by a PID Controller, you may get better
    // behavior with Coast as your idle mode. This ensures the behavior of the motor
    // is entirely managed by the PID Controller and gravity/friction.
    flywheelMotor.setIdleMode(IdleMode.kCoast);

    // Instantiate your Spark Max's built-in encoder.
    flywheelEncoder = flywheelMotor.getEncoder();

    // Initialize your PID Controller's setpoint to 0.
    // This isn't entirely necessary, but is good practice.
    setSetpoint(0);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    // Ensure the output value from the PID Controller is between -1 to 1.
    // You can't set a motor higher than 1.0/lower than -1.0.
    output = MathUtil.clamp(output, -1.0, 1.0);

    // Set your motor speed to the PID Controller's output value.
    flywheelMotor.set(output);
  }

  @Override
  public double getMeasurement() {
    // Return the process variable measurement here

    // Return your encoder's velocity measurement.
    // On Spark Max motor controllers, this is the motor's RPM.
    return flywheelEncoder.getVelocity();
  }

  @Override
  public void periodic() {
    // super.periodic() MUST remain in this method!
    // This is how the PIDSubsystem parent class does PID calculations!
    super.periodic();

    SmartDashboard.putBoolean("PID Subsystem Enabled", isEnabled());
    SmartDashboard.putNumber("PID Setpoint", getSetpoint());
  }
}
