-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.CounterT by Synflow Studio
-- Project    : SHA-256
--
-- File       : CounterT.vhd
-- Author     : Nicolas
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
entity CounterT is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    msg_i       : in std_logic_vector(32 - 1 downto 0);
    msg_i_send  : in std_logic;
    start_o       : out std_logic;
    msg_o       : out std_logic_vector(32 - 1 downto 0);
    t_o       : out std_logic_vector(6 - 1 downto 0));
end CounterT;


-------------------------------------------------------------------------------
architecture rtl_CounterT of CounterT is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  signal t : unsigned(7 - 1 downto 0);


  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  CounterT_execute : process (reset_n, clock) is
    variable msg_i_in  : unsigned(32 - 1 downto 0);
    variable start_o_out : std_logic :=  '0';
    variable msg_o_out : unsigned(32 - 1 downto 0) :=  (others => '0');
    variable t_o_out : unsigned(6 - 1 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      t <= 0;
      start_o <= '0';
      msg_o <= (others => '0');
      t_o <= (others => '0');
    --
    elsif rising_edge(clock) then
      --
      if to_boolean(msg_i_send and ) then
        -- Body of CounterT_0_a (line 15)
        msg_o <= msg_i;
        start_o <= '1';
        t_o <= t - 1;
        t <= t + 1;
      elsif to_boolean() then
        -- Body of CounterT_0_b (line 20)
        t_o <= t - 1;
        t <= t + 1;
      elsif to_boolean() then
        -- Body of CounterT_0_c (line 23)
        t <= 0;
        start_o <= '0';
      end if;
    end if;
  end process CounterT_execute;

end architecture rtl_CounterT;

