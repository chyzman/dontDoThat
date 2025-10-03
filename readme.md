# Don't Do That
Stops Minecraft from doing certain annoying things.

[![Badge showing the amount of downloads on modrinth](https://img.shields.io/badge/dynamic/json?color=2d2d2d&colorA=5da545&label=&suffix=%20downloads%20&query=downloads&url=https://api.modrinth.com/v2/project/FBfbX7OM&style=flat-square&logo=modrinth&logoColor=2d2d2d)](https://modrinth.com/mod/dont-do-that)
[![Badge showing the amount of downloads on curseforge](https://cf.way2muchnoise.eu/full_1112211_downloads.svg?badge_style=flat)](https://www.curseforge.com/minecraft/mc-mods/dont-do-that)
[![Badge linking to issues on github](https://img.shields.io/badge/dynamic/json?query=value&url=https%3A%2F%2Fimg.shields.io%2Fgithub%2Fissues-raw%2Fchyzman%2FdontDoThat.json&label=&logo=github&color=2d2d2d&style=flat-square&labelColor=6e5494&logoColor=2d2d2d&suffix=%20issues)](https://github.com/chyzman/dontDoThat/issues)

Current features include:

### Chat/Commands
1. Makes chat history a lot longer (65536 messages instead of 100)
2. "Removes" the chat command character limit (2147483647 characters instead of 256)
3. Makes chat suggestions more lenient
   - Identifier suggestions suggest all namespaces instead of just minecraft
   - Literal suggestions match if they contain the input instead of just starting with it
4. Makes command exceptions log errors if not handled by the command

### Game Options
1. Prevents deletion of unknown options from your options.txt file
    - DDT stores unread lines from options.txt and writes them at the end of the file when saving
2. Makes overlapping keybindings work
    - Key conflicts are still shown in red, but will all be pressed

### Screens/GUI
1. Removes multiple annoying popups
   - Here be dragons
   - Resource/Data pack version mismatch
2. Removes arbitrary minimum loading times on certain loading screens
3. Allows GUIs to be opened while standing in portals

### Misc
1. Makes deleting worlds send them to the recycling bin instead of deleting them (if possible)
2. Makes "Pinned" resource packs able to be moved/disabled (server provided or otherwise)
