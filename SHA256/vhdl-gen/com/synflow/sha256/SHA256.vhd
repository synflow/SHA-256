-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.SHA256 by Synflow Studio
-- Project    : SHA-256
--
-- File       : SHA256.vhd
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
entity SHA256 is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    msg       : in std_logic_vector(32 - 1 downto 0);
    msg_send  : in std_logic;
    hash       : out std_logic_vector(256 - 1 downto 0);
    hash_send  : out std_logic);
end SHA256;


-------------------------------------------------------------------------------
architecture rtl_SHA256 of SHA256 is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type H_i_type is array (0 to 7) of unsigned(32 - 1 downto 0);
  type W_type is array (0 to 63) of unsigned(32 - 1 downto 0);
  type K_type is array (0 to 63) of unsigned(32 - 1 downto 0);
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
  signal W : W_type;
  constant K : K_type := (1116352408, 1899447441, 3049323471, 3921009573, 961987163, 1508970993, 2453635748, 2870763221, 3624381080, 310598401, 607225278, 1426881987, 1925078388, 2162078206, 2614888103, 3248222580, 3835390401, 4022224774, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, 2554220882, 2821834349, 2952996808, 3210313671, 3336571891, 3584528711, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, 2177026350, 2456956037, 2730485921, 2820302411, 3259730800, 3345764771, 3516065817, 3600352804, 4094571909, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, 2227730452, 2361852424, 2428436474, 2756734187, 3204031479, 3329325298);


  -----------------------------------------------------------------------------
  -- FSM
  -----------------------------------------------------------------------------
  type FSM_type is (s_SHA256_0, s_SHA256_1, s_SHA256_2, s_SHA256_3, s_SHA256_4, s_SHA256_5);
  signal FSM : FSM_type;

  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  SHA256_execute : process (reset_n, clock) is
    variable local_W_1 : unsigned(32 - 1 downto 0);
    variable call_lcSigma1_1 : unsigned(32 - 1 downto 0);
    variable local_W0_1 : unsigned(32 - 1 downto 0);
    variable local_W1_1 : unsigned(32 - 1 downto 0);
    variable call_lcSigma0_1 : unsigned(32 - 1 downto 0);
    variable local_W2_1 : unsigned(32 - 1 downto 0);
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
    variable local_K_1 : unsigned(32 - 1 downto 0);
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
    variable msg_in  : unsigned(32 - 1 downto 0);
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
      W <= (others => 0);
      hash <= (others => '0');
      hash_send <= '0';
      FSM    <= s_SHA256_0;
    --
    elsif rising_edge(clock) then
      hash_send <= '0';
      --
      case FSM is
        when s_SHA256_0 =>
          if to_boolean() then
            -- Body of SHA256_0 (line 29)
            t <= 0;
            FSM <= s_SHA256_1;
          end if;
        
        when s_SHA256_1 =>
          if to_boolean(msg_send and ) then
            -- Body of SHA256_1_a (line 30)
            W(to_integer(t))  <= msg;
            write(output, "W[" & to_hstring_93(to_bitvector(std_logic_vector(t))) & "] = " & to_hstring_93(to_bitvector(std_logic_vector(msg))) & LF);
            t <= t + 1;
            FSM <= s_SHA256_1;
          elsif to_boolean() then
            -- Body of SHA256_1_b (line 0)
            FSM <= s_SHA256_2;
          end if;
        
        when s_SHA256_2 =>
          if to_boolean() then
            -- Body of SHA256_2_a (line 36)
            local_W_1 := W(to_integer(t - 2));
            call_lcSigma1_1 := resize(Ch(local_W_1), com.synflow.models.ir.impl.ExprIntImpl@6e2860b6 (value: 32));
            local_W0_1 := W(to_integer(t - 7));
            local_W1_1 := W(to_integer(t - 15));
            call_lcSigma0_1 := resize(Maj(local_W1_1), com.synflow.models.ir.impl.ExprIntImpl@1ce5853a (value: 32));
            local_W2_1 := W(to_integer(t - 16));
            W(to_integer(t))  <= call_lcSigma1_1 + local_W0_1 + call_lcSigma0_1 + local_W2_1;
            write(output, "W[" & to_hstring_93(to_bitvector(std_logic_vector(t))) & "] = " & to_hstring_93(to_bitvector(std_logic_vector(call_lcSigma1_1 + local_W0_1 + call_lcSigma0_1 + local_W2_1))) & LF);
            t <= t + 1;
            FSM <= s_SHA256_2;
          elsif to_boolean() then
            -- Body of SHA256_2_b (line 41)
            H_i(to_integer(to_unsigned(0, 3)))  <= 1779033703;
            H_i(to_integer(to_unsigned(1, 3)))  <= 3144134277;
            H_i(to_integer(to_unsigned(2, 3)))  <= 1013904242;
            H_i(to_integer(to_unsigned(3, 3)))  <= 2773480762;
            H_i(to_integer(to_unsigned(4, 3)))  <= 1359893119;
            H_i(to_integer(to_unsigned(5, 3)))  <= 2600822924;
            H_i(to_integer(to_unsigned(6, 3)))  <= 528734635;
            H_i(to_integer(to_unsigned(7, 3)))  <= 1541459225;
            FSM <= s_SHA256_3;
          end if;
        
        when s_SHA256_3 =>
          if to_boolean() then
            -- Body of SHA256_3 (line 52)
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
            FSM <= s_SHA256_4;
          end if;
        
        when s_SHA256_4 =>
          if to_boolean() then
            -- Body of SHA256_4_a (line 62)
            call_ucSigma1_1 := resize(lcSigma0(e), com.synflow.models.ir.impl.ExprIntImpl@208db1a8 (value: 32));
            call_Ch_1 := resize(lcSigma1(e, f, g), com.synflow.models.ir.impl.ExprIntImpl@368a9d4a (value: 32));
            local_K_1 := K(to_integer(t));
            local_W_1 := W(to_integer(t));
            call_ucSigma0_1 := resize(ucSigma0(a), com.synflow.models.ir.impl.ExprIntImpl@465c813f (value: 32));
            call_Maj_1 := resize(ucSigma1(a, b, c), com.synflow.models.ir.impl.ExprIntImpl@6329732f (value: 32));
            h <= g;
            e <= d + h + call_ucSigma1_1 + call_Ch_1 + local_K_1 + local_W_1;
            f <= e;
            g <= f;
            t <= t + 1;
            a <= h + call_ucSigma1_1 + call_Ch_1 + local_K_1 + local_W_1 + call_ucSigma0_1 + call_Maj_1;
            b <= a;
            c <= b;
            d <= c;
            FSM <= s_SHA256_4;
          elsif to_boolean() then
            -- Body of SHA256_4_b (line 74)
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
            FSM <= s_SHA256_5;
          end if;
        
        when s_SHA256_5 =>
          if to_boolean() then
            -- Body of SHA256_5 (line 85)
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
            FSM <= s_SHA256_0;
          end if;
      end case;
    end if;
  end process SHA256_execute;

end architecture rtl_SHA256;

