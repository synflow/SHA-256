-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.SHACommon by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : SHACommon.vhd
-- Author     : Nicolas
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
    return x and y xor not (x) and z;
  end Ch;
  
  impure function Maj(x : unsigned; y : unsigned; z : unsigned) return unsigned is
  begin
    return x and y xor x and z xor y and z;
  end Maj;
  
  impure function lcSigma0(x : unsigned) return unsigned is
  begin
    return x(com.synflow.models.ir.impl.ExprIntImpl@459ae9f6 (value: 32) - 1 downto 7) or (x & "0000000000000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@459ae9f6 (value: 32) - 1 downto 18) or (x & "00000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@459ae9f6 (value: 32) - 1 downto 3);
  end lcSigma0;
  
  impure function lcSigma1(x : unsigned) return unsigned is
  begin
    return x(com.synflow.models.ir.impl.ExprIntImpl@3d76392d (value: 32) - 1 downto 17) or (x & "000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@3d76392d (value: 32) - 1 downto 19) or (x & "0000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@3d76392d (value: 32) - 1 downto 10);
  end lcSigma1;
  
  impure function ucSigma0(x : unsigned) return unsigned is
  begin
    return x(com.synflow.models.ir.impl.ExprIntImpl@1ca3d8e1 (value: 32) - 1 downto 2) or (x & "000000000000000000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@1ca3d8e1 (value: 32) - 1 downto 13) or (x & "0000000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@1ca3d8e1 (value: 32) - 1 downto 22) or (x & "0000000000");
  end ucSigma0;
  
  impure function ucSigma1(x : unsigned) return unsigned is
  begin
    return x(com.synflow.models.ir.impl.ExprIntImpl@48f3aa77 (value: 32) - 1 downto 6) or (x & "00000000000000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@48f3aa77 (value: 32) - 1 downto 11) or (x & "000000000000000000000") xor x(com.synflow.models.ir.impl.ExprIntImpl@48f3aa77 (value: 32) - 1 downto 25) or (x & "0000000");
  end ucSigma1;
  

end package body SHACommon;
