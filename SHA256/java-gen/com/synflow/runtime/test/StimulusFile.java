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
package com.synflow.runtime.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.synflow.runtime.Port;

/**
 * This class defines a test file used by stimulus and expected sets in
 * testbenches.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class StimulusFile {

	private int currentIndex;

	private String nextLine;

	private List<Port> ports;

	private BufferedReader reader;

	public StimulusFile(String fileNames, String fileValues, Port... inputs)
			throws IOException {
		initializePorts(fileNames, inputs);
		initializeValues(fileValues);
	}

	public void checkLine() {
		currentIndex = 0;
		for (Port port : ports) {
			Number value = readOneValue();
			if (port.isSync()) {
				boolean expectsValue = readOneValue().intValue() != 0;
				if (!expectsValue) {
					if (port.available()) {
						System.err.println("got data from sync port " + port
								+ " (expected nothing)");
						System.exit(1);
					}

					// not expecting a value at this cycle, skip to next port
					continue;
				}

				if (!port.available()) {
					System.err.println("expected data on sync port " + port
							+ " (got nothing)");
					System.exit(1);
				}
			}

			int size = port.getSize();
			if (size > 64) {
				BigInteger actual = port.readBigInteger();
				BigInteger expected = (BigInteger) value;
				if (!actual.equals(expected)) {
					System.err.println("error on " + port + ": expected "
							+ expected + ", got " + actual);
					System.exit(1);
				}
			} else if (size > 32) {
				long actual = port.readLong();
				long expected = value.longValue();
				if (actual != expected) {
					System.err.println("error on " + port + ": expected "
							+ expected + ", got " + actual);
					System.exit(1);
				}
			} else {
				int actual = port.readInt();
				int expected = value.intValue();
				if (actual != expected) {
					System.err.println("error on " + port + ": expected "
							+ expected + ", got " + actual);
					System.exit(1);
				}
			}
		}
	}

	public void commit() {
		for (Port port : ports) {
			port.commit();
		}
	}

	public boolean hasNext() {
		try {
			nextLine = reader.readLine();
		} catch (IOException e) {
			return false;
		}
		return nextLine != null && !nextLine.isEmpty();
	}

	private void initializePorts(String fileNames, Port[] inputs)
			throws IOException {
		Path path = Paths.get(fileNames);
		Charset cs = Charset.defaultCharset();

		List<String> lines = Files.readAllLines(path, cs);
		Map<String, Port> candidates = new HashMap<>(inputs.length);
		for (Port port : inputs) {
			candidates.put(port.getName(), port);
		}

		ports = new ArrayList<>();
		for (String line : lines) {
			int indexParen = line.indexOf('(');
			String name;
			if (indexParen == -1) {
				name = line;
			} else {
				name = line.substring(0, indexParen);
			}

			// port will be null when the 'send' signal is read
			Port port = candidates.remove(name);
			if (port != null) {
				ports.add(port);
			}
		}
	}

	private void initializeValues(String fileValues) throws IOException {
		Path path = Paths.get(fileValues);
		Charset cs = Charset.defaultCharset();
		reader = Files.newBufferedReader(path, cs);
	}

	private BigInteger readIntegerValue(String base) {
		int radix;
		BigInteger num = BigInteger.ZERO;
		boolean negative = false;

		if ("signed".equals(base)) {
			radix = 10;

			char ch = nextLine.charAt(currentIndex);
			if (ch == '-') {
				negative = true;
				currentIndex++;
			}
		} else {
			radix = Integer.parseInt(base);
		}
		BigInteger radixBig = BigInteger.valueOf(radix);

		while (currentIndex < nextLine.length()) {
			char ch = nextLine.charAt(currentIndex);
			currentIndex++;
			int digit = Character.digit(ch, radix);
			if (digit == -1) {
				break;
			}

			num = num.multiply(radixBig).add(BigInteger.valueOf(digit));
		}

		if (negative) {
			return num.negate();
		} else {
			return num;
		}
	}

	public void readLine() {
		currentIndex = 0;
		for (Port port : ports) {
			Number value = readOneValue();

			// for sync ports, read value for the 'send' signal
			boolean write;
			if (port.isSync()) {
				write = readOneValue().intValue() != 0;
			} else {
				write = true;
			}

			if (write) {
				int size = port.getSize();
				if (size > 64) {
					port.write((BigInteger) value);
				} else if (size > 32) {
					port.write(value.longValue());
				} else {
					port.write(value.intValue());
				}
			}
		}
	}

	private Number readOneValue() {
		while (currentIndex < nextLine.length()) {
			if (!Character.isWhitespace(nextLine.charAt(currentIndex))) {
				break;
			}

			currentIndex++;
		}

		int indexPound = nextLine.indexOf('#', currentIndex);
		if (indexPound == -1) {
			return null;
		}

		String base = nextLine.substring(currentIndex, indexPound);
		currentIndex = indexPound + 1;
		if ("float".equals(base)) {
			System.out.println("float not yet implemented");
		} else {
			return readIntegerValue(base);
		}

		return null;
	}

}
