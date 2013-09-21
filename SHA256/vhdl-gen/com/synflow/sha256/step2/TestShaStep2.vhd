-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.TestShaStep2 by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : TestShaStep2.vhd
-- Author     : Matthieu
-- Standard   : VHDL'93
-------------------------------------------------------------------------------
-- Copyright (c) 2013
-------------------------------------------------------------------------------

-------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

------------------------------------------------------------------------------
entity TestShaStep2 is
  port (
                                          -- Clock signal
  clock    : in  std_logic;

  reset_n  : in  std_logic);
end TestShaStep2;

------------------------------------------------------------------------------
architecture rtl_TestShaStep2 of TestShaStep2 is

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  -- Module : source
  signal source_stimulus      : std_logic_vector(31 downto 0);
  signal source_stimulus_send : std_logic;
  -- Module : topShaStep2
  signal topShaStep2_hash      : std_logic_vector(255 downto 0);
  signal topShaStep2_hash_send : std_logic;
  ---------------------------------------------------------------------------

begin

  ---------------------------------------------------------------------------
  -- Actors and Networks 
  ---------------------------------------------------------------------------
  source : entity work.Source
  port map (
    clock   => clock,
    reset_n => reset_n,
    stimulus      => source_stimulus,
    stimulus_send => source_stimulus_send
  );
  
  expected : entity work.Expected
  port map (
    clock   => clock,
    reset_n => reset_n,
    hash      => topShaStep2_hash,
    hash_send => topShaStep2_hash_send
  );
  
  topShaStep2 : entity work.TopShaStep2
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg      => source_stimulus,
    msg_send => source_stimulus_send,
    hash      => topShaStep2_hash,
    hash_send => topShaStep2_hash_send
  );


end architecture rtl_TestShaStep2;
