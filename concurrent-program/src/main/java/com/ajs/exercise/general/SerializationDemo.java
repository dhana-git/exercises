package com.ajs.exercise.general;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationDemo {

	public static void main(String[] args) {
		Entity entity = new Entity("instanceField", "finalField");
		entity.superInstanceField = "Super";
		entity.transientField = "transientField";
		Entity.staticField = "staticField";
		serialize(entity);
		Entity.staticField = "staticFieldPostSer";
		Entity deserializedObj = deserialize("./ser-out");
		System.out.println("staticField:" + Entity.staticField);
		System.out.println("transientField:" + deserializedObj.transientField);
	}

	private static <T> void serialize(T object) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("./ser-out")));
			out.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static <T> T deserialize(String fileName) {
		T retObj = null;
		try {
			ObjectInputStream out = new ObjectInputStream(new FileInputStream(new File(fileName)));
			retObj = (T) out.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retObj;
	}
}

class SuperEntity implements Serializable {
	public String superInstanceField;
}

class Entity extends SuperEntity implements Serializable {
	public String instanceField;
	public transient String transientField;
	public static String staticField;
	public static final String staticFinalField = "staticFinalField";
	public final String finalField;

	public Entity() {
		System.out.println("Invoked Entity()");
		finalField = "finalField-initAtDefaultConst";
		instanceField = "instanceField-initAtDefaultConst";
		// transientField = "transientField-initAtDefaultConst";
	}

	public Entity(String instanceField, String finalField) {
		System.out.println("Invoked Entity(String instanceField, String finalField)");
		this.instanceField = instanceField;
		this.finalField = finalField;
	}

	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		in.defaultReadObject();
		staticField = (String) in.readObject(); // reading static field
		transientField = (String) in.readObject(); // writing transient field
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
		out.writeObject(staticField); // persisting static field
		out.writeObject(transientField); // persisting transient field
	}

	/*
	 * @Override public void writeExternal(ObjectOutput out) throws IOException { // out.writeObject(staticField); }
	 * 
	 * @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException { // staticField =
	 * (String) in.readObject(); }
	 */

}
