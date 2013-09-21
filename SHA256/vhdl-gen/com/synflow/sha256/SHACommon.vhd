-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.SHACommon by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : SHACommon.vhd
-- Author     : Matthieu
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2013
-------------------------------------------------------------------------------

------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library std;
use std.textio.all;

library work;
use work.Helper_functions.all;

------------------------------------------------------------------------------
package SHACommon is

  ---------------------------------------------------------------------------
  -- Declaration of constants
  ---------------------------------------------------------------------------

  ---------------------------------------------------------------------------
  -- Declaration of functions
  ---------------------------------------------------------------------------
  impure function Ch(x : unsigned; y : unsigned; z : unsigned) return unsigned;
  impure function Maj(x : unsigned; y : unsigned; z : unsigned) return unsigned;
  impure function lcSigma0(x : unsigned) return unsigned;
  impure function lcSigma1(x : unsigned) return unsigned;
  impure function ucSigma0(x : unsigned) return unsigned;
  impure function ucSigma1(x : unsigned) return unsigned;

end SHACommon;

------------------------------------------------------------------------------
package body SHACommon is

  ---------------------------------------------------------------------------
  -- Implementation of functions
  ---------------------------------------------------------------------------
  impure function Ch(x : unsigned; y : unsigned; z : unsigned) return unsigned is
  begin
    return (((x) and (y)) xor ((not ((x))) and (z)));
  end Ch;
  
  impure function Maj(x : unsigned; y : unsigned; z : unsigned) return unsigned is
  begin
    return ((((x) and (y)) xor ((x) and (z))) xor ((y) and (z)));
  end Maj;
  
  impure function lcSigma0(x : unsigned) return unsigned is
  begin
    return resize(((resize(x(31 downto 7), 57) or ((x & "0000000000000000000000000"))) xor resize(resize(x(31 downto 18), 46) or ((x & "00000000000000")), 57)) xor resize(x(31 downto 3), 57), 32);
  end lcSigma0;
  
  impure function lcSigma1(x : unsigned) return unsigned is
  begin
    return resize(((resize(x(31 downto 17), 47) or ((x & "000000000000000"))) xor resize(resize(x(31 downto 19), 45) or ((x & "0000000000000")), 47)) xor resize(x(31 downto 10), 47), 32);
  end lcSigma1;
  
  impure function ucSigma0(x : unsigned) return unsigned is
  begin
    return resize(((resize(x(31 downto 2), 62) or ((x & "000000000000000000000000000000"))) xor resize(resize(x(31 downto 13), 51) or ((x & "0000000000000000000")), 62)) xor resize(resize(x(31 downto 22), 42) or ((x & "0000000000")), 62), 32);
  end ucSigma0;
  
  impure function ucSigma1(x : unsigned) return unsigned is
  begin
    return resize(((resize(x(31 downto 6), 58) or ((x & "00000000000000000000000000"))) xor resize(resize(x(31 downto 11), 53) or ((x & "000000000000000000000")), 58)) xor resize(resize(x(31 downto 25), 39) or ((x & "0000000")), 58), 32);
  end ucSigma1;
  

end package body SHACommon;
