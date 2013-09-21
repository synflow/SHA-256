-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.ComputeW by Synflow Studio
-- Project    : SHA-256
--
-- File       : ComputeW.vhd
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
use work.SHACommon.all;

-------------------------------------------------------------------------------
entity ComputeW is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    start       : in std_logic;
    msg       : in std_logic_vector(31 downto 0);
    W       : out std_logic_vector(31 downto 0);
    W_send  : out std_logic);
end ComputeW;


-------------------------------------------------------------------------------
architecture rtl_ComputeW of ComputeW is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type words_type is array (0 to 15) of unsigned(31 downto 0);
  signal words : words_type;
  signal t : unsigned(5 downto 0);


  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------
  -- Scheduler of ComputeW_0_a (line 19)
  impure function isSchedulable_ComputeW_0_a(start_in : std_logic) return std_logic is
    variable local_start : std_logic;
  begin
    local_start := start_in;
    return (not ((local_start)));
  end function isSchedulable_ComputeW_0_a;
  
  -- Scheduler of ComputeW_0_b (line 20)
  impure function isSchedulable_ComputeW_0_b(start_in : std_logic) return std_logic is
    variable local_start : std_logic;
  begin
    local_start := start_in;
    return (not ((not ((local_start)))));
  end function isSchedulable_ComputeW_0_b;
  

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  ComputeW_execute : process (reset_n, clock) is
    variable m : unsigned(31 downto 0);
    variable local_msg : unsigned(31 downto 0);
    variable temp : unsigned(31 downto 0);
    variable local_t : unsigned(5 downto 0);
    variable tmp_if : unsigned(34 downto 0);
    variable local_words : unsigned(31 downto 0);
    variable call_lcSigma1 : unsigned(31 downto 0);
    variable local_words0 : unsigned(31 downto 0);
    variable local_words1 : unsigned(31 downto 0);
    variable call_lcSigma0 : unsigned(31 downto 0);
    variable local_words2 : unsigned(31 downto 0);
    variable i : unsigned(4 downto 0);
    variable local_words3 : unsigned(31 downto 0);
    variable start_in  : std_logic;
    variable msg_in  : unsigned(31 downto 0);
    variable W_out : unsigned(31 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      words <= (others => x"00000000");
      t <= "000000";
      W <= (others => '0');
      W_send <= '0';
    --
    elsif rising_edge(clock) then
      W_send <= '0';
      --
      if to_boolean(isSchedulable_ComputeW_0_a(start)) then
        -- Body of ComputeW_0_a (line 19)
      elsif to_boolean(isSchedulable_ComputeW_0_b(start)) then
        -- Body of ComputeW_0_b (line 20)
        msg_in := unsigned(msg);
        local_t := t;
        local_msg := msg_in;
        m := (local_msg);
        if ((local_t) < "010000") then
          tmp_if := resize(m, 35);
        else
          local_words := words(to_integer(to_unsigned(1, 4)));
          call_lcSigma1 := resize(lcSigma1((local_words)), 32);
          local_words0 := words(to_integer(to_unsigned(6, 4)));
          local_words1 := words(to_integer(to_unsigned(14, 4)));
          call_lcSigma0 := resize(lcSigma0((local_words1)), 32);
          local_words2 := words(to_integer(to_unsigned(15, 4)));
          tmp_if := (resize(resize(resize(call_lcSigma1, 33) + resize(local_words0, 33), 34) + resize(call_lcSigma0, 34), 35) + resize(local_words2, 35));
        end if;
        temp := resize(tmp_if, 32);
        write(output, "W["
         & to_hstring_93(to_bitvector(std_logic_vector((local_t))))
         & "] = "
         & to_hstring_93(to_bitvector(std_logic_vector((temp))))
         & LF);
        W_out := (temp);
        local_t := resize(resize(local_t, 7) + "0000001", 6);
        i := "00000";
        while ((i) < "01111") loop
          local_words3 := words(to_integer(resize("001110" - resize(i, 6), 4)));
          words(to_integer(resize("001111" - resize(i, 6), 4)))  <= (local_words3);
          i := resize(resize(i, 6) + "000001", 5);
        end loop;
        words(to_integer(to_unsigned(0, 4)))  <= (temp);
        t <= (local_t);
        W   <= std_logic_vector(W_out);
        W_send <= '1';
      end if;
    end if;
  end process ComputeW_execute;

end architecture rtl_ComputeW;

