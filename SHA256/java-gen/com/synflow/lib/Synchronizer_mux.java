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

import com.synflow.runtime.Entity;
import com.synflow.runtime.Port;

/**
 * This class defines a mux synchronizer.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Synchronizer_mux implements Entity {

	private Port din;

	private final Port din_send;

	private final Port dout;

	private final String name;

	private final int size;

	private final Synchronizer_ff sync_ff;

	/**
	 * Creates a synchronizer with the given name, flags, and stages.
	 * 
	 * @param name
	 *            name of this synchronizer
	 * @param flags
	 *            flags
	 * @param width
	 *            width
	 * @param stages
	 *            number of stages
	 */
	public Synchronizer_mux(String name, int _flags, int width, int stages) {
		this.name = name;
		this.size = width;
		this.sync_ff = new Synchronizer_ff("sync", _flags, stages);

		dout = new Port(this, "dout", width, _flags);
		din_send = new Port(this, "din_send", 1, _flags);
	}

	@Override
	public void commit() {
		dout.commit();
		sync_ff.commit();
	}

	@Override
	public void connect(Port... ports) {
		din = ports[0];
		din.connect();

		sync_ff.connect(din_send);
	}

	@Override
	public void execute() {
		// commit din_send immediately because this is a combinational assignment
		din_send.write(din.available());
		din_send.commit();

		sync_ff.execute();

		if (sync_ff.getDout().readBoolean()) {
			if (size > 64) {
				dout.write(din.readBigInteger());
			} else if (size > 32) {
				dout.write(din.readLong());
			} else {
				dout.write(din.readInt());
			}
		}
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