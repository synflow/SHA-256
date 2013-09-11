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
-- Title      : Synchronous FIFO
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------


library ieee;
use ieee.std_logic_1164.all;
use ieee.std_logic_unsigned.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------

entity Synchronous_fifo is
  generic (
    depth : integer := 8;
    width : integer := 16);
  port (
    reset_n          : in  std_logic;
    clock            : in  std_logic;
                                        -- write data
    din_send         : in  std_logic;
    din              : in  std_logic_vector(width - 1 downto 0);
                                        -- read data
    dout_send        : out std_logic;
    dout             : out std_logic_vector(width - 1 downto 0);
                                        -- Ready to send
    ready            : in  std_logic;
                                        -- Flags
    full             : out std_logic;
    almost_full      : out std_logic;
    empty            : out std_logic
    );
end Synchronous_fifo;

-------------------------------------------------------------------------------

architecture arch_Synchronous_fifo of Synchronous_fifo is

  -----------------------------------------------------------------------------
  -- Signals declaration
  -----------------------------------------------------------------------------
  signal full_i          : std_logic;
  signal empty_i         : std_logic;
  --
  signal wr_enable       : std_logic;
  signal rd_enable       : std_logic;
  --
  signal rd_address      : unsigned(depth - 1 downto 0);
  signal wr_address      : unsigned(depth - 1 downto 0);
  -------------------------------------------------------------------------------

begin

  -- full and empty flag
  full             <= full_i;
  empty            <= empty_i;

  process (reset_n, clock) is
  begin
    if reset_n = '0' then
      dout_send <= '0';
    elsif rising_edge(clock) then
      dout_send <= rd_enable;
    end if;
  end process;

  -- wr_enable and rd_enable are active iff the flags allow it
  wr_enable <= din_send and not full_i;
  rd_enable <= ready and not empty_i;

  ram : entity work.Dual_Port_RAM
    generic map (
      depth => depth,
      width => width)
    port map (
      wr_clock        => clock,
      rd_clock        => clock,
      reset_n         => reset_n,
      wr_address      => std_logic_vector(wr_address),
      data            => din,
      data_send       => wr_enable,
      rd_address      => std_logic_vector(rd_address),
      q               => dout);

  wr_ctrl : entity work.FIFO_Write_Controller
    generic map (
      depth => depth)
    port map (
      reset_n     => reset_n,
      wr_clock    => clock,
      enable      => wr_enable,
      rd_address  => rd_address,
      full        => full_i,
      wr_address  => wr_address,
      almost_full => almost_full);

  rd_ctrl : entity work.FIFO_Read_Controller
    generic map (
      depth => depth)
    port map (
      reset_n    => reset_n,
      rd_clock   => clock,
      enable     => rd_enable,
      wr_address => wr_address,
      empty      => empty_i,
      rd_address => rd_address);

end arch_Synchronous_fifo;
