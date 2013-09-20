package com.synflow.sha256.step1;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.Source;
import com.synflow.sha256.step1.SHA256_step1;
import com.synflow.sha256.Expected;
import com.synflow.sha256.step1.RomK;

@SuppressWarnings("all")
final public class TestShaStep1 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TestShaStep1.class, args).run();
	}

	private final String _name;

	// ports

	private final Source source;
	private final SHA256_step1 sHA256_step1;
	private final Expected expected;
	private final RomK romK;

	/**
	 * constructor
	 */
	public TestShaStep1(String name, int flags) {
		this._name = name;

		// create input ports

		// create instances
		source = new Source("source", flags);
		sHA256_step1 = new SHA256_step1("sHA256_step1", flags);
		expected = new Expected("expected", flags);
		romK = new RomK("romK", flags);
	}

	@Override
	public void commit() {
		source.commit();
		sHA256_step1.commit();
		expected.commit();
		romK.commit();
		
	}

	@Override
	public void connect(Port... ports) {

		source.connect();
		sHA256_step1.connect(source.getStimulus(), romK.getDout());
		expected.connect(sHA256_step1.getHash());
		romK.connect(sHA256_step1.getKaddr());
	}

	@Override
	public void execute() {
		source.execute();
		sHA256_step1.execute();
		expected.execute();
		romK.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { source, sHA256_step1, expected, romK };
	}

	@Override
	public Port[] getInputs() {
		return new Port[] {  };
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] {  };
	}


	@Override
	public String toString() {
		return _name;
	}

}
