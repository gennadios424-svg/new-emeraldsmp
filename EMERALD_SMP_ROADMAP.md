# EMERALD SMP — FULL DEVELOPMENT ROADMAP

Target: Paper 1.21.11 / Java 21.

This is the authoritative development specification. /worth already exists and MUST be continued, not replaced with a basic Stage 1 implementation.

1. /worth — existing system
- Large chest GUI with every obtainable normal-player item.
- Exclude bedrock, command blocks, barrier, structure/admin-only items, spawn eggs and other unobtainable items.
- Every listed item has a value.
- Search, sorting, pagination and working page buttons.
- Emerald/dark-green visual theme.
- Prices visible on hover.
- Money formatting: $1K, $1M, $1B.
- /worth is the master price database shared by /sell, spawner auto-selling and all other systems needing item values.
- Anchors: Kelp $200; Sea Pickle $150; Pink Petals $125; Shulker Box $200; Diamond Hoe $250; Netherite Block $250,000; Dragon Egg $2,000,000.
- Container anchors: chest boat $50; chest minecart $250; regular chest $500. Keep implementation consistent.

2. /sell
- Real chest/container GUI, never command-only.
- Normal click, right click, shift-click, drag, number keys and stack splitting.
- Shift-click from player inventory into sell slots MUST work.
- Show calculated value and provide a sell button; closing can optionally sell.
- Handle shulker boxes appropriately.
- No duplication, deletion, corruption, kicks, crashes or ghost items.
- Use the shared WorthService/database.

3. /shop
- Completely separate from /worth.
- Proper GUI shop.
- Categories: REDSTONE, BLOCKS, CPVP, FOOD, FARM, END.
- CPVP remains limited; do not make an unlimited OP shop.
- Do not create a giant armor/sword shop.
- Buying should normally cost more than the sell-back value.
- Example Kelp: buy $500, sell $200.

4. /ah
- /ah, item-in-hand listing, amount/price input, GUI, search, pagination, confirmation, seller info and safe item handling.
- Listings expire after 14 days.
- Expired items go to seller collection/expired-items storage and never simply disappear.
- Keep hover text clean.

5. /order
- Player buy orders for all obtainable items.
- Search, amount, price, creation GUI, collection and safe payment/item handling.
- Amount means individual items, not Minecraft stack size.
- Use chat input for amount and price, not old sign input.
- Orders expire after 14 days.
- Fulfilling an order atomically transfers items/payment and prevents races or double payouts.

6. /cf
- Coinflip with money balance.
- Create bet, amount input, GUI, join, winner selection and payout.
- Prevent zero/negative values, self-join where disallowed, duplicate payouts and balance exploits.

7. /rtp
- GUI: Overworld, Nether, End.
- 5-second countdown with sounds.
- Movement cancels countdown.
- No coordinates displayed in GUI.
- Reject void, lava, water, bedrock, Nether roof/invalid locations, deepslate/unsafe underground areas and trapped/deadly spawn points.
- Land only at genuinely safe locations.

8. /home
- /sethome, /home, /delhome.
- Teleport countdown and movement cancellation.
- Persistent homes.
- Permission-based limits where appropriate.

9. Teams
- Create, invite, accept, leave, kick, member management, member view and optional team chat.
- Prevent abuse/exploits.

10. Economy
Money is the real currency and must remain separate from Emerald Shards.
Money powers /shop, /ah, /order, /cf, /invest and other economy systems.
Shards are a separate Emerald progression/resource system.
Balances persist.
Formatting: $500, $25K, $2.5M, $1.2B.

11. /invest
- Maximum investment: $125,000,000.
- Generation: $125/second.
- Accept K/M/B input such as 5M.
- GUI shows invested amount, earnings, maximum and status.
- No unintended offline/restart generation exploits.

12. Emerald Shards
- Completely separate from money.
- Independent storage and handling.

13. /shardgainer
- Random reward range 1–92 Emerald Shards.
- 7-day expiry starts when shards are received, not when a crate was created.
- Expired shards cannot be exploited.

