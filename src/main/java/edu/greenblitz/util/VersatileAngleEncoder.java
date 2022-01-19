package edu.greenblitz.util;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.VOLTAGE_TO_ROTATIONS;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK;

public class VersatileAngleEncoder {
	private AnalogInput lamprey;
	private SparkEncoder neoEncoder;
	private boolean isLampray = false;

	public VersatileAngleEncoder(int lampreyID, CANSparkMax motor){
		lamprey = new AnalogInput(lampreyID);
		neoEncoder = new SparkEncoder(NORMALIZER_SPARK, motor);
	}

	public void setLampray(boolean lampray) {
		isLampray = lampray;
	}

	public boolean isLampray() {
		return isLampray;
	}

	public double getEncoderValue(){
		if(isLampray) {
			return lamprey.getVoltage();
		}else{
			return neoEncoder.getRawTicks();
		}
	}
	public double getAngle(){
		if(isLampray) {
			return lamprey.getVoltage() * 3.3/(2*Math.PI);
		}else{
			return ((neoEncoder.getRawTicks()) % VOLTAGE_TO_ROTATIONS) / VOLTAGE_TO_ROTATIONS * 2 * Math.PI;
		}
	}

}
