-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.TopShaStep2 by Synflow Studio
-- Project    : SHA-256
-------------------------------------------------------------------------------
-- File       : TopShaStep2.vhd
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
entity TopShaStep2 is
  port (
                                          -- Clock signal
  clock    : in  std_logic;

  reset_n  : in  std_logic;
  msg       : in std_logic_vector(31 downto 0);
  msg_send  : in std_logic;
  hash       : out std_logic_vector(255 downto 0);
  hash_send  : out std_logic);
end TopShaStep2;

------------------------------------------------------------------------------
architecture rtl_TopShaStep2 of TopShaStep2 is

  ---------------------------------------------------------------------------
  -- Signals declaration
  ---------------------------------------------------------------------------
  -- Module : computeW
  signal computeW_W      : std_logic_vector(31 downto 0);
  signal computeW_W_send : std_logic;
  -- Module : counterT
  signal counterT_start_o      : std_logic;
  signal counterT_msg_o      : std_logic_vector(31 downto 0);
  signal counterT_t_o      : std_logic_vector(5 downto 0);
  -- Module : rom
  signal rom_q      : std_logic_vector(31 downto 0);
  ---------------------------------------------------------------------------

begin

  ---------------------------------------------------------------------------
  -- Actors and Networks 
  ---------------------------------------------------------------------------
  sHA256_step2 : entity work.SHA256_step2
  port map (
    clock   => clock,
    reset_n => reset_n,
    W      => computeW_W,
    W_send => computeW_W_send,
    Kin      => rom_q,
    hash      => hash,
    hash_send => hash_send
  );
  
  computeW : entity work.ComputeW
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg      => counterT_msg_o,
    start      => counterT_start_o,
    W      => computeW_W,
    W_send => computeW_W_send
  );
  
  counterT : entity work.CounterT
  port map (
    clock   => clock,
    reset_n => reset_n,
    msg_i      => msg,
    msg_i_send => msg_send,
    start_o      => counterT_start_o,
    msg_o      => counterT_msg_o,
    t_o      => counterT_t_o
  );
  
  rom : entity work.TopShaStep2_rom
  port map (
    clock   => clock,
    reset_n => reset_n,
    address      => counterT_t_o,
    q      => rom_q
  );


end architecture rtl_TopShaStep2;
