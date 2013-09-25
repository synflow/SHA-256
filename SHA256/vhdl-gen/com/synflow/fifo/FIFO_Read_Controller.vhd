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

-------------------------------------------------------------------------------
-- Title      : FIFO read controller
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity FIFO_Read_Controller is
  generic (
    depth : integer := 8);
  port (
    reset_n    : in  std_logic;
    rd_clock   : in  std_logic;
    --
    enable     : in  std_logic;
    wr_address : in  unsigned(depth - 1 downto 0);
    --
    empty      : out std_logic;
    rd_address : out unsigned(depth - 1 downto 0)
    );
end FIFO_Read_Controller;

-------------------------------------------------------------------------------

architecture arch_FIFO_Read_Controller of FIFO_Read_Controller is

  -----------------------------------------------------------------------------
  -- Constants and signals
  -----------------------------------------------------------------------------
  signal i_rd_address    : unsigned(depth - 1 downto 0);
  signal next_rd_address : unsigned(depth - 1 downto 0);

begin
  rd_address <= i_rd_address;

  -- Synchro
  counter_sync : process(rd_clock, reset_n) is
  begin
    if reset_n = '0' then
      i_rd_address <= (others => '0');
    elsif rising_edge(rd_clock) then
      i_rd_address <= next_rd_address;
    end if;
  end process counter_sync;

  -- Incremental counter
  next_rd_address <= i_rd_address + unsigned'("" & enable);

  -- Flag management
  emptyFlag : process(i_rd_address, wr_address) is
  begin
    if i_rd_address = wr_address then
      empty <= '1';
    else
      empty <= '0';
    end if;
  end process emptyFlag;

end arch_FIFO_Read_Controller;