-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.sha256.step1.RomK by Synflow Studio
-- Project    : SHA-256
--
-- File       : RomK.vhd
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
entity RomK is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    addr       : in std_logic_vector(5 downto 0);
    dout       : out std_logic_vector(31 downto 0));
end RomK;


-------------------------------------------------------------------------------
architecture rtl_RomK of RomK is

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  type K_type is array (0 to 63) of unsigned(31 downto 0);
  constant K : K_type := (x"428a2f98", x"71374491", x"b5c0fbcf", x"e9b5dba5", x"3956c25b", x"59f111f1", x"923f82a4", x"ab1c5ed5", x"d807aa98", x"12835b01", x"243185be", x"550c7dc3", x"72be5d74", x"80deb1fe", x"9bdc06a7", x"c19bf174", x"e49b69c1", x"efbe4786", x"0fc19dc6", x"240ca1cc", x"2de92c6f", x"4a7484aa", x"5cb0a9dc", x"76f988da", x"983e5152", x"a831c66d", x"b00327c8", x"bf597fc7", x"c6e00bf3", x"d5a79147", x"06ca6351", x"14292967", x"27b70a85", x"2e1b2138", x"4d2c6dfc", x"53380d13", x"650a7354", x"766a0abb", x"81c2c92e", x"92722c85", x"a2bfe8a1", x"a81a664b", x"c24b8b70", x"c76c51a3", x"d192e819", x"d6990624", x"f40e3585", x"106aa070", x"19a4c116", x"1e376c08", x"2748774c", x"34b0bcb5", x"391c0cb3", x"4ed8aa4a", x"5b9cca4f", x"682e6ff3", x"748f82ee", x"78a5636f", x"84c87814", x"8cc70208", x"90befffa", x"a4506ceb", x"bef9a3f7", x"c67178f2");


  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  RomK_execute : process (reset_n, clock) is
    variable local_K : unsigned(31 downto 0);
    variable local_addr : unsigned(5 downto 0);
    variable addr_in  : unsigned(5 downto 0);
    variable dout_out : unsigned(31 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
    --
    elsif rising_edge(clock) then
      --
      if to_boolean('1') then
        -- Body of RomK_0 (line 27)
        addr_in := unsigned(addr);
        local_addr := addr_in;
        local_K := K(to_integer((local_addr)));
        dout_out := (local_K);
        dout   <= std_logic_vector(dout_out);
      end if;
    end if;
  end process RomK_execute;

end architecture rtl_RomK;

