package edu.greenblitz.util;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK;

public class VersatileAngleEncoder {
	private AnalogInput lamprey;
	private SparkEncoder neoEncoder;
	private boolean isLampray;

	public VersatileAngleEncoder(int lampreyID, CANSparkMax motor){
		lamprey = new AnalogInput(lampreyID);
		neoEncoder = new SparkEncoder(NORMALIZER_SPARK, motor);
	}

	public void setLampray(boolean lampray) {
		isLampray = lampray;
	}

	public double getEncoderValue(){
		if(isLampray) {
			return lamprey.getVoltage();
		}else{
			return neoEncoder.getRawTicks();
		}
	}

}