14. /drill
- Separate from shardgainer.
- OP/custom Original Drill.
- 3x3 digout pickaxe.

15. Spawners
- Skeleton spawner target: 1–4 skeletons approximately every 15 seconds.
- Appropriate bones, arrows and bows.
- GUI with drop button, preferences and information.
- Optimize for large numbers of spawners.
- Diamond Pickaxe or higher can break spawners.
- No Silk Touch requirement.
- Spawners stack.

16. Crates
- Physical crates visually resemble colored shulker-box-style crates.
- Digital keys.
- /crates edit accepts actual ItemStacks.
- Rewards, chances, key checks, animations, item handling, permissions and configuration.
- No fake text-only crate system.

17. /keyall
- OP-only.
- Gives selected key to eligible players.
- Approximately 8–10 second visible animation.
- Digital distribution; do not create physical keys unless specifically configured.
- Keys are consumable and finite.
- Prevent infinite keys, duplication, reuse and double claiming.

18. /banlist
- GUI only, not chat.
- Show player, reason, duration and status.
- NEVER display IP addresses.
- IPs remain hidden from normal users and GUI displays.

19. /suslist
- Admin/staff GUI.
- Player, reason, status and appropriate staff notes.
- No private information exposure.

20. TAB
- Clean professional TAB.
- Rank, player name and ping.
- Ranks: OWNER, DEV, MOD, MEDIA, EMERALD, MVP, VIP, MEMBER.
- Emerald-themed formatting.
- No overload.

21. Sidebar
- Emerald SMP branding, money, Emerald Shards, rank and only useful information.
- Clean, readable emerald/dark-green styling.

22. /bal
- /bal shows money.
- Optional other-player balance lookup.
- Uses the same central economy.

23. /afk
- Reliable AFK detection/status.
- Integrate with TAB/sidebar where useful.
- Avoid false AFK states.

24. Visual system
- Emerald green, dark green, black/dark GUI backgrounds.
- Clean gradients/glossy emerald appearance where appropriate.
- Avoid ugly plain text, excessive emojis, rainbow clutter, huge lore walls and inconsistent GUIs.
- Ranks must visually look like actual ranks.

25. Lag cleaner
- Approximately every 5 minutes.
- Remove genuinely unnecessary entities/items safely.
- Never blindly delete valuable dropped items, pets, important entities or server-critical entities.
- Optimize the cleanup.

26. Data/storage
Persist money, shards, homes, teams, AH listings, orders, investments, crate/key data, spawners, ranks and all progression.
Save on quit, shutdown, periodically and after important transactions.
Use safe/atomic transaction patterns where necessary.

27. Economy exploit protection
Test and protect against double-clicks, shift-clicks, rapid clicking, GUI closing, restart during transactions, disconnects, duplicate item movement, negative amounts, numeric overflow, huge/negative balances, concurrent AH purchases and concurrent order fulfillment.
Never let a player receive both sides of a transaction.

28. GUI quality
Consistent borders, emerald/dark-green styling, clear buttons, correct icons, concise lore, working pagination, back and close buttons.
No placeholder/fake buttons.

29. Performance
Avoid huge repeating tasks, constant full-server scans, excessive entity spawning, bad inventory handlers, memory leaks, infinite loops and per-tick file/database writes.
Optimize spawners, RTP, AH, orders, worth, sell and lag cleaner.

30. Commands/permissions
Register all commands in plugin.yml.
Admin/OP functionality requires real permissions.
Use at minimum emerald.admin; do not rely only on command-name checks.

31. Development rule
DO NOT rebuild Emerald SMP as a basic Stage 1 plugin. /worth is the first completed system; all other systems must be integrated around the existing architecture.

32. Release gate
Before calling the plugin finished, test on a real Paper 1.21.11 / Java 21 server:
- Console startup clean.
- Plugin actually ENABLES.
- No dependency errors.
- No command registration errors.
- No GUI/inventory exceptions.
- No economy duplication.
- No item loss.
- No crashes.
- Test actual commands in-game.
A successful GitHub compilation alone does NOT count as runtime validation.
