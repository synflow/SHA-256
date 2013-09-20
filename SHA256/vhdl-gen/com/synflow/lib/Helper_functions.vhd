-------------------------------------------------------------------------------
-- Copyright (c) 2013, Synflow SAS
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
-- Title      : Adds VHDL-2008 like constructs in a VHDL'93 compatible way
-- Author     : Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
use std.textio.all;
-------------------------------------------------------------------------------


-------------------------------------------------------------------------------
-- 
-------------------------------------------------------------------------------
package Helper_functions is

  function to_boolean(b : std_logic) return boolean;
  function to_std_logic(b : boolean) return std_logic;
  function to_string_93(b : bit) return string;
  function to_hstring_93(b : bit_vector) return string;

end Helper_functions;

-------------------------------------------------------------------------------
-- Body of package
-------------------------------------------------------------------------------
package body Helper_functions is

  -----------------------------------------------------------------------------
  -- Built-in constants and functions
  -----------------------------------------------------------------------------

  function to_boolean(b : std_logic) return boolean is
  begin
    return b = '1';
  end;

  function to_std_logic(b : boolean) return std_logic is
  begin
    if b then
      return '1';
    else
      return '0';
    end if;
  end;

  function to_string_93(b : bit) return string is begin
    -- rtl_synthesis off
    -- synthesis translate_off
    return to_string(b);
    -- synthesis translate_on
    -- rtl_synthesis on
    return "";
  end;

  function to_hstring_93(b : bit_vector) return string is begin
    -- rtl_synthesis off
    -- synthesis translate_off
    return to_hstring(b);
    -- synthesis translate_on
    -- rtl_synthesis on
    return "";
  end;

end Helper_functions;
