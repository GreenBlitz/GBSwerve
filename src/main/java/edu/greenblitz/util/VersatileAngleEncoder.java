package edu.greenblitz.util;

import com.revrobotics.CANSparkMax;
import edu.greenblitz.gblib.encoder.SparkEncoder;
import edu.wpi.first.wpilibj.AnalogInput;

import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.Modules.TICKS_PER_ROTATION;
import static edu.greenblitz.bigRodika.RobotMap.Limbo2.Chassis.SwerveModule.NORMALIZER_SPARK;

public class VersatileAngleEncoder {
	private AnalogInput lamprey;
	private SparkEncoder neoEncoder;
	private boolean isLamprey = false;

	public VersatileAngleEncoder(int lampreyID, CANSparkMax motor){
		lamprey = new AnalogInput(lampreyID);   //TODO: Anda: Add variable specific to each object that stores the maximum of ADC that each encoder gave.
		lamprey.setAverageBits(2);
		neoEncoder = new SparkEncoder(NORMALIZER_SPARK, motor);
		neoEncoder.reset();
	}

	public void setLamprey(boolean lamprey) {
		isLamprey = lamprey;
	}

	public boolean isLamprey() {
		return isLamprey;
	}

	public int getEncoderValue(){
		if(isLamprey) {
			return lamprey.getValue();
		}else{
			return ((neoEncoder.getRawTicks()) % (TICKS_PER_ROTATION * 6));
		}
	}
	public double getAngle(){
		if(isLamprey) {
			return (((double)lamprey.getAverageValue()) / 4076)  * (2*Math.PI);
		}else{
			return ((neoEncoder.getRawTicks()) % (TICKS_PER_ROTATION * 6)) / (TICKS_PER_ROTATION * 6.0) * 2 * Math.PI;
		}
	}

	public double getAngleByNeo(){
		return ((neoEncoder.getRawTicks()) % (TICKS_PER_ROTATION * 6)) / (TICKS_PER_ROTATION * 6.0) * 2 * Math.PI;
	}

	public double getAngleByLamprey(){
			return (((double)lamprey.getAverageValue()) / 4076)  * (2*Math.PI);
		}

	public int getLampreyADCValue(){
		return lamprey.getAverageValue();
	}
}
