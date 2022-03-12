// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.PIDShotUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetShotHighSpeed extends CommandBase {
  private final PIDShotUtil pidShotUtil;

  private boolean done;

  /** Creates a new SetShotHighSpeed. */
  public SetShotHighSpeed(PIDShotUtil pidShotUtil) {
    this.pidShotUtil = pidShotUtil;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(pidShotUtil);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Initialize done to false so the command doesn't finish until we execute
    done = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Enable the PID Controller if it isn't already running
    //
    // Note: Do NOT enable() the PID Controller if it's not already enabled!
    // PIDSubsystem.enable() resets the PID Controller, which means it "forgets"
    // the history of your RPM graph up to this point. Your PID Controller will
    // think it's starting from 0, rather than where it's from, and it may apply
    // significant adjustments which can damage motors.
    if (!pidShotUtil.isEnabled())
      pidShotUtil.enable();

    // Update the PID Controller's setpoint to Constants.shotHighRPM
    pidShotUtil.setSetpoint(Constants.shotHighRPM);

    // Set done = true, which is returned in the isFinished method
    done = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return done;
  }
}
