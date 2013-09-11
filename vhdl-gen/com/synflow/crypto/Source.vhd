-------------------------------------------------------------------------------
-- Title      : Generated from com.synflow.crypto.Source by Synflow Studio
-- Project    : SHA-256
--
-- File       : Source.vhd
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


-------------------------------------------------------------------------------
entity Source is
  port (
                                          -- Standard I/Os
    clock    : in  std_logic;
    reset_n  : in  std_logic;
                                          -- Actor I/Os
    stimulus       : out std_logic_vector(31 downto 0);
    stimulus_send  : out std_logic);
end Source;


-------------------------------------------------------------------------------
architecture rtl_Source of Source is

  function to_boolean(b : std_logic) return boolean is begin return b = '1'; end;
  function to_std_logic(b : boolean) return std_logic is begin if b then return '1'; else return '0'; end if; end;

  -----------------------------------------------------------------------------
  -- Signal(s) and Constant(s)
  -----------------------------------------------------------------------------
  signal Source_0_i : unsigned(5 downto 0);


  -----------------------------------------------------------------------------
  -- FSM
  -----------------------------------------------------------------------------
  type FSM_type is (s_Source_0, s_Source_1, s_Source_2);
  signal FSM : FSM_type;

  -----------------------------------------------------------------------------
  -- Behavior
  -----------------------------------------------------------------------------
  -- Scheduler of Source_1_a (line 15)
  impure function isSchedulable_Source_1_a return std_logic is
    variable local_i : unsigned(5 downto 0);
  begin
    local_i := Source_0_i;
    return to_std_logic((local_i) < "001110");
  end function isSchedulable_Source_1_a;
  
  -- Scheduler of Source_1_b (line 17)
  impure function isSchedulable_Source_1_b return std_logic is
    variable local_i : unsigned(5 downto 0);
  begin
    local_i := Source_0_i;
    return (not (to_std_logic((local_i) < "001110")));
  end function isSchedulable_Source_1_b;
  

begin

  -----------------------------------------------------------------------------
  -- Synchronous process
  -----------------------------------------------------------------------------
  Source_execute : process (reset_n, clock) is
    variable local_i : unsigned(5 downto 0);
    variable stimulus_out : unsigned(31 downto 0) :=  (others => '0');
  begin
    if reset_n = '0' then
      Source_0_i <= "000000";
      stimulus      <= (others => '0');
      stimulus_send <= '0';
      FSM    <= s_Source_0;
    --
    elsif rising_edge(clock) then
      stimulus_send <= '0';
      --
      case FSM is
        when s_Source_0 =>
          if to_boolean('1') then
            -- Body of Source_0 (line 12)
            stimulus_out := x"61626380";
            Source_0_i <= "000000";
            stimulus   <= std_logic_vector(stimulus_out);
            stimulus_send <= '1';
            FSM <= s_Source_1;
          end if;
        
        when s_Source_1 =>
          if to_boolean(isSchedulable_Source_1_a) then
            -- Body of Source_1_a (line 15)
            local_i := Source_0_i;
            stimulus_out := x"00000000";
            local_i := resize(resize(local_i, 7) + "0000001", 6);
            Source_0_i <= (local_i);
            stimulus   <= std_logic_vector(stimulus_out);
            stimulus_send <= '1';
            FSM <= s_Source_1;
          elsif to_boolean(isSchedulable_Source_1_b) then
            -- Body of Source_1_b (line 17)
            stimulus_out := x"00000018";
            stimulus   <= std_logic_vector(stimulus_out);
            stimulus_send <= '1';
            FSM <= s_Source_2;
          end if;
        
        when s_Source_2 =>
      end case;
    end if;
  end process Source_execute;

end architecture rtl_Source;

