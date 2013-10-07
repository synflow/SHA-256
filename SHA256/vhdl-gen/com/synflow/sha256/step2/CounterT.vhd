-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.CounterT by Synflow Studio
-- Project    : SHA-256
--
-- File       : CounterT.vhd
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
entity CounterT is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    msg_i       : in std_logic_vector(31 downto 0);
    msg_i_send  : in std_logic;
    start_o       : out std_logic;
    msg_o       : out std_logic_vector(31 downto 0);
    t_o       : out std_logic_vector(5 downto 0));
end CounterT;


-------------------------------------------------------------------------------
architecture rtl_CounterT of CounterT is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  signal t : unsigned(6 downto 0);


  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------
  -- Scheduler of CounterT_0_a (line 18)
  impure function isSchedulable_CounterT_0_a return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return to_std_logic((local_t) <= "0010000");
  end function isSchedulable_CounterT_0_a;
  
  -- Scheduler of CounterT_0_b (line 23)
  impure function isSchedulable_CounterT_0_b return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return to_std_logic((local_t) <= "1000000");
  end function isSchedulable_CounterT_0_b;
  

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  CounterT_execute : process (reset_n, clock) is
    variable local_msg_i : unsigned(31 downto 0);
    variable local_t : unsigned(6 downto 0);
    variable msg_i_in  : unsigned(31 downto 0);
    variable start_o_out : std_logic :=  '0';
    variable msg_o_out : unsigned(31 downto 0) :=  (others => '0');
    variable t_o_out : unsigned(5 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      t <= "0000000";
      start_o <= '0';
      msg_o <= (others => '0');
      t_o <= (others => '0');
    --
    elsif rising_edge(clock) then
      --
      if to_boolean(msg_i_send and isSchedulable_CounterT_0_a) then
        -- Body of CounterT_0_a (line 18)
        msg_i_in := unsigned(msg_i);
        local_t := t;
        local_msg_i := msg_i_in;
        msg_o_out := (local_msg_i);
        start_o_out := '1';
        t_o_out := resize(resize(local_t, 8) - x"01", 6);
        local_t := resize(resize(local_t, 8) + x"01", 7);
        t <= (local_t);
        msg_o   <= std_logic_vector(msg_o_out);
        start_o   <= start_o_out;
        t_o   <= std_logic_vector(t_o_out);
      elsif to_boolean(isSchedulable_CounterT_0_b) then
        -- Body of CounterT_0_b (line 23)
        local_t := t;
        t_o_out := resize(resize(local_t, 8) - x"01", 6);
        local_t := resize(resize(local_t, 8) + x"01", 7);
        t <= (local_t);
        t_o   <= std_logic_vector(t_o_out);
      elsif to_boolean('1') then
        -- Body of CounterT_0_c (line 0)
        t <= "0000000";
        start_o_out := '0';
        start_o   <= start_o_out;
      end if;
    end if;
  end process CounterT_execute;

end architecture rtl_CounterT;

