package com.synflow.sha256.step1;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.step1.SHA256_step1;
import com.synflow.sha256.step1.RomK;

@SuppressWarnings("all")
final public class TopShaStep1 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TopShaStep1.class, args).run();
	}

	private final String _name;

	// ports
	private Port msg;

	private final SHA256_step1 sHA256_step1;
	private final RomK romK;

	/**
	 * constructor
	 */
	public TopShaStep1(String name, int flags) {
		this._name = name;

		// create input ports
		msg = new Port(this, "msg", 32, Port.SYNC | flags);

		// create instances
		sHA256_step1 = new SHA256_step1("sHA256_step1", flags);
		romK = new RomK("romK", flags);
	}

	@Override
	public void commit() {
		sHA256_step1.commit();
		romK.commit();
		
	}

	@Override
	public void connect(Port... ports) {
		this.msg = ports[0];

		sHA256_step1.connect(msg, romK.getDout());
		romK.connect(sHA256_step1.getKaddr());
	}

	@Override
	public void execute() {
		sHA256_step1.execute();
		romK.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { sHA256_step1, romK };
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { msg };
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { sHA256_step1.getHash() };
	}

	public Port getHash() {
		return sHA256_step1.getHash();
	}

	@Override
	public String toString() {
		return _name;
	}

}
