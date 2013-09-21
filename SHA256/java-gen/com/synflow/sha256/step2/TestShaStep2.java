package com.synflow.sha256.step2;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.Source;
import com.synflow.sha256.step2.TopShaStep2;
import com.synflow.sha256.Expected;

@SuppressWarnings("all")
final public class TestShaStep2 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TestShaStep2.class, args).run();
	}

	private final String _name;

	// ports

	private final Source source;
	private final TopShaStep2 topShaStep2;
	private final Expected expected;

	/**
	 * constructor
	 */
	public TestShaStep2(String name, int flags) {
		this._name = name;

		// create input ports

		// create instances
		source = new Source("source", flags);
		topShaStep2 = new TopShaStep2("topShaStep2", flags);
		expected = new Expected("expected", flags);
	}

	@Override
	public void commit() {
		source.commit();
		topShaStep2.commit();
		expected.commit();
		
	}

	@Override
	public void connect(Port... ports) {

		source.connect();
		topShaStep2.connect(source.getStimulus());
		expected.connect(topShaStep2.getHash());
	}

	@Override
	public void execute() {
		source.execute();
		topShaStep2.execute();
		expected.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { source, topShaStep2, expected };
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
