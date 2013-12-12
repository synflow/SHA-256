-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step2.SHA256_step2 by Synflow Studio
-- Project    : SHA-256
--
-- File       : SHA256_step2.vhd
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
entity SHA256_step2 is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    W       : in std_logic_vector(32 - 1 downto 0);
    W_send  : in std_logic;
    Kin       : in std_logic_vector(32 - 1 downto 0);
    hash       : out std_logic_vector(256 - 1 downto 0);
    hash_send  : out std_logic);
end SHA256_step2;


-------------------------------------------------------------------------------
architecture rtl_SHA256_step2 of SHA256_step2 is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type H_i_type is array (0 to 7) of unsigned(32 - 1 downto 0);
  signal H_i : H_i_type;
  signal a : unsigned(32 - 1 downto 0);
  signal b : unsigned(32 - 1 downto 0);
  signal c : unsigned(32 - 1 downto 0);
  signal d : unsigned(32 - 1 downto 0);
  signal e : unsigned(32 - 1 downto 0);
  signal f : unsigned(32 - 1 downto 0);
  signal g : unsigned(32 - 1 downto 0);
  signal h : unsigned(32 - 1 downto 0);
  signal t : unsigned(7 - 1 downto 0);


  -----------------------------------------------------------------------------
  -- FSM
  -----------------------------------------------------------------------------
  type FSM_type is (s_SHA256_step2_0, s_SHA256_step2_1, s_SHA256_step2_2, s_SHA256_step2_3);
  signal FSM : FSM_type;

  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  SHA256_step2_execute : process (reset_n, clock) is
    variable local_H_i_1 : unsigned(32 - 1 downto 0);
    variable local_H_i0_1 : unsigned(32 - 1 downto 0);
    variable local_H_i1_1 : unsigned(32 - 1 downto 0);
    variable local_H_i2_1 : unsigned(32 - 1 downto 0);
    variable local_H_i3_1 : unsigned(32 - 1 downto 0);
    variable local_H_i4_1 : unsigned(32 - 1 downto 0);
    variable local_H_i5_1 : unsigned(32 - 1 downto 0);
    variable local_H_i6_1 : unsigned(32 - 1 downto 0);
    variable call_ucSigma1_1 : unsigned(32 - 1 downto 0);
    variable call_Ch_1 : unsigned(32 - 1 downto 0);
    variable call_ucSigma0_1 : unsigned(32 - 1 downto 0);
    variable call_Maj_1 : unsigned(32 - 1 downto 0);
    variable local_H_i7_1 : unsigned(32 - 1 downto 0);
    variable local_H_i8_1 : unsigned(32 - 1 downto 0);
    variable local_H_i9_1 : unsigned(32 - 1 downto 0);
    variable local_H_i10_1 : unsigned(32 - 1 downto 0);
    variable local_H_i11_1 : unsigned(32 - 1 downto 0);
    variable local_H_i12_1 : unsigned(32 - 1 downto 0);
    variable local_H_i13_1 : unsigned(32 - 1 downto 0);
    variable local_H_i14_1 : unsigned(32 - 1 downto 0);
    variable W_in  : unsigned(32 - 1 downto 0);
    variable Kin_in  : unsigned(32 - 1 downto 0);
    variable hash_out : unsigned(256 - 1 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      H_i <= (others => 0);
      a <= 0;
      b <= 0;
      c <= 0;
      d <= 0;
      e <= 0;
      f <= 0;
      g <= 0;
      h <= 0;
      t <= 0;
      hash <= (others => '0');
      hash_send <= '0';
      FSM    <= s_SHA256_step2_0;
    --
    elsif rising_edge(clock) then
      hash_send <= '0';
      --
      case FSM is
        when s_SHA256_step2_0 =>
          if to_boolean() then
            -- Body of SHA256_step2_0 (line 20)
            H_i(to_integer(to_unsigned(0, 3)))  <= 1779033703;
            H_i(to_integer(to_unsigned(1, 3)))  <= 3144134277;
            H_i(to_integer(to_unsigned(2, 3)))  <= 1013904242;
            H_i(to_integer(to_unsigned(3, 3)))  <= 2773480762;
            H_i(to_integer(to_unsigned(4, 3)))  <= 1359893119;
            H_i(to_integer(to_unsigned(5, 3)))  <= 2600822924;
            H_i(to_integer(to_unsigned(6, 3)))  <= 528734635;
            H_i(to_integer(to_unsigned(7, 3)))  <= 1541459225;
            FSM <= s_SHA256_step2_1;
          end if;
        
        when s_SHA256_step2_1 =>
          if to_boolean() then
            -- Body of SHA256_step2_1 (line 31)
            local_H_i_1 := H_i(to_integer(to_unsigned(0, 3)));
            a <= local_H_i_1;
            local_H_i0_1 := H_i(to_integer(to_unsigned(1, 3)));
            b <= local_H_i0_1;
            local_H_i1_1 := H_i(to_integer(to_unsigned(2, 3)));
            c <= local_H_i1_1;
            local_H_i2_1 := H_i(to_integer(to_unsigned(3, 3)));
            d <= local_H_i2_1;
            local_H_i3_1 := H_i(to_integer(to_unsigned(4, 3)));
            e <= local_H_i3_1;
            local_H_i4_1 := H_i(to_integer(to_unsigned(5, 3)));
            f <= local_H_i4_1;
            local_H_i5_1 := H_i(to_integer(to_unsigned(6, 3)));
            g <= local_H_i5_1;
            local_H_i6_1 := H_i(to_integer(to_unsigned(7, 3)));
            h <= local_H_i6_1;
            t <= 0;
            FSM <= s_SHA256_step2_2;
          end if;
        
        when s_SHA256_step2_2 =>
          if to_boolean(W_send and ) then
            -- Body of SHA256_step2_2_a (line 41)
            write(output, "K[" & to_hstring_93(to_bitvector(std_logic_vector(t))) & "] = " & to_hstring_93(to_bitvector(std_logic_vector(Kin))) & LF);
            call_ucSigma1_1 := resize(Ch(e), com.synflow.models.ir.impl.ExprIntImpl@c7a53fd (value: 32));
            call_Ch_1 := resize(Maj(e, f, g), com.synflow.models.ir.impl.ExprIntImpl@33200d71 (value: 32));
            call_ucSigma0_1 := resize(lcSigma0(a), com.synflow.models.ir.impl.ExprIntImpl@7d528fef (value: 32));
            call_Maj_1 := resize(lcSigma1(a, b, c), com.synflow.models.ir.impl.ExprIntImpl@3767d430 (value: 32));
            t <= t + 1;
            h <= g;
            e <= d + h + call_ucSigma1_1 + call_Ch_1 + Kin + W;
            f <= e;
            g <= f;
            a <= h + call_ucSigma1_1 + call_Ch_1 + Kin + W + call_ucSigma0_1 + call_Maj_1;
            b <= a;
            c <= b;
            d <= c;
            FSM <= s_SHA256_step2_2;
          elsif to_boolean() then
            -- Body of SHA256_step2_2_b (line 55)
            local_H_i_1 := H_i(to_integer(to_unsigned(0, 3)));
            H_i(to_integer(to_unsigned(0, 3)))  <= local_H_i_1 + a;
            local_H_i0_1 := H_i(to_integer(to_unsigned(1, 3)));
            H_i(to_integer(to_unsigned(1, 3)))  <= local_H_i0_1 + b;
            local_H_i1_1 := H_i(to_integer(to_unsigned(2, 3)));
            H_i(to_integer(to_unsigned(2, 3)))  <= local_H_i1_1 + c;
            local_H_i2_1 := H_i(to_integer(to_unsigned(3, 3)));
            H_i(to_integer(to_unsigned(3, 3)))  <= local_H_i2_1 + d;
            local_H_i3_1 := H_i(to_integer(to_unsigned(4, 3)));
            H_i(to_integer(to_unsigned(4, 3)))  <= local_H_i3_1 + e;
            local_H_i4_1 := H_i(to_integer(to_unsigned(5, 3)));
            H_i(to_integer(to_unsigned(5, 3)))  <= local_H_i4_1 + f;
            local_H_i5_1 := H_i(to_integer(to_unsigned(6, 3)));
            H_i(to_integer(to_unsigned(6, 3)))  <= local_H_i5_1 + g;
            local_H_i6_1 := H_i(to_integer(to_unsigned(7, 3)));
            H_i(to_integer(to_unsigned(7, 3)))  <= local_H_i6_1 + h;
            FSM <= s_SHA256_step2_3;
          end if;
        
        when s_SHA256_step2_3 =>
          if to_boolean() then
            -- Body of SHA256_step2_3 (line 66)
            local_H_i_1 := H_i(to_integer(to_unsigned(0, 3)));
            write(output, "H_i[0] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i_1))) & LF);
            local_H_i0_1 := H_i(to_integer(to_unsigned(1, 3)));
            write(output, "H_i[1] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i0_1))) & LF);
            local_H_i1_1 := H_i(to_integer(to_unsigned(2, 3)));
            write(output, "H_i[2] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i1_1))) & LF);
            local_H_i2_1 := H_i(to_integer(to_unsigned(3, 3)));
            write(output, "H_i[3] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i2_1))) & LF);
            local_H_i3_1 := H_i(to_integer(to_unsigned(4, 3)));
            write(output, "H_i[4] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i3_1))) & LF);
            local_H_i4_1 := H_i(to_integer(to_unsigned(5, 3)));
            write(output, "H_i[5] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i4_1))) & LF);
            local_H_i5_1 := H_i(to_integer(to_unsigned(6, 3)));
            write(output, "H_i[6] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i5_1))) & LF);
            local_H_i6_1 := H_i(to_integer(to_unsigned(7, 3)));
            write(output, "H_i[7] = " & to_hstring_93(to_bitvector(std_logic_vector(local_H_i6_1))) & LF);
            local_H_i7_1 := H_i(to_integer(to_unsigned(0, 3)));
            local_H_i8_1 := H_i(to_integer(to_unsigned(1, 3)));
            local_H_i9_1 := H_i(to_integer(to_unsigned(2, 3)));
            local_H_i10_1 := H_i(to_integer(to_unsigned(3, 3)));
            local_H_i11_1 := H_i(to_integer(to_unsigned(4, 3)));
            local_H_i12_1 := H_i(to_integer(to_unsigned(5, 3)));
            local_H_i13_1 := H_i(to_integer(to_unsigned(6, 3)));
            local_H_i14_1 := H_i(to_integer(to_unsigned(7, 3)));
            hash <= (local_H_i7_1 & x"00000000000000000000000000000000000000000000000000000000") or (local_H_i8_1 & x"000000000000000000000000000000000000000000000000") or (local_H_i9_1 & x"0000000000000000000000000000000000000000") or (local_H_i10_1 & x"00000000000000000000000000000000") or (local_H_i11_1 & x"000000000000000000000000") or (local_H_i12_1 & x"0000000000000000") or (local_H_i13_1 & x"00000000") or local_H_i14_1;
            hash_send <= '1';
            FSM <= s_SHA256_step2_0;
          end if;
      end case;
    end if;
  end process SHA256_step2_execute;

end architecture rtl_SHA256_step2;

