// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PIDShotUtil;

public class StopShotMotor extends CommandBase {
  private final PIDShotUtil pidShotUtil;

  private boolean done;

  /** Creates a new StopShotMotor. */
  public StopShotMotor(PIDShotUtil pidShotUtil) {
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
    // Disable the PID Controller
    //
    // Note: This method disables the PID Controller AND sends a 0 speed value to
    // the motor controller! No need to set the motor speed yourself.
    pidShotUtil.disable();

    // Set the PID setpoint to 0.
    // This isn't entirely necessary, but is good practice.
    pidShotUtil.setSetpoint(0);

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
