-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.Expected by Synflow Studio
-- Project    : SHA-256
--
-- File       : Expected.vhd
-- Author     : Matthieu
-- Standard   : VHDL'93
--
-------------------------------------------------------------------------------
-- Copyright (c) 2013
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

library std;
use std.textio.all;

library work;
use work.Helper_functions.all;

-------------------------------------------------------------------------------
entity Expected is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    hash       : in std_logic_vector(255 downto 0);
    hash_send  : in std_logic);
end Expected;


-------------------------------------------------------------------------------
architecture rtl_Expected of Expected is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------


  -----------------------------------------------------------------------------
  -- FSM
  -----------------------------------------------------------------------------
  type FSM_type is (s_Expected_0, s_Expected_1);
  signal FSM : FSM_type;

  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  Expected_execute : process (reset_n, clock) is
    variable dut_hash : unsigned(255 downto 0);
    variable local_hash : unsigned(255 downto 0);
    variable hash_in  : unsigned(255 downto 0);
  begin
    if reset_n = '0' then
      FSM    <= s_Expected_0;
    --
    elsif rising_edge(clock) then
      --
      case FSM is
        when s_Expected_0 =>
          if to_boolean(hash_send and '1') then
            -- Body of Expected_0 (line 12)
            hash_in := unsigned(hash);
            local_hash := hash_in;
            dut_hash := (local_hash);
            write(output, "read hash from dut: "
             & to_hstring_93(to_bitvector(std_logic_vector((dut_hash))))
             & LF);
            assert (dut_hash) = x"ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad" report "(dut_hash) = x'ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad'" severity failure;
            write(output, "assertion passed"
             & LF);
            FSM <= s_Expected_1;
          end if;
        
        when s_Expected_1 =>
      end case;
    end if;
  end process Expected_execute;

end architecture rtl_Expected;

