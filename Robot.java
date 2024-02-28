// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// added imports below
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.XboxController;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default"; //goes across line
  private static final String kspeakermiddle = "speakermiddle";
  private static final String kRedLongAuto = "Red Long Speaker";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final PWMSparkMax leftFront = new PWMSparkMax(0);
  private final PWMSparkMax leftRear = new PWMSparkMax(1);
  private final PWMSparkMax rightFront = new PWMSparkMax(2);
  private final PWMSparkMax rightRear = new PWMSparkMax(3);

  private final PWMSparkMax intake1 = new PWMSparkMax(4);
  private final PWMSparkMax intake2 = new PWMSparkMax(5);

  private final PWMSparkMax shooter = new PWMSparkMax(6);

  private final Servo exampleServo1 = new Servo(9);


  private final Timer timer1 = new Timer();

  private final XboxController driverController = new XboxController(0);
  private final XboxController operatorController = new XboxController(1);


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("speakermiddle", kspeakermiddle);
    m_chooser.addOption("Red Long Speaker", kRedLongAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftFront.setInverted(true);
    rightFront.setInverted(true);
    shooter.setInverted(false);

    timer1.start();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

    timer1.reset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kRedLongAuto:
        if(timer1.get() < 2.0){  //flicking note into shooter
        }
        else if(timer1.get() < 5.0){  //turn on feedwheel to launch note
          shooter.set(.98);
        }
        else if(timer1.get() < 6.5){  //back up
          shooter.set(0);
          leftFront.set(.4);
          leftRear.addFollower(leftFront);
          rightFront.set(.4);
          rightRear.addFollower(rightFront); 
        }
        else if(timer1.get() < 7.5){  //turning
          leftFront.set(-.4);
          leftRear.addFollower(leftFront);
          rightFront.set(.4);
          rightRear.addFollower(rightFront); 
        }
        else if(timer1.get() < 9.5){
          leftFront.set(.5);
          leftRear.addFollower(leftFront);
          rightFront.set(.5);
          rightRear.addFollower(rightFront);
        }
        else{  //turn off all motors
          shooter.set(0);
          leftFront.set(0);
          leftRear.addFollower(leftFront);
          rightFront.set(0);
          rightRear.addFollower(rightFront);
        } 
        break;
      case kspeakermiddle:  //speakermiddle autonomous
        if(timer1.get() < 5.0){  //turn on feedwheel to launch note
          shooter.set(.98);
        }
        else if(timer1.get() < 6.5){  //back up
          shooter.set(0);
          leftFront.set(.4);
          leftRear.addFollower(leftFront);
          rightFront.set(.4);
          rightRear.addFollower(rightFront); 
        }
        else{  //turn off all motors
          shooter.set(0);
          leftFront.set(0);
          leftRear.addFollower(leftFront);
          rightFront.set(0);
          rightRear.addFollower(rightFront); 
        }
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        if(timer1.get() < 2.0){
          leftFront.set(.4);
          leftRear.addFollower(leftFront);
          rightFront.set(.4);
          rightRear.addFollower(rightFront); 
        }else{
          leftFront.set(0);
          leftRear.addFollower(leftFront);
          rightFront.set(0);
          rightRear.addFollower(rightFront); 
        }
        break;
    } 
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    /*tank drive controls */
    //left side controls
    leftFront.set(driverController.getLeftY());
    leftRear.addFollower(leftFront);
    //right side controls
    rightFront.set(-driverController.getRightY());
    rightRear.addFollower(rightFront);
    //-----------------------------------------------
    /*intake controls */
    if(driverController.getRightBumperPressed()){
      intake1.set(.98);
      //intake2.set(.98);
    }else if(driverController.getRightBumperReleased()){
      intake1.set(0);
      intake2.set(0);
    }
    //-----------------------------------------------
    /*shooter controls (Y-split) */
    if(driverController.getYButton()){
      shooter.set(.98);
    }else if(driverController.getXButton()){
      shooter.set(.0);
    }
    //-----------------------------------------------
    /*servo controls */
    if(operatorController.getYButton()){
      exampleServo1.setPosition(.5);
    }else{
      exampleServo1.setPosition(1);
    }

  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
