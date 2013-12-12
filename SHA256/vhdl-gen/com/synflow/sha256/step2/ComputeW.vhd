-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.ComputeW by Synflow Studio
-- Project    : SHA-256
--
-- File       : ComputeW.vhd
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
use work.SHACommon.all;

-------------------------------------------------------------------------------
entity ComputeW is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    start       : in std_logic;
    msg       : in std_logic_vector(32 - 1 downto 0);
    W       : out std_logic_vector(32 - 1 downto 0);
    W_send  : out std_logic);
end ComputeW;


-------------------------------------------------------------------------------
architecture rtl_ComputeW of ComputeW is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type words_type is array (0 to 15) of unsigned(32 - 1 downto 0);
  signal words : words_type;
  signal t : unsigned(6 - 1 downto 0);


  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  ComputeW_execute : process (reset_n, clock) is
    variable tmp_if_2 : unsigned( - 1 downto 0);
    variable local_words_1 : unsigned(32 - 1 downto 0);
    variable call_lcSigma1_1 : unsigned(32 - 1 downto 0);
    variable local_words0_1 : unsigned(32 - 1 downto 0);
    variable local_words1_1 : unsigned(32 - 1 downto 0);
    variable call_lcSigma0_1 : unsigned(32 - 1 downto 0);
    variable local_words2_1 : unsigned(32 - 1 downto 0);
    variable local_words3_1 : unsigned(32 - 1 downto 0);
    variable i_l_3 : unsigned(5 - 1 downto 0);
    variable start_in  : std_logic;
    variable msg_in  : unsigned(32 - 1 downto 0);
    variable W_out : unsigned(32 - 1 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      words <= (others => 0);
      t <= 0;
      W <= (others => '0');
      W_send <= '0';
    --
    elsif rising_edge(clock) then
      W_send <= '0';
      --
      if to_boolean() then
        -- Body of ComputeW_0_a (line 0)
      elsif to_boolean() then
        -- Body of ComputeW_0_b (line 21)
        if (to_std_logic(t < 16)) then
          tmp_if_2 := msg;
        else
          local_words_1 := words(to_integer(to_unsigned(1, 4)));
          call_lcSigma1_1 := resize(Ch(local_words_1), com.synflow.models.ir.impl.ExprIntImpl@4a39f37a (value: 32));
          local_words0_1 := words(to_integer(to_unsigned(6, 4)));
          local_words1_1 := words(to_integer(to_unsigned(14, 4)));
          call_lcSigma0_1 := resize(Maj(local_words1_1), com.synflow.models.ir.impl.ExprIntImpl@229ff4a8 (value: 32));
          local_words2_1 := words(to_integer(to_unsigned(15, 4)));
          tmp_if_2 := call_lcSigma1_1 + local_words0_1 + call_lcSigma0_1 + local_words2_1;
        end if;
        write(output, "W[" & to_hstring_93(to_bitvector(std_logic_vector(t))) & "] = " & to_hstring_93(to_bitvector(std_logic_vector(tmp_if_2))) & LF);
        W <= tmp_if_2;
        i_l_3 := 0;
        while (to_std_logic(i_l_3 < 15)) loop
          local_words3_1 := words(to_integer(14 - i_l_3));
          words(to_integer(15 - i_l_3))  <= local_words3_1;
          i_l_3 := i_l_3 + 1;
        end loop;
        words(to_integer(to_unsigned(0, 4)))  <= tmp_if_2;
        t <= t + 1;
        W_send <= '1';
      end if;
    end if;
  end process ComputeW_execute;

end architecture rtl_ComputeW;

