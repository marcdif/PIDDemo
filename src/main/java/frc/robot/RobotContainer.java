// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.SetShotHighSpeed;
import frc.robot.commands.SetShotLowSpeed;
import frc.robot.commands.StopShotMotor;
import frc.robot.subsystems.PIDShotUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems:
  private final PIDShotUtil pidShotUtil = new PIDShotUtil();

  // Joysticks:
  private final Joystick joystick;

  // JoystickButtons:
  private JoystickButton stopShotMotor, setShotLowSpeed, setShotHighSpeed;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    joystick = new Joystick(0);

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // TODO - Update the Joystick mappings depending on what controllers you use!

    stopShotMotor = new JoystickButton(joystick, Constants.kJoystickButton1);
    stopShotMotor.whenPressed(new StopShotMotor(pidShotUtil));

    setShotLowSpeed = new JoystickButton(joystick, Constants.kJoystickButton2);
    setShotLowSpeed.whenPressed(new SetShotLowSpeed(pidShotUtil));

    setShotHighSpeed = new JoystickButton(joystick, Constants.kJoystickButton3);
    setShotHighSpeed.whenPressed(new SetShotHighSpeed(pidShotUtil));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
