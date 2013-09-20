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
package com.synflow.runtime;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines a VCD writer.
 * 
 * @author Matthieu Wipliez
 *
 */
public class VcdWriter {

	private static VcdWriter instance = new VcdWriter();

	public static VcdWriter getInstance() {
		return instance;
	}

	private Map<Port, String> map;

	private int oldTimestamp;

	private PrintWriter out;

	private int timestamp;

	private VcdWriter() {
		map = new HashMap<>();
	}

	void addValue(Port port, BigInteger value) {
		printTimestamp();
		printValue(port, value.toString(2));
	}

	void addValue(Port port, int value) {
		printTimestamp();
		if (port.getSize() == 1) {
			printValue(port, value != 0 ? '1' : '0');
		} else {
			printValue(port, Integer.toString(value, 2));
		}
	}

	void addValue(Port port, long value) {
		printTimestamp();
		printValue(port, Long.toString(value, 2));
	}

	public void flush() {
		out.flush();
	}

	private void printTimestamp() {
		if (timestamp != oldTimestamp) {
			out.println("#" + (timestamp * 10 + 105));
			oldTimestamp = timestamp;
		}
	}

	private void printValue(Port port, char value) {
		out.println(value + map.get(port));
	}

	private void printValue(Port port, String value) {
		out.println("b" + value + " " + map.get(port));
	}

	public void setFileName(String fileName) throws FileNotFoundException {
		out = new PrintWriter(fileName);
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public void writeHeader(Entity entity) {
		out.println("$date " + new SimpleDateFormat("").format(new Date())
				+ " $end");
		out.println("$version Synflow Studio $end");
		out.println("$timescale 1ns $end");

		writeScope(entity);
		out.println("$enddefinitions $end");
		out.println("#0");
	}

	private void writePort(Port port) {
		String identifier = port.toString();
		map.put(port, identifier);
		out.println("$var wire " + port.getSize() + " " + identifier + " "
				+ port.getName() + " $end");
	}

	private void writeScope(Entity entity) {
		out.println("$scope module " + entity + " $end");
		for (Port port : entity.getOutputs()) {
			writePort(port);
		}
		for (Entity child : entity.getChildren()) {
			writeScope(child);
		}
		out.println("$upscope $end");
	}

}
