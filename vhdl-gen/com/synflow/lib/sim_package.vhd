-------------------------------------------------------------------------------
-- Copyright (c) 2012-2013, Synflow SAS
-- All rights reserved.
-- 
-- REDISTRIBUTION of this file in source and binary forms, with or without
-- modification, is NOT permitted in any way.
--
-- The use of this file in source and binary forms, with or without
-- modification, is permitted if you have a valid commercial license of
-- Synflow Studio.
-- If you do NOT have a valid license of Synflow Studio: you are NOT allowed
-- to use this file.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
-- Title      : Simulation package
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Created from the package written by Sarma Nedunuri (sarma.nedunuri@gmail.com)
-- see http://www.velocityreviews.com/forums/t55417-read-from-file.html
-- Standard   : VHDL'93
-- SIMULATION ONLY
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use std.textio.all;
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
-- 
-------------------------------------------------------------------------------
package sim_package is

  type fd is file of character;

  procedure checkValue(file f : fd; name : string; actual : in std_logic);
  procedure checkValue(file f : fd; name : string; actual : in std_logic_vector);
  procedure checkValue(file f : fd; name : string; actual : in std_logic; actual_send : in std_logic);
  procedure checkValue(file f : fd; name : string; actual : in std_logic_vector; actual_send : in std_logic);

  procedure writeValue(file f : fd; signal target : out std_logic);
  procedure writeValue(file f : fd; signal target : out std_logic_vector);
  procedure writeValue(file f : fd; signal target : out std_logic; signal target_send : out std_logic);
  procedure writeValue(file f : fd; signal target : out std_logic_vector; signal target_send : out std_logic);
  
end sim_package;


-------------------------------------------------------------------------------
-- Body of package
-------------------------------------------------------------------------------
package body sim_package is

  function isSpace(c : character) return boolean is
  begin
    return c = ' ' or c = ht or c = cr or c = lf;
  end function;

  procedure readBase(file f : fd; outputString : out string; valid : out boolean) is
    variable c : character;
    variable i : integer;
  begin
    -- skip space character from file
    while not endfile(f) loop
      read(f, c);
      exit when not isSpace(c);
    end loop;

    -- copy characters until end of file or # character encountered
    i := 0;
    while not endfile(f) loop
      i := i + 1;
      outputString(i) := c;

      read(f, c);
      exit when c = '#';
    end loop;

    -- result is valid when we read at least one character
    valid := i > 0;
  end procedure;

  function atoi(str : string) return integer is
    variable n, temp : integer;
    variable c : character;
  begin
    temp := 0;
    for i in str'range loop
      c := str(i);
      exit when c < '0' or c > '9';

      n := character'pos(c) - character'pos('0');
      temp := temp * 10 + n;
    end loop;
    return temp;
  end function;

  procedure readInteger(file f : fd; radix : integer; result : out unsigned; valid : out boolean) is
    variable c : character;
    variable negate : boolean;
    variable i, n : integer;
    variable temp : unsigned(result'length - 1 downto 0);
  begin
    temp := (others => '0');
    
    -- read first character
    read(f, c);
    if c = '-' then
      negate := true;
      read(f, c);
    else
      negate := false;
    end if;

    -- compute number until end of file or space character encountered
    i := 0;
    while not endfile(f) loop
      i := i + 1;
      
      if c >= '0' and c <= '9' then
        n := character'pos(c) - character'pos('0');
      elsif c >= 'a' and c <= 'z' then
        n := 10 + character'pos(c) - character'pos('a');
      elsif c >= 'A' and c <= 'Z' then
        n := 10 + character'pos(c) - character'pos('A');
      else
        n := 0;
      end if;

      temp := resize(temp * radix + n, result'length);
      
      read(f, c);
      exit when isSpace(c);
    end loop;
    
    -- result is valid when we read at least one character
    valid := i > 0;
    
    if negate then
      result := 0 - temp;
    else
      result := temp;
    end if;
  end procedure;

  procedure readValue(file f : fd; result : out std_logic_vector; valid : out boolean) is
    variable temp : unsigned(result'length - 1 downto 0);
    variable valid_i : boolean;
    variable radix : integer;
    variable baseString : string(1 to 6);
  begin
    readBase(f, baseString, valid_i);
    valid := valid_i;

    if valid_i then
      if baseString = ("float" & nul) then
        assert false report "Error: float not yet implemented" severity error;
      elsif baseString = "signed" then
        readInteger(f, 10, temp, valid);
        result := std_logic_vector(temp);
      else
        radix := atoi(baseString);
        readInteger(f, radix, temp, valid);
        result := std_logic_vector(temp);
      end if;
    end if;
  end procedure;
  
  -----------------------------------------------------------------------------
  --
  -----------------------------------------------------------------------------
  procedure checkValue(file f : fd; name : string; actual : in std_logic) is
    variable temp : std_logic_vector(6 downto 0) := (0 => actual, others => '0');
  begin
    checkValue(f, name, temp);
  end procedure;

  procedure checkValue(file f : fd; name : string; actual : in std_logic_vector) is
    variable expected : std_logic_vector(actual'length - 1 downto 0);
    variable valid : boolean;
  begin
    readValue(f, expected, valid);
    if valid then
      assert expected = actual
      report "expected " & to_hstring(to_bitvector(expected)) & " on port " & name & ", got " & to_hstring(to_bitvector(actual))
      severity error;
    end if;
  end procedure;
  
  -----------------------------------------------------------------------------
  --
  -----------------------------------------------------------------------------
  procedure checkValue(file f : fd; name : string; actual : in std_logic; actual_send : in std_logic) is
    variable temp : std_logic_vector(6 downto 0) := (0 => actual, others => '0');
  begin
    checkValue(f, name, temp, actual_send);
  end procedure;

  procedure checkValue(file f : fd; name : string; actual : in std_logic_vector; actual_send : in std_logic) is
    variable expected : std_logic_vector(actual'length - 1 downto 0);
    variable expected_send : std_logic_vector(6 downto 0);
    variable valid : boolean;
  begin
    readValue(f, expected, valid);
    if valid then
      readValue(f, expected_send, valid);

      assert valid;
      assert expected_send(0) = actual_send
      report "expected " & to_hstring(to_bitvector(expected_send)) & ", got " & std_logic'image(actual_send)
      severity error;

      if expected_send(0) = '1' then
        assert expected = actual
        report "expected " & to_hstring(to_bitvector(expected)) & " on port " & name & ", got " & to_hstring(to_bitvector(actual))
        severity error;
      end if;
    end if;
  end procedure;
  
  -----------------------------------------------------------------------------
  --
  -----------------------------------------------------------------------------
  procedure writeValue(file f : fd; signal target : out std_logic_vector) is
    variable result : std_logic_vector(target'length - 1 downto 0);
    variable valid : boolean;
  begin
    readValue(f, result, valid);
    if valid then
      target <= result;
    end if;
  end procedure;

  procedure writeValue(file f : fd; signal target : out std_logic) is
    variable result : std_logic_vector(6 downto 0);
    variable valid : boolean;
  begin
    readValue(f, result, valid);
    if valid then
      target <= result(0);
    end if;
  end procedure;

  procedure writeValue(file f : fd; signal target : out std_logic; signal target_send : out std_logic) is
  begin
    writeValue(f, target);
    writeValue(f, target_send);
  end procedure;

  procedure writeValue(file f : fd; signal target : out std_logic_vector; signal target_send : out std_logic) is
  begin
    writeValue(f, target);
    writeValue(f, target_send);
  end procedure;

end sim_package;
