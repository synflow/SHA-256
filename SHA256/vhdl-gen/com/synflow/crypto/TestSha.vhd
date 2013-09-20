-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.crypto.TestSha by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : TestSha.vhd
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
entity TestSha is
  port (
                                          -- Clock signal
  clock    : in  std_logic;

  reset_n  : in  std_logic);
end TestSha;

------------------------------------------------------------------------------
architecture rtl_TestSha of TestSha is

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  -- Module : source
  signal source_stimulus      : std_logic_vector(31 downto 0);
  signal source_stimulus_send : std_logic;
  -- Module : sha256
  signal sha256_hash      : std_logic_vector(255 downto 0);
  signal sha256_hash_send : std_logic;
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
  
  sha256 : entity work.SHA256
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg      => source_stimulus,
    msg_send => source_stimulus_send,
    hash      => sha256_hash,
    hash_send => sha256_hash_send
  );
  
  expected : entity work.Expected
  port map (
    clock   => clock,
    reset_n => reset_n,
    hash      => sha256_hash,
    hash_send => sha256_hash_send
  );


end architecture rtl_TestSha;
