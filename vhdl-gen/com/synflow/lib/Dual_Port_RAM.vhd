-------------------------------------------------------------------------------
-- Copyright (c) 2012-2013, Synflow SAS
-- All rights reserved.
-- 
-- REDISTRIBUTION of this file in source and binary forms, with or without
-- modification, is NOT permitted in any way.
--
-- The use of this file in source and binary forms, with or without
-- modification, is permitted if you have a valid commercial license of
-- Synflow Studio.
-- If you do NOT have a valid license of Synflow Studio: you are NOT allowed
-- to use this file.
-- 
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
-- ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
-- LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
-- CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
-- SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
-- INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
-- STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
-- WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
-- SUCH DAMAGE.

-------------------------------------------------------------------------------
-- Title      : Pseudo dual-port RAM
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;
-------------------------------------------------------------------------------



-------------------------------------------------------------------------------
-- Entity
-------------------------------------------------------------------------------
entity Dual_Port_RAM is
  generic (
    depth : integer := 8;
    width : integer := 16;
    initVal: integer := 0);
  port (
    wr_clock        : in  std_logic;
    rd_clock        : in  std_logic;
    reset_n         : in  std_logic;
    --
    wr_address      : in  std_logic_vector(depth - 1 downto 0);
    data            : in  std_logic_vector(width - 1 downto 0);
    data_send       : in  std_logic;
    rd_address      : in  std_logic_vector(depth - 1 downto 0);
    q               : out std_logic_vector(width - 1 downto 0));
end Dual_Port_RAM;
-------------------------------------------------------------------------------



-------------------------------------------------------------------------------
-- Architecture
-------------------------------------------------------------------------------
architecture arch_Dual_Port_RAM of Dual_Port_RAM is

  -----------------------------------------------------------------------------
  -- Internal type declarations
  -----------------------------------------------------------------------------
  type ram_type is array (0 to 2**depth - 1) of std_logic_vector(width - 1 downto 0);

  -----------------------------------------------------------------------------
  -- Internal signal declarations
  -----------------------------------------------------------------------------
  shared variable ram : ram_type := (others => std_logic_vector(to_signed(initVal, width)));

-------------------------------------------------------------------------------
begin

  readData : process (rd_clock)
  begin
    if rising_edge(rd_clock) then
      q <= ram(to_integer(unsigned(rd_address)));
    end if;
  end process readData;

  writeData : process (wr_clock)
  begin
    if rising_edge(wr_clock) then
      if data_send = '1' then
        ram(to_integer(unsigned(wr_address))) := data;
      end if;
    end if;
  end process writeData;

end arch_Dual_Port_RAM;
