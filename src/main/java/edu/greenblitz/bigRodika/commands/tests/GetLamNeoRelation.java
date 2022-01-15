package edu.greenblitz.bigRodika.commands.tests;

import edu.greenblitz.bigRodika.commands.chassis.ChassisCommand;
import edu.greenblitz.bigRodika.subsystems.Chassis;
import edu.greenblitz.gblib.command.GBCommand;

import java.io.*;
import java.util.HashMap;

public class GetLamNeoRelation extends GBCommand {
	private int id;
	private int numOfTicks;
	private HashMap<Integer, Double> tickToVoltage;
	private int lastMotorRead;
	private int currentMotorRead;


	public GetLamNeoRelation(int id) {
		this.id = id;
		this.numOfTicks = 0;
		this.tickToVoltage = new HashMap<>();
	}

	@Override
	public void initialize() {
		this.currentMotorRead = Chassis.getInstance().getSwerveModules()[id].getRawTicks();
		//Setup recording
	}

	@Override
	public void execute() {
		System.out.println(numOfTicks);
		Chassis.getInstance().moveMotor(id, 0, 0.02, false);
		int ticks = Chassis.getInstance().getSwerveModules()[id].getRawTicks();
		double volt = Chassis.getInstance().getSwerveModules()[id].getAngleEncoderValue();
		if (numOfTicks < ticks) {
			tickToVoltage.put(ticks, volt);
			lastMotorRead = currentMotorRead;
			currentMotorRead = ticks;
			int diff = currentMotorRead - lastMotorRead;
			if (lastMotorRead > currentMotorRead) {
				diff += 42;
			}

			this.numOfTicks += diff;
		}

		System.out.println("NUM OF TICKS: " + numOfTicks);
		System.out.println("CURRENT MOTOR READ: " + currentMotorRead);
		System.out.println("LAST MOTOR READ: " + lastMotorRead);
	}

	@Override
	public void end(boolean interrupted) {
		System.out.println(interrupted);
		Chassis.getInstance().stopMotor(id);
		serializeHashMap(tickToVoltage);
	}

	@Override
	public boolean isFinished() {
		System.out.println(numOfTicks);
		return numOfTicks >= 252; //num of ticks in a cycle
	}

	//todo: make sure the all is good
	private <T, K> void serializeHashMap(HashMap<T, K> myMap) {
		try {
			//when u run this code make sure to know the dest for the file.
			FileOutputStream myFileOutStream = new FileOutputStream("/home/lvuser/ticksToVoltageArchive.gb");
			ObjectOutputStream myObjectOutStream = new ObjectOutputStream(myFileOutStream);
			myObjectOutStream.writeObject(myMap);//should check that writeObject will work recursively (we deal with HashMap in HashMap)
			//asaf said it will be find and it will work recursively
			myObjectOutStream.close();
			myFileOutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//we copied those functions from gf"g, link:
	//https://www.geeksforgeeks.org/how-to-serialize-hashmap-in-java/
	@SuppressWarnings("unchecked")
	private HashMap<Integer, Double> deserializeHashMap(String fileName) {
		try {
			FileInputStream fileInput = new FileInputStream("/src/main/resources/".concat(fileName).concat("txt"));
			ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			HashMap<Integer, Double> myMap = (HashMap<Integer, Double>) objectInput.readObject();
			objectInput.close();
			fileInput.close();
			return myMap;
		} catch (IOException obj1) {
			obj1.printStackTrace();
			return null;
		} catch (ClassNotFoundException obj2) {
			System.out.println("Class not found");
			obj2.printStackTrace();
			return null;
		}
	}

}
