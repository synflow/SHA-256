-------------------------------------------------------------------------------
-- Title      : Generated from SHA256 by Synflow Studio
-- Project    : SHA-256
--
-- File       : SHA256.tb.vhd
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
entity SHA256_tb is
end SHA256_tb;

-------------------------------------------------------------------------------
architecture arch_SHA256_tb of SHA256_tb is 

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
  signal msg       : std_logic_vector(31 downto 0);
  signal hash       : std_logic_vector(255 downto 0);
  signal hash_send  : std_logic;

  ---------------------------------------------------------------------------

begin

  SHA256 : entity work.SHA256
  port map (
    clock    => clock,
    reset_n    => reset_dut,
    msg   => msg,
    hash   => hash,
    hash_send   => hash_send
  );

  -- clock gen
  clock <= not clock after PERIOD_clock / 2;

  -- reset gen
  reset_n   <= '1' after INIT_RESET;

  process (reset_n, clock)
  begin
    if reset_n = '0' then
      msg <= (others => '0');
      --
    elsif rising_edge(clock) then
      if not endfile(fd_stim) then
        -- write values to input ports
        writeValue(fd_stim, msg);
      end if;
  
      if startRecv = '1' and not endfile(fd_trace) then
        -- check values on output ports
        checkValue(fd_trace, "hash", hash, hash_send);
      end if;
  
      startRecv <= reset_dut;
      reset_dut <= '1';
    end if;
  end process;

end architecture arch_SHA256_tb;
