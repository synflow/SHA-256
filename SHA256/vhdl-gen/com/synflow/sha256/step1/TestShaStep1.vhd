-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step1.TestShaStep1 by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : TestShaStep1.vhd
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
entity TestShaStep1 is
  port (
                                          -- Clock signal
  clock    : in  std_logic;

  reset_n  : in  std_logic);
end TestShaStep1;

------------------------------------------------------------------------------
architecture rtl_TestShaStep1 of TestShaStep1 is

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  -- Module : source
  signal source_stimulus      : std_logic_vector(31 downto 0);
  signal source_stimulus_send : std_logic;
  -- Module : romK
  signal romK_dout      : std_logic_vector(31 downto 0);
  -- Module : sHA256_step1
  signal sHA256_step1_hash      : std_logic_vector(255 downto 0);
  signal sHA256_step1_hash_send : std_logic;
  signal sHA256_step1_Kaddr      : std_logic_vector(5 downto 0);
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
    hash      => sHA256_step1_hash,
    hash_send => sHA256_step1_hash_send
  );
  
  romK : entity work.RomK
  port map (
    clock   => clock,
    reset_n => reset_n,
    addr      => sHA256_step1_Kaddr,
    dout      => romK_dout
  );
  
  sHA256_step1 : entity work.SHA256_step1
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg      => source_stimulus,
    msg_send => source_stimulus_send,
    Kin      => romK_dout,
    hash      => sHA256_step1_hash,
    hash_send => sHA256_step1_hash_send,
    Kaddr      => sHA256_step1_Kaddr
  );


end architecture rtl_TestShaStep1;
