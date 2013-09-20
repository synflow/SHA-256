/*
 * Copyright (c) 2012-2013, Synflow SAS
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
package com.synflow.ram;

import com.synflow.runtime.Port;

/**
 * This class defines a dual-port RAM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Dual_Port_RAM extends AbstractRam {

	private Port rd_address;

	private Port wr_address;

	public Dual_Port_RAM(String name, int flags, int depth, int width) {
		super(name, flags, depth, width);
	}

	@Override
	public void connect(Port... ports) {
		this.rd_address = ports[0];
		this.wr_address = ports[1];
		this.data = ports[2];
		
		rd_address.connect();
		wr_address.connect();
		data.connect();
	}

	@Override
	public void execute() {
		if (data.available()) {
			writeDataToArray(wr_address.readInt());
		}

		readDataFromArray(rd_address.readInt());
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { data, rd_address, wr_address };
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { q };
	}

}