-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.SHA256 by Synflow Studio
-- Project    : SHA-256
--
-- File       : SHA256.vhd
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
entity SHA256 is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    msg       : in std_logic_vector(31 downto 0);
    msg_send  : in std_logic;
    hash       : out std_logic_vector(255 downto 0);
    hash_send  : out std_logic);
end SHA256;


-------------------------------------------------------------------------------
architecture rtl_SHA256 of SHA256 is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type H_i_type is array (0 to 7) of unsigned(31 downto 0);
  type W_type is array (0 to 63) of unsigned(31 downto 0);
  type K_type is array (0 to 63) of unsigned(31 downto 0);
  signal H_i : H_i_type;
  signal a : unsigned(31 downto 0);
  signal b : unsigned(31 downto 0);
  signal c : unsigned(31 downto 0);
  signal d : unsigned(31 downto 0);
  signal e : unsigned(31 downto 0);
  signal f : unsigned(31 downto 0);
  signal g : unsigned(31 downto 0);
  signal h : unsigned(31 downto 0);
  signal t : unsigned(6 downto 0);
  signal W : W_type;
  constant K : K_type := (x"428a2f98", x"71374491", x"b5c0fbcf", x"e9b5dba5", x"3956c25b", x"59f111f1", x"923f82a4", x"ab1c5ed5", x"d807aa98", x"12835b01", x"243185be", x"550c7dc3", x"72be5d74", x"80deb1fe", x"9bdc06a7", x"c19bf174", x"e49b69c1", x"efbe4786", x"0fc19dc6", x"240ca1cc", x"2de92c6f", x"4a7484aa", x"5cb0a9dc", x"76f988da", x"983e5152", x"a831c66d", x"b00327c8", x"bf597fc7", x"c6e00bf3", x"d5a79147", x"06ca6351", x"14292967", x"27b70a85", x"2e1b2138", x"4d2c6dfc", x"53380d13", x"650a7354", x"766a0abb", x"81c2c92e", x"92722c85", x"a2bfe8a1", x"a81a664b", x"c24b8b70", x"c76c51a3", x"d192e819", x"d6990624", x"f40e3585", x"106aa070", x"19a4c116", x"1e376c08", x"2748774c", x"34b0bcb5", x"391c0cb3", x"4ed8aa4a", x"5b9cca4f", x"682e6ff3", x"748f82ee", x"78a5636f", x"84c87814", x"8cc70208", x"90befffa", x"a4506ceb", x"bef9a3f7", x"c67178f2");


  -----------------------------------------------------------------------------
  -- FSM
  -----------------------------------------------------------------------------
  type FSM_type is (s_SHA256_0, s_SHA256_1, s_SHA256_2, s_SHA256_3, s_SHA256_4, s_SHA256_5);
  signal FSM : FSM_type;

  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------
  -- Scheduler of SHA256_1_a (line 35)
  impure function isSchedulable_SHA256_1_a return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return to_std_logic((local_t) < "0010000");
  end function isSchedulable_SHA256_1_a;
  
  -- Scheduler of SHA256_1_b (line 39)
  impure function isSchedulable_SHA256_1_b return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return (not (to_std_logic((local_t) < "0010000")));
  end function isSchedulable_SHA256_1_b;
  
  -- Scheduler of SHA256_2_a (line 41)
  impure function isSchedulable_SHA256_2_a return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return to_std_logic((local_t) < "1000000");
  end function isSchedulable_SHA256_2_a;
  
  -- Scheduler of SHA256_2_b (line 45)
  impure function isSchedulable_SHA256_2_b return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return (not (to_std_logic((local_t) < "1000000")));
  end function isSchedulable_SHA256_2_b;
  
  -- Scheduler of SHA256_4_a (line 67)
  impure function isSchedulable_SHA256_4_a return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return to_std_logic((local_t) < "1000000");
  end function isSchedulable_SHA256_4_a;
  
  -- Scheduler of SHA256_4_b (line 78)
  impure function isSchedulable_SHA256_4_b return std_logic is
    variable local_t : unsigned(6 downto 0);
  begin
    local_t := t;
    return (not (to_std_logic((local_t) < "1000000")));
  end function isSchedulable_SHA256_4_b;
  

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  SHA256_execute : process (reset_n, clock) is
    variable m : unsigned(31 downto 0);
    variable local_msg : unsigned(31 downto 0);
    variable local_t : unsigned(6 downto 0);
    variable temp : unsigned(31 downto 0);
    variable local_W : unsigned(31 downto 0);
    variable call_lcSigma1 : unsigned(31 downto 0);
    variable local_W0 : unsigned(31 downto 0);
    variable local_W1 : unsigned(31 downto 0);
    variable call_lcSigma0 : unsigned(31 downto 0);
    variable local_W2 : unsigned(31 downto 0);
    variable local_H_i : unsigned(31 downto 0);
    variable local_H_i0 : unsigned(31 downto 0);
    variable local_H_i1 : unsigned(31 downto 0);
    variable local_H_i2 : unsigned(31 downto 0);
    variable local_H_i3 : unsigned(31 downto 0);
    variable local_H_i4 : unsigned(31 downto 0);
    variable local_H_i5 : unsigned(31 downto 0);
    variable local_H_i6 : unsigned(31 downto 0);
    variable T1 : unsigned(31 downto 0);
    variable local_h : unsigned(31 downto 0);
    variable local_e : unsigned(31 downto 0);
    variable call_ucSigma1 : unsigned(31 downto 0);
    variable local_f : unsigned(31 downto 0);
    variable local_g : unsigned(31 downto 0);
    variable call_Ch : unsigned(31 downto 0);
    variable local_K : unsigned(31 downto 0);
    variable T2 : unsigned(31 downto 0);
    variable local_a : unsigned(31 downto 0);
    variable call_ucSigma0 : unsigned(31 downto 0);
    variable local_b : unsigned(31 downto 0);
    variable local_c : unsigned(31 downto 0);
    variable call_Maj : unsigned(31 downto 0);
    variable local_d : unsigned(31 downto 0);
    variable local_H_i7 : unsigned(31 downto 0);
    variable local_H_i8 : unsigned(31 downto 0);
    variable local_H_i9 : unsigned(31 downto 0);
    variable local_H_i10 : unsigned(31 downto 0);
    variable local_H_i11 : unsigned(31 downto 0);
    variable local_H_i12 : unsigned(31 downto 0);
    variable local_H_i13 : unsigned(31 downto 0);
    variable local_H_i14 : unsigned(31 downto 0);
    variable msg_in  : unsigned(31 downto 0);
    variable hash_out : unsigned(255 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      H_i <= (others => x"00000000");
      a <= x"00000000";
      b <= x"00000000";
      c <= x"00000000";
      d <= x"00000000";
      e <= x"00000000";
      f <= x"00000000";
      g <= x"00000000";
      h <= x"00000000";
      t <= "0000000";
      W <= (others => x"00000000");
      hash <= (others => '0');
      hash_send <= '0';
      FSM    <= s_SHA256_0;
    --
    elsif rising_edge(clock) then
      hash_send <= '0';
      --
      case FSM is
        when s_SHA256_0 =>
          if to_boolean('1') then
            -- Body of SHA256_0 (line 34)
            t <= "0000000";
            FSM <= s_SHA256_1;
          end if;
        
        when s_SHA256_1 =>
          if to_boolean(msg_send and isSchedulable_SHA256_1_a) then
            -- Body of SHA256_1_a (line 35)
            msg_in := unsigned(msg);
            local_t := t;
            local_msg := msg_in;
            m := (local_msg);
            W(to_integer(resize(local_t, 6)))  <= (m);
            write(output, "W["
             & to_hstring_93(to_bitvector(std_logic_vector((local_t))))
             & "] = "
             & to_hstring_93(to_bitvector(std_logic_vector((m))))
             & LF);
            local_t := resize(resize(local_t, 8) + x"01", 7);
            t <= (local_t);
            FSM <= s_SHA256_1;
          elsif to_boolean(isSchedulable_SHA256_1_b) then
            -- Body of SHA256_1_b (line 39)
            FSM <= s_SHA256_2;
          end if;
        
        when s_SHA256_2 =>
          if to_boolean(isSchedulable_SHA256_2_a) then
            -- Body of SHA256_2_a (line 41)
            local_t := t;
            local_W := W(to_integer(resize(resize(local_t, 8) - x"02", 6)));
            call_lcSigma1 := resize(lcSigma1((local_W)), 32);
            local_W0 := W(to_integer(resize(resize(local_t, 8) - x"07", 6)));
            local_W1 := W(to_integer(resize(resize(local_t, 8) - x"0f", 6)));
            call_lcSigma0 := resize(lcSigma0((local_W1)), 32);
            local_W2 := W(to_integer(resize(resize(local_t, 8) - x"10", 6)));
            temp := resize(resize(resize(resize(call_lcSigma1, 33) + resize(local_W0, 33), 34) + resize(call_lcSigma0, 34), 35) + resize(local_W2, 35), 32);
            W(to_integer(resize(local_t, 6)))  <= (temp);
            write(output, "W["
             & to_hstring_93(to_bitvector(std_logic_vector((local_t))))
             & "] = "
             & to_hstring_93(to_bitvector(std_logic_vector((temp))))
             & LF);
            local_t := resize(resize(local_t, 8) + x"01", 7);
            t <= (local_t);
            FSM <= s_SHA256_2;
          elsif to_boolean(isSchedulable_SHA256_2_b) then
            -- Body of SHA256_2_b (line 45)
            H_i(to_integer(to_unsigned(0, 3)))  <= x"6a09e667";
            H_i(to_integer(to_unsigned(1, 3)))  <= x"bb67ae85";
            H_i(to_integer(to_unsigned(2, 3)))  <= x"3c6ef372";
            H_i(to_integer(to_unsigned(3, 3)))  <= x"a54ff53a";
            H_i(to_integer(to_unsigned(4, 3)))  <= x"510e527f";
            H_i(to_integer(to_unsigned(5, 3)))  <= x"9b05688c";
            H_i(to_integer(to_unsigned(6, 3)))  <= x"1f83d9ab";
            H_i(to_integer(to_unsigned(7, 3)))  <= x"5be0cd19";
            FSM <= s_SHA256_3;
          end if;
        
        when s_SHA256_3 =>
          if to_boolean('1') then
            -- Body of SHA256_3 (line 57)
            local_H_i := H_i(to_integer(to_unsigned(0, 3)));
            a <= (local_H_i);
            local_H_i0 := H_i(to_integer(to_unsigned(1, 3)));
            b <= (local_H_i0);
            local_H_i1 := H_i(to_integer(to_unsigned(2, 3)));
            c <= (local_H_i1);
            local_H_i2 := H_i(to_integer(to_unsigned(3, 3)));
            d <= (local_H_i2);
            local_H_i3 := H_i(to_integer(to_unsigned(4, 3)));
            e <= (local_H_i3);
            local_H_i4 := H_i(to_integer(to_unsigned(5, 3)));
            f <= (local_H_i4);
            local_H_i5 := H_i(to_integer(to_unsigned(6, 3)));
            g <= (local_H_i5);
            local_H_i6 := H_i(to_integer(to_unsigned(7, 3)));
            h <= (local_H_i6);
            t <= "0000000";
            FSM <= s_SHA256_4;
          end if;
        
        when s_SHA256_4 =>
          if to_boolean(isSchedulable_SHA256_4_a) then
            -- Body of SHA256_4_a (line 67)
            local_h := h;
            local_e := e;
            local_f := f;
            local_g := g;
            local_t := t;
            local_a := a;
            local_b := b;
            local_c := c;
            local_d := d;
            call_ucSigma1 := resize(ucSigma1((local_e)), 32);
            call_Ch := resize(Ch((local_e), (local_f), (local_g)), 32);
            local_K := K(to_integer(resize(local_t, 6)));
            local_W := W(to_integer(resize(local_t, 6)));
            T1 := resize(resize(resize(resize(resize(local_h, 33) + resize(call_ucSigma1, 33), 34) + resize(call_Ch, 34), 35) + resize(local_K, 35), 36) + resize(local_W, 36), 32);
            call_ucSigma0 := resize(ucSigma0((local_a)), 32);
            call_Maj := resize(Maj((local_a), (local_b), (local_c)), 32);
            T2 := resize(resize(call_ucSigma0, 33) + resize(call_Maj, 33), 32);
            local_h := (local_g);
            local_g := (local_f);
            local_f := (local_e);
            local_e := resize(resize(local_d, 33) + resize(T1, 33), 32);
            local_d := (local_c);
            local_c := (local_b);
            local_b := (local_a);
            local_a := resize(resize(T1, 33) + resize(T2, 33), 32);
            local_t := resize(resize(local_t, 8) + x"01", 7);
            h <= (local_h);
            e <= (local_e);
            f <= (local_f);
            g <= (local_g);
            t <= (local_t);
            a <= (local_a);
            b <= (local_b);
            c <= (local_c);
            d <= (local_d);
            FSM <= s_SHA256_4;
          elsif to_boolean(isSchedulable_SHA256_4_b) then
            -- Body of SHA256_4_b (line 78)
            local_a := a;
            local_b := b;
            local_c := c;
            local_d := d;
            local_e := e;
            local_f := f;
            local_g := g;
            local_h := h;
            local_H_i := H_i(to_integer(to_unsigned(0, 3)));
            H_i(to_integer(to_unsigned(0, 3)))  <= resize(resize(local_H_i, 33) + resize(local_a, 33), 32);
            local_H_i0 := H_i(to_integer(to_unsigned(1, 3)));
            H_i(to_integer(to_unsigned(1, 3)))  <= resize(resize(local_H_i0, 33) + resize(local_b, 33), 32);
            local_H_i1 := H_i(to_integer(to_unsigned(2, 3)));
            H_i(to_integer(to_unsigned(2, 3)))  <= resize(resize(local_H_i1, 33) + resize(local_c, 33), 32);
            local_H_i2 := H_i(to_integer(to_unsigned(3, 3)));
            H_i(to_integer(to_unsigned(3, 3)))  <= resize(resize(local_H_i2, 33) + resize(local_d, 33), 32);
            local_H_i3 := H_i(to_integer(to_unsigned(4, 3)));
            H_i(to_integer(to_unsigned(4, 3)))  <= resize(resize(local_H_i3, 33) + resize(local_e, 33), 32);
            local_H_i4 := H_i(to_integer(to_unsigned(5, 3)));
            H_i(to_integer(to_unsigned(5, 3)))  <= resize(resize(local_H_i4, 33) + resize(local_f, 33), 32);
            local_H_i5 := H_i(to_integer(to_unsigned(6, 3)));
            H_i(to_integer(to_unsigned(6, 3)))  <= resize(resize(local_H_i5, 33) + resize(local_g, 33), 32);
            local_H_i6 := H_i(to_integer(to_unsigned(7, 3)));
            H_i(to_integer(to_unsigned(7, 3)))  <= resize(resize(local_H_i6, 33) + resize(local_h, 33), 32);
            FSM <= s_SHA256_5;
          end if;
        
        when s_SHA256_5 =>
          if to_boolean('1') then
            -- Body of SHA256_5 (line 90)
            local_H_i := H_i(to_integer(to_unsigned(0, 3)));
            write(output, "H_i[0] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i))))
             & LF);
            local_H_i0 := H_i(to_integer(to_unsigned(1, 3)));
            write(output, "H_i[1] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i0))))
             & LF);
            local_H_i1 := H_i(to_integer(to_unsigned(2, 3)));
            write(output, "H_i[2] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i1))))
             & LF);
            local_H_i2 := H_i(to_integer(to_unsigned(3, 3)));
            write(output, "H_i[3] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i2))))
             & LF);
            local_H_i3 := H_i(to_integer(to_unsigned(4, 3)));
            write(output, "H_i[4] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i3))))
             & LF);
            local_H_i4 := H_i(to_integer(to_unsigned(5, 3)));
            write(output, "H_i[5] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i4))))
             & LF);
            local_H_i5 := H_i(to_integer(to_unsigned(6, 3)));
            write(output, "H_i[6] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i5))))
             & LF);
            local_H_i6 := H_i(to_integer(to_unsigned(7, 3)));
            write(output, "H_i[7] = "
             & to_hstring_93(to_bitvector(std_logic_vector((local_H_i6))))
             & LF);
            local_H_i7 := H_i(to_integer(to_unsigned(0, 3)));
            local_H_i8 := H_i(to_integer(to_unsigned(1, 3)));
            local_H_i9 := H_i(to_integer(to_unsigned(2, 3)));
            local_H_i10 := H_i(to_integer(to_unsigned(3, 3)));
            local_H_i11 := H_i(to_integer(to_unsigned(4, 3)));
            local_H_i12 := H_i(to_integer(to_unsigned(5, 3)));
            local_H_i13 := H_i(to_integer(to_unsigned(6, 3)));
            local_H_i14 := H_i(to_integer(to_unsigned(7, 3)));
            hash_out := (((((((((local_H_i7 & x"00000000000000000000000000000000000000000000000000000000")) or resize((local_H_i8 & x"000000000000000000000000000000000000000000000000"), 256)) or resize((local_H_i9 & x"0000000000000000000000000000000000000000"), 256)) or resize((local_H_i10 & x"00000000000000000000000000000000"), 256)) or resize((local_H_i11 & x"000000000000000000000000"), 256)) or resize((local_H_i12 & x"0000000000000000"), 256)) or resize((local_H_i13 & x"00000000"), 256)) or resize(local_H_i14, 256));
            hash   <= std_logic_vector(hash_out);
            hash_send <= '1';
            FSM <= s_SHA256_0;
          end if;
      end case;
    end if;
  end process SHA256_execute;

end architecture rtl_SHA256;

