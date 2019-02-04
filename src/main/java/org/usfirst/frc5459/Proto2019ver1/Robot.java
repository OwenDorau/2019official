// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc5459.Proto2019ver1;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc5459.Proto2019ver1.commands.*;
import org.usfirst.frc5459.Proto2019ver1.sensors.PixyCam2;
import org.usfirst.frc5459.Proto2019ver1.sensors.PixyCamBlocks;
import org.usfirst.frc5459.Proto2019ver1.subsystems.*;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Drive drive;
    public static PDP pDP;
    public static UsbCamera camera1, camera2;
    public static VideoSink server;
    public static Boolean flipped;
    public static Integer cameraNumber;
    public static AnalogGyro vexGyro;
    // public static Encoder rightEncoder;
    // public static Encoder leftEncoder;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drive = new Drive();
        pDP = new PDP();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        camera1.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        camera2.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
        server = CameraServer.getInstance().getServer();
        flipped = false;
        server.setSource(camera1);
        vexGyro = new AnalogGyro(0);
        vexGyro.setSensitivity(.00175);
        //rightEncoder = new Encoder(0, 1, false);
        //leftEncoder = new Encoder(2, 3, true);
        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putBoolean("isFlipped", flipped);
        SmartDashboard.putNumber("right encoder", drive.getRightEncoder());
        SmartDashboard.putNumber("left encoder", drive.getLeftEncoder());
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        
        if (autonomousCommand != null) autonomousCommand.start();
        /*
     * This function is called periodically during autonomous
     */
    }
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        PixyCamBlocks[] blocks =  PixyCam2.getBlocks();
        if(blocks.length != 2){
            System.out.println("Error: " + blocks.length + " Blocks found");
        }else{
            int avgHeight = (blocks[0].height + blocks[1].height) / 2;
            int centerDistance = Math.abs(blocks[0].xCenter - blocks[1].xCenter);
            System.out.println("Height of blocks: " + avgHeight + " Distance Between: " + centerDistance);
        }
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        Robot.drive.setDefaultCommand(new DriveCommand());
        if (autonomousCommand != null) autonomousCommand.cancel();
        System.out.println("teleopInit being called");
        
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("right encoder", drive.getRightEncoder());
        SmartDashboard.putNumber("left encoder", drive.getLeftEncoder());
    }
}
