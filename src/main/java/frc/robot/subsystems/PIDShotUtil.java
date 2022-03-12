// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PIDShotUtil extends SubsystemBase {
  private CANSparkMax flywheelMotor, secondMotor;
  private RelativeEncoder flywheelEncoder;

  private BangBangController bangBangController;
  private double setpoint = 0;
  private boolean enabled = false;

  /** Creates a new PIDShotUtil. */
  public PIDShotUtil() {

    // Instantiate your Spark Max motor controller.
    // You can use any motor controller!
    // However, you'll need an encoder that outputs a "velocity" value.
    // Spark Max motor controllers have a built-in encoder for this.

    // TODO - Ensure the Constants.shotMotorCANID matches your shot motor's CAN ID!
    flywheelMotor = new CANSparkMax(Constants.shotMotorCANID, MotorType.kBrushless);
    secondMotor = new CANSparkMax(Constants.shotMotorCANID_2, MotorType.kBrushless);
    secondMotor.follow(flywheelMotor, true);

    // Brake vs Coast idle modes determine how the motor spins down when it's
    // powered off. Brake mode applies a "brake" force which spins down quicker,
    // whereas Coast mode allows the motor to spin down at its own pace.
    //
    // With a flywheel motor controlled by a PID Controller, you may get better
    // behavior with Coast as your idle mode. This ensures the behavior of the motor
    // is entirely managed by the PID Controller and gravity/friction.
    flywheelMotor.setIdleMode(IdleMode.kCoast);
    secondMotor.setIdleMode(IdleMode.kCoast);

    // Instantiate your Spark Max's built-in encoder.
    flywheelEncoder = flywheelMotor.getEncoder();

    bangBangController = new BangBangController(50);
  }

  public double getSetpoint() {
      return setpoint;
  }

  public void setSetpoint(double setpoint) {
      this.setpoint = setpoint;
  }

  public boolean isEnabled() {
      return enabled;
  }

  public void enable() {
      this.enabled = true;
  }

  public void disable() {
      this.enabled = false;
      flywheelMotor.set(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("PID Subsystem Enabled", isEnabled());
    SmartDashboard.putNumber("PID Setpoint", getSetpoint());

    if (isEnabled()) {
      flywheelMotor.set(bangBangController.calculate(flywheelEncoder.getVelocity()));
    }
  }
}
