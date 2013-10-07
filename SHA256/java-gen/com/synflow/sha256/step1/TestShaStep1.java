package com.synflow.sha256.step1;

import java.io.IOException;
import java.math.BigInteger;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;
import com.synflow.runtime.Runner;
import com.synflow.sha256.Source;
import com.synflow.sha256.step1.TopShaStep1;
import com.synflow.sha256.Expected;

@SuppressWarnings("all")
final public class TestShaStep1 implements Entity {

	public static void main(String[] args) throws ReflectiveOperationException, IOException {
		new Runner(TestShaStep1.class, args).run();
	}

	private final String _name;

	// ports

	private final Source source;
	private final TopShaStep1 topShaStep1;
	private final Expected expected;

	/**
	 * constructor
	 */
	public TestShaStep1(String name, int _flags) {
		this._name = name;

		// create input ports

		// create instances
		source = new Source("source", _flags);
		topShaStep1 = new TopShaStep1("topShaStep1", _flags);
		expected = new Expected("expected", _flags);
	}

	@Override
	public void commit() {
		source.commit();
		topShaStep1.commit();
		expected.commit();
		
	}

	@Override
	public void connect(Port... ports) {

		source.connect();
		topShaStep1.connect(source.getStimulus());
		expected.connect(topShaStep1.getHash());
	}

	@Override
	public void execute() {
		source.execute();
		topShaStep1.execute();
		expected.execute();
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[] { source, topShaStep1, expected };
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
