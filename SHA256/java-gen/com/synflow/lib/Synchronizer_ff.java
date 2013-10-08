/*
 * Copyright (c) 2013, Synflow SAS
 * All rights reserved.
 * 
 * REDISTRIBUTION of this file in source and binary forms, with or without
 * modification, is NOT permitted in any way.
 *
 * The use of this file in source and binary forms, with or without
 * modification, is permitted if you have a valid commercial license of
 * Synflow Studio.
 * If you do NOT have a valid license of Synflow Studio: you are NOT allowed
 * to use this file.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE
 */
package com.synflow.lib;

import java.util.BitSet;

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;

/**
 * This class defines a flip-flop synchronizer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Synchronizer_ff implements Entity {

	protected Port din;

	private final Port dout;

	private final BitSet ff;

	private final String name;

	private final int stages;

	/**
	 * Creates a synchronizer with the given name, flags, and stages.
	 * 
	 * @param name
	 *            name of this synchronizer
	 * @param flags
	 *            flags
	 * @param stages
	 *            number of stages
	 */
	public Synchronizer_ff(String name, int _flags, int stages) {
		this.name = name;
		// we use stages - 1 because 'dout' is a flip-flop too
		// so we count it as a stage
		this.stages = stages - 1;
		this.ff = new BitSet(stages);

		dout = new Port(this, "dout", 1, _flags);
	}

	@Override
	public void commit() {
		dout.commit();
	}

	@Override
	public void connect(Port... ports) {
		din = ports[0];
		din.connect();
	}

	@Override
	public void execute() {
		// N-bit shift register
		dout.write(ff.get(stages - 1));
		for (int i = stages - 1; i > 0; i--) {
			ff.set(i, ff.get(i - 1));
		}
		ff.set(0, din.readBoolean());
	}

	@Override
	public Entity[] getChildren() {
		return new Entity[0];
	}

	/**
	 * Returns the <code>dout</code> port.
	 * 
	 * @return the <code>dout</code> port
	 */
	public final Port getDout() {
		return dout;
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { din };
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { dout };
	}

	@Override
	public final String toString() {
		return getName();
	}

}