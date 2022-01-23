/* deprecated lamprey was fixed
package edu.greenblitz.bigRodika.commands.tests.singleModule;

import edu.greenblitz.bigRodika.commands.swervemodule.OpMode;
import edu.greenblitz.bigRodika.subsystems.SingleModule;
import edu.greenblitz.gblib.command.GBCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class FindLampreyValuesByNeo extends GBCommand {
	private RemoteCSVTarget target;
	private HashMap<Double, ArrayList<Double>> buffer;
	private HashSet<Integer> isItInThereYet;
	private double power;
	private int targetTicks;

	private int currentID;

	public FindLampreyValuesByNeo(double power, int id){
		this.power = power;
		buffer = new HashMap<>();
		isItInThereYet = new HashSet<>();
		this.currentID = id;
	}

	@Override
	public void initialize() {
		String fileName = "neoToLamprey" + Integer.toString(this.currentID);
		target = RemoteCSVTarget.initTarget(fileName, "neo value", "lamprey median");
		SingleModule.getInstance().getModule().setOpMode(OpMode.BY_POWER);
	}

	@Override
	public void execute() {

		SingleModule.getInstance().getModule().setAnglePower(power);
		SingleModule.getInstance().getModule().setIsLamprey(false);
		double neoRadians = SingleModule.getInstance().getModule().getAngle();
		int neoTicks =(SingleModule.getInstance().getModule().getAngleEncoderValue());
		SmartDashboard.putNumber("curr ticks", neoTicks);
		if(isItInThereYet.contains(targetTicks)){
			for (int i = 0; i < 252; i++) {
				if(!isItInThereYet.contains(i))
					targetTicks = i;
			}
		}
		SmartDashboard.putNumber("target ticks", targetTicks);

		SingleModule.getInstance().getModule().setIsLamprey(true);
		double lampreyVoltage = SingleModule.getInstance().getModule().getAngle();
		if (!buffer.containsKey(neoRadians)) {
			buffer.put(neoRadians, new ArrayList<>(15));
			isItInThereYet.add(neoTicks);
		}
		buffer.get(neoRadians).add(lampreyVoltage);

	}

	@Override
	public void end(boolean interrupted) {
		Object[] keys = buffer.keySet().toArray();
		SingleModule.getInstance().getModule().setAnglePower(0);
		for (int i = 0; i < buffer.size(); i++) {
			Collections.sort(buffer.get(keys[i]));
			target.report(Double.parseDouble(keys[i].toString()),
					buffer.get(keys[i]).get(buffer.get(keys[i]).size()/2),
					buffer.get(keys[i]).size());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static int toDegrees(double angle){
		return (int)(angle*360/(2*Math.PI));
	}

	public static double toRadians(int angle){
		return angle/360*(2*Math.PI);
	}

}*/
