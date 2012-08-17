Copyright (C) 2012 Jacek Kuzemczak (wide_load) <support@minebans.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.



Introduction

MineBansAntiSpam is an advanced spam prevention plugin for Bukkit
based Minecraft servers that will automatically ban players that are
detected as spamming for 30 minutes.

Checks

    1. Message Count
       
       The number of messages over a 2.5 second period is checked every
       2.5 seconds, if that number is greater than 8 the player is
       given a warning or banned.
    
    2. Message Delay
       
       The delay between messages is checked in two ways, first the
       time between messages is checked to see if any of the most
       recent 50 that were sent were too close together. As well as
       this the standard deviation of the last 50 message delays are
       checked, if all of them were within 20 ms of the average delay
       the player is warned or banned.