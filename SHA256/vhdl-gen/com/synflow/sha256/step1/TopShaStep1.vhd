-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step1.TopShaStep1 by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : TopShaStep1.vhd
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
entity TopShaStep1 is
  port (
                                          -- Clock signal
  clock    : in  std_logic;

  reset_n  : in  std_logic;
  msg       : in std_logic_vector(31 downto 0);
  msg_send  : in std_logic;
  hash       : out std_logic_vector(255 downto 0);
  hash_send  : out std_logic);
end TopShaStep1;

------------------------------------------------------------------------------
architecture rtl_TopShaStep1 of TopShaStep1 is

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  -- Module : sHA256_step1
  signal sHA256_step1_Kaddr      : std_logic_vector(5 downto 0);
  -- Module : rom
  signal rom_q      : std_logic_vector(31 downto 0);
  ---------------------------------------------------------------------------

begin

  ---------------------------------------------------------------------------
  -- Actors and Networks 
  ---------------------------------------------------------------------------
  sHA256_step1 : entity work.SHA256_step1
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg      => msg,
    msg_send => msg_send,
    Kin      => rom_q,
    hash      => hash,
    hash_send => hash_send,
    Kaddr      => sHA256_step1_Kaddr
  );
  
  rom : entity work.TopShaStep1_rom
  port map (
    clock   => clock,
    reset_n => reset_n,
    address      => sHA256_step1_Kaddr,
    q      => rom_q
  );


end architecture rtl_TopShaStep1;
