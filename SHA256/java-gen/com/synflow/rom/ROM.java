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
package com.synflow.rom;

import java.math.BigInteger;

import com.synflow.ram.AbstractRam;
import com.synflow.runtime.Port;

/**
 * This class defines a synchronous ROM.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ROM extends AbstractRam {

	private static int getDepth(int length) {
		return BigInteger.valueOf(length - 1).bitLength();
	}

	private Port address;

	public ROM(String name, int flags, int width, BigInteger[] contents) {
		super(name, flags, getDepth(contents.length), width);

		for (int i = 0; i < contents.length; i++) {
			contentsBigInteger[i] = contents[i];
		}
	}

	public ROM(String name, int flags, int width, int[] contents) {
		super(name, flags, getDepth(contents.length), width);

		for (int i = 0; i < contents.length; i++) {
			contentsInt[i] = contents[i];
		}
	}

	public ROM(String name, int flags, int width, long[] contents) {
		super(name, flags, getDepth(contents.length), width);

		for (int i = 0; i < contents.length; i++) {
			contentsLong[i] = contents[i];
		}
	}

	@Override
	public void connect(Port... ports) {
		this.address = ports[0];
		address.connect();
	}

	@Override
	public void execute() {
		int addr = address.readInt();
		readDataFromArray(addr);
	}

	@Override
	public Port[] getInputs() {
		return new Port[] { address };
	}

	@Override
	public Port[] getOutputs() {
		return new Port[] { q };
	}

}