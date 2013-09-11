package com.synflow.crypto;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.crypto.Source;
import com.synflow.crypto.SHA256;
import com.synflow.crypto.Expected;

@SuppressWarnings("all")
final public class TestSha implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TestSha.class, args).run();
	}

	private final String _name;

	// ports

	private final Source source;
	private final SHA256 sha256;
	private final Expected expected;

	/**
	 * constructor
	 */
	public TestSha(String name, int flags) {
		this._name = name;

		// create input ports

		// create instances
		source = new Source("source", flags);
		sha256 = new SHA256("sha256", flags);
		expected = new Expected("expected", flags);
	}

	@Override
	public void commit() {
		source.commit();
		sha256.commit();
		expected.commit();
		
	}

	@Override
	public void connect(Port... ports) {

		source.connect();
		sha256.connect(source.getStimulus());
		expected.connect(sha256.getHash());
	}

	@Override
	public void execute() {
		source.execute();
		sha256.execute();
		expected.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { source, sha256, expected };
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
