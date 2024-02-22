// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase; //shows yellow error
import frc.robot.Constants; //shows red error



/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  PWMSparkMax frontLeft = new PWMSparkMax(0);
  PWMSparkMax backLeft = new PWMSparkMax(1);
  PWMSparkMax frontRight = new PWMSparkMax(2);
  PWMSparkMax backRight = new PWMSparkMax(3);
  PWMSparkMax upperI = new PWMSparkMax(4);
  PWMSparkMax lowerI = new PWMSparkMax(5);



  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
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
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  
  XboxController controller = new XboxController(0);
  //XboxController controller2 = new XboxController(1);
  

  if(controller.getBButtonPressed()){
    lowerI.set(1);
    upperI.set(1);
  }else if(controller.getBButtonReleased()){
    lowerI.set(0);
    upperI.set(0);
  }

    // class Drivetrain extends SubsystemBase {

    PWMSparkMax frontLeft;
    PWMSparkMax frontRight;
    PWMSparkMax backLeft;
    PWMSparkMax backRight;
    
    backLeft.addfollower(frontLeft); //addfolower is an error, the end parenthesis is an error
    backRight.addfollower(frontLeft);
    DifferentialDrive drive;


    // public Drivetrain(){  //public is an error
      
    frontLeft = new PWMSparkMax(Constants.front_Left);
    frontLeft.setinverted(true);

    backLeft = new PWMSparkMax(Constants.back_Left);
    backLeft.setinverted(true);

    frontRight = new PWMSparkMax(Constants.front_Right);
    frontRight.setInverted(false);

    backRight = new PWMSparkMax(Constants.back_Right);
    backRight.setInverted(false);

    backLeft = new addfollower(frontLeft);
    backRight = new addfollower(frontRight);

    drive = new DifferentialDrive(frontLeft, frontRight);
    }
    @Override
    public void periodic(){

    }
    public void drivewithjoysticks(XboxController,double speed){ //Xboxcontroller is an error
      drive.tankDrive(controller.getLeftY(), controller.getRightY());
    }
    public void driveForward(double speed)
    {
      drive.tankDrive(-1, -1);
    }
    public void stop()
    {
      drive.stopMotor();
    }


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
