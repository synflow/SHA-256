-------------------------------------------------------------------------------
-- Title      : Generated from TestSha by Synflow Studio
-- Project    : SHA-256
--
-- File       : TestSha.tb.vhd
-- Author     : Matthieu
-- Standard   : VHDL'93
--
-------------------------------------------------------------------------------
-- Copyright (c) 2013
-------------------------------------------------------------------------------


------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_textio.all;
use ieee.numeric_std.all;

library std;
use std.textio.all;

library work;
use work.sim_package.all;

-------------------------------------------------------------------------------
entity TestSha_tb is
end TestSha_tb;

-------------------------------------------------------------------------------
architecture arch_TestSha_tb of TestSha_tb is 

  ---------------------------------------------------------------------------
  -- Signal & Constants
  ---------------------------------------------------------------------------
  type severity_level is (note, warning, error, failure);

  constant INIT_RESET : time := 10 * 10 ns;

  signal reset_n : std_logic := '0';
  signal reset_dut : std_logic := '0';
  signal startRecv : std_logic := '0';

  file fd_stim  : fd open read_mode is "stimSigValues.txt";
  file fd_trace : fd open read_mode is "traceSigValues.txt";

  -- Clocks
  constant PERIOD_clock      : time := 10 ns;
  signal clock       : std_logic := '1';

  -- Ports

  ---------------------------------------------------------------------------

begin

  TestSha : entity work.TestSha
  port map (
    clock    => clock,
    reset_n    => reset_dut
  );

  -- clock gen
  clock <= not clock after PERIOD_clock / 2;

  -- reset gen
  reset_n   <= '1' after INIT_RESET;

  process (reset_n, clock)
  begin
    if reset_n = '0' then
      --
    elsif rising_edge(clock) then
      if not endfile(fd_stim) then
        -- write values to input ports
      end if;
  
      if startRecv = '1' and not endfile(fd_trace) then
        -- check values on output ports
      end if;
  
      startRecv <= reset_dut;
      reset_dut <= '1';
    end if;
  end process;

end architecture arch_TestSha_tb;
