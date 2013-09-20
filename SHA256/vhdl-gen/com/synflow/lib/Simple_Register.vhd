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
-- Title      : Simple register
-- Author     : Nicolas Siret (nicolas.siret@synflow.com)
--              Matthieu Wipliez (matthieu.wipliez@synflow.com)
-- Standard   : VHDL'93
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

-------------------------------------------------------------------------------


entity Simple_Register is
  generic (
    depth : integer := 8);
  port (
    reset_n : in  std_logic;
    clock   : in  std_logic;
    din     : in  unsigned(depth - 1 downto 0);
    dout    : out unsigned(depth - 1 downto 0)
    );
end Simple_Register;

-------------------------------------------------------------------------------

architecture arch_Simple_Register of Simple_Register is

  -----------------------------------------------------------------------------
  -- Internal type and signal declaration
  -----------------------------------------------------------------------------

  signal reg_value : unsigned(depth - 1 downto 0);
  -----------------------------------------------------------------------------

begin

  syncProcess : process(reset_n, clock)
  begin
    if reset_n = '0' then
      reg_value <= (others => '0');
      dout      <= (others => '0');
    elsif rising_edge(clock) then
      reg_value <= din;
      dout      <= reg_value;
    end if;
  end process syncProcess;

end arch_Simple_Register;